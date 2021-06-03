package com.javashitang.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lilimin
 * @since 2021-06-03
 */
@Configuration
public class RpcAutoConfiguration {

    @Bean
    public RpcBeanPostProcessor rpcBeanPostProcessor() {
        RpcBeanPostProcessor rpcBeanPostProcessor = new RpcBeanPostProcessor();
        return rpcBeanPostProcessor;
    }
}
