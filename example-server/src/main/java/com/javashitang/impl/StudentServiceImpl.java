package com.javashitang.impl;

import com.javashitang.Student;
import com.javashitang.StudentService;

/**
 * @author lilimin
 * @since 2020-10-25
 */
public class StudentServiceImpl implements StudentService {

    @Override
    public Student getStudentInfo(Integer id) {
        Student student = Student.builder().id(id).name("test").age(10).build();
        return student;
    }
}
