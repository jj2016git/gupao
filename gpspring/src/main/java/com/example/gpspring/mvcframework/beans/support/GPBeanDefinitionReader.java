package com.example.gpspring.mvcframework.beans.support;

import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.beans.Introspector.decapitalize;

public class GPBeanDefinitionReader {
    private static final String SCAN_PACKAGE = "scanPackage";
    private Properties config = new Properties();
    private List<String> beanClassNames = new ArrayList<>();

    public GPBeanDefinitionReader(String... location) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(location[0].replace("classpath:", ""))) {
            config.load(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doScan(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));

        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if (file.isDirectory()) {
                doScan(scanPackage + "." + file.getName());
            } else {
                if (file.getName().endsWith(".class")) {
                    String className = scanPackage + "." + file.getName().replace(".class", "");
                    beanClassNames.add(className);
                }
            }
        }
    }

    public List<GPBeanDefinition> scanAndRegister(GPDefaultListableBeanFactory beanFactory) throws ClassNotFoundException {

        doScan(config.getProperty(SCAN_PACKAGE));

        List<GPBeanDefinition> beanDefinitions = new ArrayList<>();
        for (String beanClassName : beanClassNames) {
            Class<?> beanClass = Class.forName(beanClassName);
            if (beanClass.isInterface()) {
                continue;
            }

            String beanName = decapitalize(beanClass.getSimpleName());
            beanFactory.getBeanDefinitionMap().putIfAbsent(beanName, new GPBeanDefinition(beanClassName, beanClass));
            beanFactory.getBeanDefinitionNames().add(beanName);
        }
        return beanDefinitions;
    }

    public Properties getConfig() {
        return config;
    }
}
