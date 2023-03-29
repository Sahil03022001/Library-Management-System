package com.lms.Library.Management.System.Controller;

import com.lms.Library.Management.System.Entity.*;
import com.lms.Library.Management.System.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity addAuthor(@RequestBody AuthorRequestDto authorRequestDto){
        authorService.addAuthor(authorRequestDto);
        return new ResponseEntity<>("Author Added", HttpStatus.CREATED);
    }

    @GetMapping("/get-author")
    public ResponseEntity getAuthorById(@RequestParam("id") int id){
        AllAuthorsResponseDto allAuthorsResponseDto;
        try{
            allAuthorsResponseDto = authorService.getAuthorById(id);
        }
        catch (Exception e){
            return new ResponseEntity<>("Author not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(allAuthorsResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get-all-authors")
    public ResponseEntity getAllAuthors(){
        List<AllAuthorsResponseDto> authorList;
        try {
            authorList = authorService.getAllAuthors();
        }
        catch (Exception e){
            return new ResponseEntity<>("No Authors found", HttpStatus.OK);
        }
        return new ResponseEntity<>(authorList, HttpStatus.OK);
    }

    @PutMapping("/update-author")
    public ResponseEntity updateStudentById(@RequestBody Author author){
        try{
            authorService.updateAuthorById(author);
        }
        catch (Exception e){
            return new ResponseEntity<>("Author not found", HttpStatus.OK);
        }
        return new ResponseEntity<>("Author updated", HttpStatus.OK);
    }

    @PutMapping("/update-email")
    public ResponseEntity updateMobileNo(@RequestBody AuthorUpdateEmailRequestDto authorUpdateEmailRequestDto){
        AuthorResponseDto authorResponseDto;
        try{
            authorResponseDto = authorService.updateEmailID(authorUpdateEmailRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity<>("Author not found with given ID", HttpStatus.OK);
        }
        return new ResponseEntity<>(authorResponseDto, HttpStatus.OK);
    }
}
