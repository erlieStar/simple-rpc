package com.javashitang;

import com.javashitang.api.HelloService;
import com.javashitang.api.UpperCaseService;
import com.javashitang.service.HelloServiceImpl;
import com.javashitang.service.UpperCaseServiceImpl;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcProviderMain {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {

        HelloService helloService = new HelloServiceImpl();
        UpperCaseService upperCaseService = new UpperCaseServiceImpl();
        // 将需要暴露的接口注册到serviceMap中
        ServiceMap.registerService(HelloService.class.getName(), helloService);
        ServiceMap.registerService(UpperCaseService.class.getName(), upperCaseService);

        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            // 获取一个套接字（阻塞）。所以为了并行，来一个请求，开一个线程处理
            // 为了复用线程，用了threadPool
            final Socket socket = serverSocket.accept();
            executorService.execute(new RequestHandler(socket));
        }
    }
}
