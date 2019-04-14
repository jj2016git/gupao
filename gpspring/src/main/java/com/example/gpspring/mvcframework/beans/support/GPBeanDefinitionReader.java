package com.example.gpspring.mvcframework.beans.support;

import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

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

        doScan(config.getProperty(SCAN_PACKAGE));
    }

    private void doScan(String scanPackage) {
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));

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

    public List<GPBeanDefinition> loadBeanDefinitions() throws ClassNotFoundException {
        List<GPBeanDefinition> beanDefinitions = new ArrayList<>();
        for (String beanClassName : beanClassNames) {
            Class<?> beanClass = Class.forName(beanClassName);
            if (beanClass.isInterface()) {
                continue;
            }

            beanDefinitions.add(doCreateBeanDefinition(decapitalize(beanClass.getSimpleName()), beanClassName));

            Class<?>[] interfaces = beanClass.getInterfaces();
            Stream.of(interfaces).forEach((item)->{
                beanDefinitions.add(doCreateBeanDefinition(item.getName(), beanClassName));
            });
        }
        return beanDefinitions;
    }

    private GPBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName) {
        return new GPBeanDefinition(factoryBeanName, beanClassName);
    }
}
