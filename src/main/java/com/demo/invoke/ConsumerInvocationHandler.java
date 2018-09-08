package com.demo.invoke;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class ConsumerInvocationHandler implements InvocationHandler {

    private String host;
    private Integer prot;

    public ConsumerInvocationHandler(String host, Integer prot) {
        this.host = host;
        this.prot = prot;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Socket socket = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {

            socket = new Socket(host, prot);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(method.getName());
            outputStream.writeObject(args);
            inputStream = new ObjectInputStream(socket.getInputStream());
            Object result = inputStream.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(socket);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
