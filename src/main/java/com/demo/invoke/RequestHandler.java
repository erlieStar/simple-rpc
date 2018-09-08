package com.demo.invoke;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private Object service;
    private Socket socket;

    public RequestHandler(Object service, Socket socket) {
        this.service = service;
        this.socket = socket;
    }

    @Override
    public void run() {

        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            String methodName = inputStream.readUTF();
            Object[] arguments = (Object[]) inputStream.readObject();
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            // 这里用了org.apache.commons.lang3中的工具方法 MethodUtils.invokeExactMethod对服务实现类发起反射调用
            // 当然你也可以用Java原生api反射调用类的方法
            Object result = MethodUtils.invokeExactMethod(service, methodName, arguments);
            outputStream.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

}

