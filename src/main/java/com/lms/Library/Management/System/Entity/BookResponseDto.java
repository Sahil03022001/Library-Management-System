package com.lms.Library.Management.System.Entity;

import com.lms.Library.Management.System.Enum.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookResponseDto {

    private String title;
    private int price;
    private boolean isIssued;
    private Genre genre;
    private String authorName;
}
