# 《MyBatis应用分析与最佳实践》课后作业

1. resultType与resutlMap的区别？
   - resultType是类的全限名或别名
   - resultMap可以是由用户自定义的复杂类型，二者二选一
   - 不管是resultMap, 还是resultType，Mybatis都会将它们解析后，作为resultMap处理
2. collection和association区别
   - collection：一对多
   - association：一对一
3. Statement和PreparedStatement区别
   - Statement：静态sql对象
   - PreparedStatement：预编译sql对象，可有效防止sql注入