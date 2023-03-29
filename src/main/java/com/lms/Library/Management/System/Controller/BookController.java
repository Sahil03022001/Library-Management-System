package com.lms.Library.Management.System.Controller;

import com.lms.Library.Management.System.Entity.Book;
import com.lms.Library.Management.System.Entity.BookRequestDto;
import com.lms.Library.Management.System.Entity.BookResponseDto;
import com.lms.Library.Management.System.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add")
    public ResponseEntity addBook(@RequestBody BookRequestDto bookRequestDto){
        try{
            bookService.addBook(bookRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity<>("Book not Added", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Book Added", HttpStatus.CREATED);
    }

    @GetMapping("/get-book")
    public ResponseEntity getBookById(@RequestParam("id") int id){
        BookResponseDto bookResponseDto;
        try{
            bookResponseDto = bookService.getBookById(id);
        }
        catch (Exception e){
            return new ResponseEntity<>("Book not found", HttpStatus.OK);
        }
        return new ResponseEntity<>(bookResponseDto, HttpStatus.OK);
    }

    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        List<BookResponseDto> bookList = bookService.getAllBooks();
        if(bookList.size() == 0){
            return new ResponseEntity<>("No Books found", HttpStatus.OK);
        }
        return new ResponseEntity<>(bookList, HttpStatus.OK);
    }

    @DeleteMapping("/delete-book")
    public ResponseEntity deleteBookById(@RequestParam int id){
        try{
            bookService.deleteBookById(id);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Book Removed!", HttpStatus.OK);
    }
}
