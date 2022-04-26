package com.javashitang.service;

import com.javashitang.api.UpperCaseService;

public class UpperCaseServiceImpl implements UpperCaseService {

    @Override
    public String toUpperCase(String content) {
        return content.toUpperCase();
    }
}
