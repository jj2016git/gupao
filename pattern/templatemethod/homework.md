模板方法模式要点：
1. 抽象类：定义主流程
2. 钩子函数：作为主流程的分支条件，可由子类覆盖，实现对主流程的微调
3. 子类实现主流程中的某个具体步骤

模板方法实现方式：
1. 继承
2. 接口，如RowMap
