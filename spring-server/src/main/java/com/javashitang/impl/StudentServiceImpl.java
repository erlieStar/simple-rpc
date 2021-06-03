package com.javashitang.impl;

import com.javashitang.Student;
import com.javashitang.StudentService;
import com.javashitang.annotation.RpcService;

/**
 * @author lilimin
 * @since 2021-06-03
 */
@RpcService
public class StudentServiceImpl implements StudentService {

    @Override
    public Student getStudentInfo(Integer id) {
        Student student = Student.builder().id(id).name("test").age(10).build();
        return student;
    }
}
