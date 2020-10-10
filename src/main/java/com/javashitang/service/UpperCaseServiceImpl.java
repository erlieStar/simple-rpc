package com.javashitang.service;

/**
 * @author lilimin
 * @since 2020-10-10
 */
public class UpperCaseServiceImpl implements UpperCaseService {

    @Override
    public String toUpperCase(String content) {
        return content.toUpperCase();
    }
}
