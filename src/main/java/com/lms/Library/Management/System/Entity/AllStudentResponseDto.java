package com.lms.Library.Management.System.Entity;

import com.lms.Library.Management.System.Enum.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllStudentResponseDto {

    private String name;
    private String dob;
    private String mobNo;
    private Department department;
}
