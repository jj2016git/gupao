package com.example.gpspring.mvcframework.context;

import com.example.gpspring.mvcframework.annotation.GPAutowired;
import com.example.gpspring.mvcframework.beans.GPBeanFactory;
import com.example.gpspring.mvcframework.beans.GPBeanWrapper;
import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;
import com.example.gpspring.mvcframework.beans.support.GPBeanDefinitionReader;
import com.example.gpspring.mvcframework.beans.support.GPDefaultListableBeanFactory;
import com.example.gpspring.mvcframework.context.support.GPAbstractApplicationContext;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * // 如何解决循环依赖： e.g.
 * /*
 * * Class A {
 * * 		B b;
 * * }
 * *
 * * Class B {
 * *      A a;
 * * }
 * *
 * * 1. getBean(A): this.singletonFactories.add(A), 执行到populateBean时，会调用getBean(B)
 * * 2. getBean(B): this.singletonFactories.add(B), 執行到populateBean時，會調用getBean(A)
 * * 2.1 調用getSingleton(A), 調用A的objectFactory，拿到A的半成品的引用，完成B實例化
 * * 3. 返回1，完成A的實例化。
 * * 結論：因為B持有A的引用，A實例化完成後，B自然拿到完整的A
 * * 循環依賴注意項：B必須要能夠拿到A的半成品=>A必須要先完成實例化=>A不能通過構造器注入B
 */
public class GPApplicationContext extends GPAbstractApplicationContext implements GPBeanFactory {
    /** Cache of singleton objects: bean name --> bean instance */
    /**
     * 完整的singleton集合
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);


    /** Cache of early singleton objects: bean name --> bean instance */
    /**
     * 半成品singleton集合
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    private String configLocation;
    private GPDefaultListableBeanFactory beanFactory;

    public GPApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        this.refresh();
    }

    /**
     * 解决循环引用：
     * 1. instantiateBean, 将bean放入earlySingletonObjects中
     * 2. populateBean, 递归调用getBean实例化依赖
     * 3. initializeBean
     * @param beanName
     * @return
     * @throws Exception
     */
    @Override
    public Object getBean(String beanName) throws Exception {

        // 1. 獲取singleton對象
        Object instance = getSingleton(beanName);
        if (instance != null) {// 返回完整或半成品对象
            return instance;
        }

        // 2. 检查@DependOn依赖，首先getBean(依赖对象)

        // 3. createBean(beanName, beanDefinition, args)
        instance = createBean(beanName, this.beanFactory.getBeanDefinitionMap().get(beanName), null);

        // 创建完整singleton成功，添加singleton
        this.singletonObjects.put(beanName, instance);
        this.earlySingletonObjects.remove(beanName);

        return instance;
    }

    private Object getSingleton(String beanName) throws Exception {
        Object instance = this.singletonObjects.get(beanName);
        if (instance == null) {
            instance = this.earlySingletonObjects.get(beanName);
        }
        return instance;
    }


    private Object createBean(String beanName, GPBeanDefinition beanDefinition, Object[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        // 1. 创建实例
        GPBeanWrapper instanceWrapper = createBeanInstance(beanName, beanDefinition, args);
        Object bean = instanceWrapper.getWrappedInstance();

        this.earlySingletonObjects.putIfAbsent(beanName, bean);

        // 2. 实例属性注入
        populateBean(beanName, beanDefinition, instanceWrapper);

        // 3. 实例初始化
        return initializeBean(beanName, bean, beanDefinition);
    }

    /**
     * AOP发生地点
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */
    private Object initializeBean(String beanName, Object bean, GPBeanDefinition beanDefinition) {

        return bean;
    }

    private void populateBean(String beanName, GPBeanDefinition beanDefinition, GPBeanWrapper instanceWrapper) {
        Object bean = instanceWrapper.getWrappedInstance();
        Class<?> beanClass = instanceWrapper.getWrappedClass();
        Field[] fields = beanClass.getDeclaredFields();
        Stream.of(fields).forEach((field) -> {
            if (field.isAnnotationPresent(GPAutowired.class)) {
                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                String autowiredBeanName = autowired.value();
                Object autowiredBean = null;
                if (autowiredBeanName.trim().isEmpty()) {
                    autowiredBeanName = resolveDependency(field.getType());
                }
                try {
                    autowiredBean = getBean(autowiredBeanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (autowiredBean == null) {
                    throw new RuntimeException("autowire a nonexistent bean");
                }
                field.setAccessible(true);
                try {
                    field.set(bean, autowiredBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String resolveDependency(Class<?> type) {
        for (Map.Entry<String, GPBeanDefinition> entry : this.beanFactory.getBeanDefinitionMap().entrySet()) {
            String beanName = entry.getKey();
            GPBeanDefinition bd = entry.getValue();
            if (type.isAssignableFrom(bd.getBeanClass())) {
                return beanName;
            }
        }
        return null;
    }

    private GPBeanWrapper createBeanInstance(String beanName, GPBeanDefinition beanDefinition, Object[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String beanClassName = beanDefinition.getBeanClassName();
        Class<?> beanClass = Class.forName(beanClassName);
        Object object = beanClass.newInstance();
        return new GPBeanWrapper(object, beanClass);
    }

    @Override
    protected void refresh() {
        try {
            // 加载beanDefinitions，生成beanDefinitionMap
            refreshBeanFactory();

            // 非lazyInit singleton实例化
            instantiateSingletons(beanFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void refreshBeanFactory() throws ClassNotFoundException {
        this.beanFactory = new GPDefaultListableBeanFactory();
        loadBeanDefinitions(this.beanFactory);
    }

    private void loadBeanDefinitions(GPDefaultListableBeanFactory beanFactory) throws ClassNotFoundException {
        new GPBeanDefinitionReader(configLocation).scanAndRegister(beanFactory);
    }

    private void instantiateSingletons(GPDefaultListableBeanFactory beanFactory) {
        beanFactory.getBeanDefinitionMap().forEach((beanName, beanDef) -> {
            if (!beanDef.isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String[] getBeanNames(Class<?> type) {
        String[] result = new String[this.singletonObjects.size()];
        return this.singletonObjects.keySet().toArray(result);
    }
}
