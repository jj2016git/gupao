package com.example.gpspring.mvcframework.context;

import com.example.gpspring.mvcframework.beans.GPBeanFactory;
import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;
import com.example.gpspring.mvcframework.beans.support.GPBeanDefinitionReader;
import com.example.gpspring.mvcframework.beans.support.GPDefaultListableBeanFactory;

import java.util.List;

public class GPApplicationContext extends GPDefaultListableBeanFactory implements GPBeanFactory {
    private String configLocation;
    private GPBeanDefinitionReader reader;

    public GPApplicationContext(String configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    protected void refresh() {
        // 1. 定位配置文件
        reader = new GPBeanDefinitionReader(configLocation);

        // 2. 加载配置文件，扫描相关的类，将类封装成BeanDefinition
        List<GPBeanDefinition> beanDefinitions =  reader.loadBeanDefinitions();


        // 3. 注册
        doRegisterBeanDefinition(beanDefinitions);

        // 4. 非lazyInit类初始化

    }

    private void doRegisterBeanDefinition(List<GPBeanDefinition> beanDefinitions) {
    }
}
