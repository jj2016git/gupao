package com.example.gpspring.mvcframework.context;

import com.example.gpspring.mvcframework.annotation.GPAutowired;
import com.example.gpspring.mvcframework.beans.GPBeanFactory;
import com.example.gpspring.mvcframework.beans.GPBeanWrapper;
import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;
import com.example.gpspring.mvcframework.beans.support.GPBeanDefinitionReader;
import com.example.gpspring.mvcframework.beans.support.GPDefaultListableBeanFactory;
import com.example.gpspring.mvcframework.context.support.GPAbstractApplicationContext;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class GPApplicationContext extends GPAbstractApplicationContext implements GPBeanFactory {
    /**
     * Cache of singleton objects: bean name --> bean instance
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    private String configLocation;
    private GPDefaultListableBeanFactory beanFactory;

    public GPApplicationContext(String configLocation) {
        this.configLocation = configLocation;
        this.refresh();
    }

    @Override
    public Object getBean(String beanName) throws Exception {

        // 1. 检查singletonObjects是否包含bean，如果包含，直接返回
        if (this.singletonObjects.containsKey(beanName)) {
            return this.singletonObjects.get(beanName);
        }

        // 2. 检查依赖，首先getBean(依赖对象)

        // 3. createBean(beanName, beanDefinition, args)
        return createBean(beanName, this.beanFactory.getBeanDefinitionMap().get(beanName), null);
    }

    private Object createBean(String beanName, GPBeanDefinition beanDefinition, Object[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        // 1. 创建实例
        GPBeanWrapper instanceWrapper = createBeanInstance(beanName, beanDefinition, args);
        Object bean = instanceWrapper.getWrappedInstance();

        this.singletonObjects.putIfAbsent(beanName, bean);

        // 2. 实例属性注入
        populateBean(beanName, beanDefinition, instanceWrapper);

        // 3. 实例初始化
        Object exposedBean = initializeBean(beanName, bean, beanDefinition);
        return exposedBean;
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
        Stream.of(fields).forEach((field)-> {
            if (field.isAnnotationPresent(GPAutowired.class)) {
                GPAutowired autowired = field.getAnnotation(GPAutowired.class);
                String autowiredBeanName = autowired.value();
                Object autowiredBean = null;
                if (autowiredBeanName.trim().isEmpty()) {
                    autowiredBean = this.singletonObjects.get(field.getType().getName());
                } else {
                    autowiredBean = this.singletonObjects.get(autowiredBeanName);
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

    private GPBeanWrapper createBeanInstance(String beanName, GPBeanDefinition beanDefinition, Object[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String beanClassName = beanDefinition.getBeanClassName();
        Class<?> beanClass = Class.forName(beanClassName);
        Object object = beanClass.newInstance();
        return new GPBeanWrapper(object, beanClass);
    }

    @Override
    protected void refresh() {
        try {
            refreshBeanFactory();

            // 4. 非lazyInit类初始化
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
        List<GPBeanDefinition> beanDefinitions = new GPBeanDefinitionReader(configLocation).loadBeanDefinitions();
        doRegisterBeanDefinition(beanFactory, beanDefinitions);
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

    private void doRegisterBeanDefinition(GPDefaultListableBeanFactory beanFactory, List<GPBeanDefinition> beanDefinitions) {
        Map<String, GPBeanDefinition> beanDefinitionMap = beanFactory.getBeanDefinitionMap();
        beanDefinitions.forEach((beanDef) -> {
            beanDefinitionMap.putIfAbsent(beanDef.getFactoryBeanName(), beanDef);
        });
    }
}
