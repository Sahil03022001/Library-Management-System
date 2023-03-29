package com.lms.Library.Management.System.Service;

import com.lms.Library.Management.System.Entity.*;
import com.lms.Library.Management.System.Enum.CardStatus;
import com.lms.Library.Management.System.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void addStudent(StudentRequestDto studentRequestDto) {

        Student student = new Student();
        student.setName(studentRequestDto.getName());
        student.setDob(studentRequestDto.getDob());
        student.setMobNo(studentRequestDto.getMobNo());
        student.setDepartment(studentRequestDto.getDepartment());

        //set card details
        LibraryCard card = new LibraryCard();
        card.setValidTill("07/2027");
        card.setCardStatus(CardStatus.ACTIVATED);
        card.setStudent(student);
        student.setCard(card);

        studentRepository.save(student);
    }

    public AllStudentResponseDto getStudentById(int id) {
        Student student;
        try {
            student = studentRepository.findById(id).get();
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        AllStudentResponseDto allStudentResponseDto = new AllStudentResponseDto();
        allStudentResponseDto.setName(student.getName());
        allStudentResponseDto.setDepartment(student.getDepartment());
        allStudentResponseDto.setDob(student.getDob());
        allStudentResponseDto.setMobNo(student.getMobNo());
        return allStudentResponseDto;
    }

    public List<AllStudentResponseDto> getAllStudents() {
        List<Student> list = studentRepository.findAll();
        List<AllStudentResponseDto> allStudentList = new ArrayList<>();
        for(Student student : list){
            AllStudentResponseDto allStudentResponseDto = new AllStudentResponseDto();
            allStudentResponseDto.setName(student.getName());
            allStudentResponseDto.setDepartment(student.getDepartment());
            allStudentResponseDto.setDob(student.getDob());
            allStudentResponseDto.setMobNo(student.getMobNo());
            allStudentList.add(allStudentResponseDto);
        }
        return allStudentList;
    }

    public void updateStudentById(Student student) {
        try{
            studentRepository.findById(student.getId()).get();
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        studentRepository.save(student);
    }

    public List<AllStudentResponseDto> getAllStudentsByName(String name) {
        List<Student> students = studentRepository.findAll();
        List<AllStudentResponseDto> studentList = new ArrayList<>();
        for(Student student : students){
            if(student.getName().equals(name)){
                AllStudentResponseDto allStudentResponseDto = new AllStudentResponseDto();
                allStudentResponseDto.setName(student.getName());
                allStudentResponseDto.setDob(student.getDob());
                allStudentResponseDto.setMobNo(student.getMobNo());
                allStudentResponseDto.setDepartment(student.getDepartment());
                studentList.add(allStudentResponseDto);
            }
        }
        return studentList;
    }

    public List<AllStudentResponseDto> getStudentByMobileNo(String mobNo) {
        List<Student> students = studentRepository.findAll();
        List<AllStudentResponseDto> studentList = new ArrayList<>();
        for(Student student : students){
            if(student.getMobNo().equals(mobNo)){
                AllStudentResponseDto allStudentResponseDto = new AllStudentResponseDto();
                allStudentResponseDto.setName(student.getName());
                allStudentResponseDto.setDob(student.getDob());
                allStudentResponseDto.setMobNo(student.getMobNo());
                allStudentResponseDto.setDepartment(student.getDepartment());
                studentList.add(allStudentResponseDto);
            }
        }
        return studentList;
    }

    public StudentResponseDto updateMobileNo(StudentUpdateMobNoRequestDto studentUpdateMobNoRequestDto) {
        Student student;
        try{
            student = studentRepository.findById(studentUpdateMobNoRequestDto.getId()).get();
        }
        catch(Exception e){
            throw new RuntimeException();
        }

        //update the mobile number of student
        student.setMobNo(studentUpdateMobNoRequestDto.getMobNo());

        //make the Student Response DTO to return
        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setId(student.getId());
        studentResponseDto.setName(student.getName());
        studentResponseDto.setMobNo(student.getMobNo());

        return studentResponseDto;
    }

    public List<BookResponseDto> getAllIssuedBooksByStudent(int id) throws Exception {
        Student student;
        try{
            student = studentRepository.findById(id).get();
        }
        catch (Exception e){
            throw new Exception("Invalid Student ID");
        }

        LibraryCard card = student.getCard();
        List<Book> bookList = card.getBooksIssued();
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();

        for(Book book : bookList){
            BookResponseDto bookResponseDto = new BookResponseDto();
            bookResponseDto.setTitle(book.getTitle());
            bookResponseDto.setPrice(book.getPrice());
            bookResponseDto.setIssued(book.isIssued());
            bookResponseDto.setGenre(book.getGenre());
            bookResponseDto.setAuthorName(book.getAuthor().getName());
            bookResponseDtoList.add(bookResponseDto);
        }

        return bookResponseDtoList;
    }
}
