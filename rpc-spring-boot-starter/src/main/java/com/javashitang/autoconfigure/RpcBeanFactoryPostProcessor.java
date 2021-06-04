package com.javashitang.autoconfigure;

import com.javashitang.annotation.RpcService;
import com.javashitang.remoting.transport.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @author lilimin
 * @since 2021-06-03
 */
@Slf4j
@Component
public class RpcBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if (beanDefinition instanceof AnnotatedBeanDefinition) {
                AnnotationMetadata metadata = ((AnnotatedBeanDefinition) beanDefinition).getMetadata();
                if (metadata.isAnnotated(RpcService.class.getName())) {
                    NettyServer nettyServer = new NettyServer();
                    nettyServer.start();
                    break;
                }
            }
        }
    }
}
