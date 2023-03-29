package com.lms.Library.Management.System.Controller;

import com.lms.Library.Management.System.Entity.*;
import com.lms.Library.Management.System.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @PostMapping("/add")
    public ResponseEntity addStudent(@RequestBody StudentRequestDto studentRequestDto){
        studentService.addStudent(studentRequestDto);
        return new ResponseEntity<>("Student added", HttpStatus.CREATED);
    }

    @GetMapping("/get-student")
    public ResponseEntity getStudentById(@RequestParam("id") int id){
        AllStudentResponseDto allStudentResponseDto;
        try{
            allStudentResponseDto = studentService.getStudentById(id);
        }
        catch (Exception e){
            return new ResponseEntity<>("Student not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(allStudentResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get-all-students")
    public ResponseEntity getAllStudents(){
        List<AllStudentResponseDto> studentList = studentService.getAllStudents();
        if(studentList.size() == 0){
            return new ResponseEntity<>("No Students found", HttpStatus.OK);
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @PutMapping("/update-student")
    public ResponseEntity updateStudentById(@RequestBody Student student){
        try{
            studentService.updateStudentById(student);
        }
        catch (Exception e){
            return new ResponseEntity<>("Student not found", HttpStatus.OK);
        }
        return new ResponseEntity<>("Student updated", HttpStatus.OK);
    }

    @GetMapping("/get-students-by-name/{name}")
    public ResponseEntity getAllStudentsByName(@PathVariable String name){
        List<AllStudentResponseDto> studentList = studentService.getAllStudentsByName(name);
        if(studentList.size() == 0){
            return new ResponseEntity<>("No student found with given name", HttpStatus.OK);
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/getStudent")
    public ResponseEntity getStudentByMobileNo(@RequestParam("mobNo") String mobNo){
        List<AllStudentResponseDto> allStudentResponseDto;
        try{
            allStudentResponseDto = studentService.getStudentByMobileNo(mobNo);
        }
        catch (Exception e){
            return new ResponseEntity<>("Given mobile number not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(allStudentResponseDto, HttpStatus.OK);
    }

    @PutMapping("/update-mobNo")
    public ResponseEntity updateMobileNo(@RequestBody StudentUpdateMobNoRequestDto studentUpdateMobNoRequestDto){
        StudentResponseDto studentResponseDto;
        try{
            studentResponseDto = studentService.updateMobileNo(studentUpdateMobNoRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity<>("Student not found with given ID", HttpStatus.OK);
        }
        return new ResponseEntity<>(studentResponseDto, HttpStatus.OK);
    }
}
