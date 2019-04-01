package com.example.pattern.proxy;

import com.example.pattern.proxy.dynamic.cglib.LogMethodInterceptor;
import com.example.pattern.proxy.dynamic.gp.GpLogInnvocationHandler;
import com.example.pattern.proxy.dynamic.jdk.LogInnvocationHandler;
import com.example.pattern.proxy.staticproxy.OrderServiceLogProxy;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class ProxyTest {
    public static void main(String[] args) throws IOException {
        IOrderService targetService = new OrderServiceImpl();
        IOrderService staticProxy = new OrderServiceLogProxy(targetService);
        execute(staticProxy);

        IOrderService jdkProxy = (IOrderService) new LogInnvocationHandler().getInstance(targetService);
        execute(jdkProxy);
        byte[] proxyCode = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{IOrderService.class});
        FileOutputStream fos = new FileOutputStream("D://$Proxy0.class");
        fos.write(proxyCode);
        fos.close();
        System.out.println("world");

        IOrderService gpProxy = (IOrderService) new GpLogInnvocationHandler().getInstance(targetService);
        execute(gpProxy);

        IOrderService cglibProxy = (IOrderService) new LogMethodInterceptor().getInstance(targetService);
        execute(cglibProxy);
    }

    private static void execute(IOrderService proxy) {
        proxy.createOrder();
    }


}
