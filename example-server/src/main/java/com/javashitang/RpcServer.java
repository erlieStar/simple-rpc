package com.javashitang;

import com.javashitang.impl.StudentServiceImpl;
import com.javashitang.remoting.transport.NettyServer;
import com.javashitang.service.ServiceMap;


public class RpcServer {

    public static void main( String[] args ) {
        StudentService studentService = new StudentServiceImpl();
        // 向注册中心注册
        ServiceMap.registryService(StudentService.class.getName(), studentService);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }
}
