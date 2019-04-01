
package com.example.pattern.proxy.dynamic.gp;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 1. 拿到被代理对象的，反射获取接口
 * 2. 动态生成新代理类源码，代理类实现接口
 * 2. 将.java源码编译成.class文件
 * 3. 使用自定义classLoader装载、初始化代理类
 * 4. 生成代理对象
 */
public class GpProxy {
    private static final String ln = "\r\n";

    public static Object newProxyInstance(GpClassLoader gpClassLoader, Class<?>[] interfaces,
                                          GpLogInnvocationHandler gpLogInnvocationHandler) {


        try {
            String src = generateSrc(interfaces);

            String filePath = GpProxy.class.getResource("").getPath();
            File f = new File(filePath + "$Proxy0.java");
            try (FileWriter fw = new FileWriter(f)) {
                // 输出源码，与jdk生成的代码进行对比
                fw.write(src);
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
            Iterable iterable = manager.getJavaFileObjects(f);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, iterable);
            task.call();
            manager.close();

            Class proxyClass = gpClassLoader.findClass("$Proxy0");
            Constructor c = proxyClass.getConstructor(GpInvocationHandler.class);
            f.delete();


            return c.newInstance(gpLogInnvocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String generateSrc(Class<?>[] interfaces) {
        StringBuffer sb = new StringBuffer();
        sb.append("package com.example.pattern.proxy.dynamic.gp;" + ln);
        sb.append("import com.example.pattern.proxy.*;" + ln);
        sb.append("import java.lang.reflect.*;" + ln);
        sb.append("public final class $Proxy0 implements ");
        for (Class iface : interfaces) {
            sb.append(iface.getName());
        }
        sb.append(" {" + ln);
        sb.append("GpInvocationHandler h;" + ln);
        sb.append("public $Proxy0(GpInvocationHandler h) {this.h = h;}" + ln);
        for (Class iface : interfaces) {
            for (Method method : iface.getMethods()) {
                sb.append("public final " + method.getReturnType().getName() + " " + method.getName() + "() { " + ln);
                sb.append("try {" + ln);
                sb.append("Method m = " + iface.getName() + ".class.getMethod(\"" + method.getName() + "\", new " +
                        "Class[]{});" + ln);
                sb.append("return (" + method.getReturnType().getName() + ")this.h.invoke(this, m, null);} " + "catch" +
                        "(Throwable e) {e" + ".printStackTrace" + "();return null;}");
                sb.append("}");
            }
        }
        sb.append("}" + ln);
        return sb.toString();
    }
}
