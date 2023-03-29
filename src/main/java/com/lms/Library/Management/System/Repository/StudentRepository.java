package com.lms.Library.Management.System.Repository;

import com.lms.Library.Management.System.Entity.AllStudentResponseDto;
import com.lms.Library.Management.System.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByMobNo(String mobNo);
}
