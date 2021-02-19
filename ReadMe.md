# Test 

调用获取列表的接口在 AnswerClass 类中。

我的工作
在utils包里写了一点并发调用的工具类
让网关层实现了 concurrentRpc 抽象类完成并发的异步调用RPC。
1. 性能提升点在于，使用线程池管理rpc异步调用的线程，实现线程复用，减少线程创建的开销，提升调用性能
2. 并发调用的基类 concurrentRpc 可以实现代码复用