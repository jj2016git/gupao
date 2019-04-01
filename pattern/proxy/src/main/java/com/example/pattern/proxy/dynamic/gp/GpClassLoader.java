
package com.example.pattern.proxy.dynamic.gp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class GpClassLoader extends ClassLoader {
    private File classPathFile;

    public GpClassLoader() {
        String classpath = GpClassLoader.class.getResource("").getPath();
        this.classPathFile = new File(classpath);
    }

    /**
     * Finds the class with the specified <a href="#name">binary name</a>.
     * This method should be overridden by class loader implementations that
     * follow the delegation model for loading classes, and will be invoked by
     * the {@link #loadClass <tt>loadClass</tt>} method after checking the
     * parent class loader for the requested class.  The default implementation
     * throws a <tt>ClassNotFoundException</tt>.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class could not be found
     * @since 1.2
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String className = GpClassLoader.class.getPackage().getName() + "." + name;
        if (classPathFile != null) {
            File classFile = new File(classPathFile, name.replaceAll("\\.", "/") + ".class");
            if (classFile.exists()) {
                try (FileInputStream fis = new FileInputStream(
                        classFile); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

                    byte[] buff = new byte[1024];
                    int len = 0;
                    while ((len = fis.read(buff)) != -1) {
                        bos.write(buff, 0, len);
                    }
                    return defineClass(className, bos.toByteArray(), 0, bos.size());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return null;
    }
}
