package com.javashitang.controller;

import com.javashitang.Student;
import com.javashitang.StudentService;
import com.javashitang.annotation.RpcReference;
import com.javashitang.tool.OperStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lilimin
 * @since 2021-06-03
 */
@Controller
public class StudentController {

    @RpcReference
    private StudentService studentService;

    @RequestMapping("getStudentInfo")
    private OperStatus<Student> getStudentInfo(@RequestParam Integer id) {
        Student studentInfo = studentService.getStudentInfo(id);
        return OperStatus.newSuccess(studentInfo);
    }
}
