# event-dog
参考js事件回调模型的java版

使用方法：
```java
class MyTask extends AsyncTask<Request, Response>{
    
    //耗时IO操作，request为请求参数
    Response execute(Request request){
        //...code...
        //Response为操作结果，回调时会作为回调响应参数
        return new Response();
    }
    
    //回调函数
    void callback(Response response){
        ///code
    }
}

//可作为全局变量，表示全局就一个eventloop重复提交任务
Eventloop loop = new Eventloop();

//提交任务
loop.submit(MyTask(Request));

//启动事件轮询，此方法只需执行一次
loop.start();
