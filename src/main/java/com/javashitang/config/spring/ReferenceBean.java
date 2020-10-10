package com.javashitang.config.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author lilimin
 * @since 2020-09-22
 */
public class ReferenceBean<T> implements FactoryBean {

    private transient volatile T ref;

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
