package com.javashitang.invoke;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProviderReflect {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void invoke(final Object service, int port) throws Exception {

        ServerSocket serverSocket = new ServerSocket(port);

        while (true) {
            // 获取一个套接字（阻塞）。所以为了并行，来一个请求，开一个线程处理
            // 为了复用线程，用了threadPool，这是用bio进行网络通信时业内的一般做法
            final Socket socket = serverSocket.accept();
            executorService.execute(new RequestHandler(service, socket));
        }
    }
}
