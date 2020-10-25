package com.javashitang;

import lombok.Builder;
import lombok.Data;

/**
 * @author lilimin
 * @since 2020-10-25
 */
@Data
@Builder
public class Student {

    private Integer id;
    private String name;
    private Integer age;
}
