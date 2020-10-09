package com.javashitang.invoke;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

@Slf4j
public class ConsumerInvocationHandler implements InvocationHandler {

    private String host;
    private Integer port;

    public ConsumerInvocationHandler(String host, Integer prot) {
        this.host = host;
        this.port = prot;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        try (Socket socket = new Socket(host, port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            outputStream.writeUTF(method.getName());
            outputStream.writeObject(args);
            Object result = inputStream.readObject();
            return result;
        } catch (Exception e) {
            log.error("consumer invoke error", e);
            throw new RuntimeException("consumer invoke error");
        }
    }
}
