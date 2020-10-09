package com.javashitang.main;

import com.javashitang.invoke.RequestHandler;
import com.javashitang.invoke.ServiceMap;
import com.javashitang.service.HelloService;
import com.javashitang.service.HelloServiceImpl;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProviderMain {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {

        HelloService helloService = new HelloServiceImpl();
        // 将需要暴露的接口放到serviceMap中
        ServiceMap.putService(helloService.getClass().getName(), helloService.getClass());

        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            // 获取一个套接字（阻塞）。所以为了并行，来一个请求，开一个线程处理
            // 为了复用线程，用了threadPool
            final Socket socket = serverSocket.accept();
            executorService.execute(new RequestHandler(socket));
        }
    }
}
