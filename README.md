# event-dog
java实现的js线程模型

使用方法：
class MyTask extends AsyncTask<Request, Response>{}


Eventloop loop = new Eventloop();

loop.submit(MyTask(Request));

loop.start();
