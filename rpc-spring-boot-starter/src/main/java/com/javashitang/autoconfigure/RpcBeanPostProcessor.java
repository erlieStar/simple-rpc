package com.javashitang.autoconfigure;

import com.javashitang.annotation.RpcReference;
import com.javashitang.annotation.RpcService;
import com.javashitang.proxy.ConsumerProxy;
import com.javashitang.service.ServiceMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import java.lang.reflect.Field;

/**
 * @author lilimin
 * @since 2021-06-03
 */
public class RpcBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RpcService rpcService = bean.getClass().getAnnotation(RpcService.class);
        if (rpcService != null) {
            ServiceMap.registryService(this.getServiceKey(bean), bean);
        }
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RpcReference rpcReference = bean.getClass().getAnnotation(RpcReference.class);
            if (rpcReference != null) {
                Object proxy = ConsumerProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, proxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

    public String getServiceKey(Object bean) {
        return bean.getClass().getInterfaces()[0].getCanonicalName();
    }

}
