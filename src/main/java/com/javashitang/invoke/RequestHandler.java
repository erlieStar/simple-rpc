package com.javashitang.invoke;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class RequestHandler implements Runnable {

    private Object service;
    private Socket socket;

    public RequestHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void run() {

        try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
            String methodName = inputStream.readUTF();
            Object[] arguments = (Object[]) inputStream.readObject();
            // 这里用了org.apache.commons.lang3中的工具方法 MethodUtils.invokeExactMethod对服务实现类发起反射调用
            // 当然你也可以用Java原生api反射调用类的方法
            Object result = MethodUtils.invokeExactMethod(service, methodName, arguments);
            outputStream.writeObject(result);
        } catch (Exception e) {
            log.error("invoke method error", e);
            throw new RuntimeException("invoke method error");
        }
    }

}

