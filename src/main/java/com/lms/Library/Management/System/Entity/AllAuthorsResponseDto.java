package com.lms.Library.Management.System.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AllAuthorsResponseDto {

    private String name;
    private String email;
    private String mobNo;
    private List<BookResponseDto> books;
}
