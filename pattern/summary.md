| 设计模式 | 总结                                                         |
| -------- | ------------------------------------------------------------ |
| 单例     | 全局唯一，如Spring管理的默认类都是单例                       |
| 原型     | 通过clone，序列化/反序列化的方式快速复制对象，绕过构造和setter |
| 工厂     | 根据用户要求创建类或一类产品                                 |
| 适配器   | 新旧接口不匹配，造一个适配器既实现新接口，也集成旧接口实现   |
| 装饰器   | 添加辅助功能，但本质不变，可以层层包装                       |
| 策略     | 有若干种方法可以完成等同的目标，根据用户选择自由切换方法，比如Arrays.sort() |
| 模板方法 | 领导规定框架中的每一步流程，小兵负责某些步骤的具体实现       |
| 观察者   | 电话预定，到货的时候卖家通知买家                             |
| 代理     | 类外层加个壳（代理），所有外部调用必须穿过壳来到真实类，壳可以对真实类进行增强，也可以拦截部分外部调用 |
|委派|外部调用入口，根据不同需求调用不同功能的子模块，如DispatcherServlet, Reactor|

## Spring中的设计模式

### 单例

ApplicationContext

### 原型

### 工厂

- `org.springframework.boot.SpringApplication#createApplicationContext`

```java
if (contextClass == null) {
    try {
        switch (this.webApplicationType) {
            case SERVLET:
                contextClass = Class.forName(DEFAULT_SERVLET_WEB_CONTEXT_CLASS);
                break;
            case REACTIVE:
                contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
                break;
            default:
                contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
        }
    }
    catch (ClassNotFoundException ex) {
        throw new IllegalStateException(
            "Unable create a default ApplicationContext, "
            + "please specify an ApplicationContextClass",
            ex);
    }
}
return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
```

### 代理模式

- `org.apache.ibatis.binding.MapperProxy, org.apache.ibatis.binding.MapperProxyFactory`

```java
protected T newInstance(MapperProxy<T> mapperProxy) {
    return Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy);
}

public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
            }

            if (this.isDefaultMethod(method)) {
                return this.invokeDefaultMethod(proxy, method, args);
            }
        } catch (Throwable var5) {
            throw ExceptionUtil.unwrapThrowable(var5);
        }

        MapperMethod mapperMethod = this.cachedMapperMethod(method);
        return mapperMethod.execute(this.sqlSession, args);
    }
}
```

### 适配器

- `org.springframework.web.reactive.HandlerAdapter`

- `org.springframework.aop.framework.adapter.AdvisorAdapter`: allow handling of new Advisors and Advice types.

```java
boolean supportsAdvice(Advice advice);
```

### 模板方法

`org.springframework.jdbc.core.JdbcTemplate`

### 策略

- `org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#autowire`

```java
bean = getInstantiationStrategy().instantiate(bd, null, parent);
```

### 观察者

- `org.springframework.boot.SpringApplication#SpringApplication(org.springframework.core.io.ResourceLoader, java.lang.Class<?>...)`

```java
setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
```

- `org.springframework.boot.SpringApplication#run(java.lang.String...)`

```java
listeners.started(context);
```

### 委派

- `org.springframework.web.servlet.DispatcherServlet#doDispatch`

### 装饰器

- `org.springframework.http.server.reactive.HttpHeadResponseDecorator#writeWith`

```java
@Override
	public final Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {

		// After Reactor Netty #171 is fixed we can return without delegating

		return getDelegate().writeWith(
				Flux.from(body)
						.reduce(0, (current, buffer) -> {
							int next = current + buffer.readableByteCount();
							DataBufferUtils.release(buffer);
							return next;
						})
						.doOnNext(count -> getHeaders().setContentLength(count))
						.then(Mono.empty()));
	}
```

