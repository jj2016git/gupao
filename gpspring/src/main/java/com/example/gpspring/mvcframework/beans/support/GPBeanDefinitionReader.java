package com.example.gpspring.mvcframework.beans.support;

import com.example.gpspring.mvcframework.beans.config.GPBeanDefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class GPBeanDefinitionReader {
    private Properties config = new Properties();

    public GPBeanDefinitionReader(String... location) {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(location[0].replace()) {
            config.load(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        doScan()
    }

    public List<GPBeanDefinition> loadBeanDefinitions() {
        return new ArrayList<>();
    }
}
