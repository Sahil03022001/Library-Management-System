package com.lms.Library.Management.System.Service;

import com.lms.Library.Management.System.Entity.Author;
import com.lms.Library.Management.System.Entity.Book;
import com.lms.Library.Management.System.Entity.BookRequestDto;
import com.lms.Library.Management.System.Entity.BookResponseDto;
import com.lms.Library.Management.System.Repository.AuthorRepository;
import com.lms.Library.Management.System.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public void addBook(BookRequestDto bookRequestDto) throws Exception{
        Author author;
        try{
            author = authorRepository.findById(bookRequestDto.getAuthor_id()).get();
        }
        catch(Exception e){
            throw new RuntimeException();
        }

        // add book details to book object
        Book book = new Book();
        book.setTitle(bookRequestDto.getTitle());
        book.setPrice(bookRequestDto.getPrice());
        book.setGenre(bookRequestDto.getGenre());
        book.setIssued(false);

        //saving the book to author's books list
        author.getBooks().add(book);
        book.setAuthor(author);
        authorRepository.save(author);
    }

    public BookResponseDto getBookById(int id) {
        Book book;
        try{
            book = bookRepository.findById(id).get();
        }
        catch (Exception e){
            throw new RuntimeException();
        }

        Author author = authorRepository.findById(book.getAuthor().getId()).get();
        BookResponseDto bookResponseDto = new BookResponseDto();
        bookResponseDto.setTitle(book.getTitle());
        bookResponseDto.setPrice(book.getPrice());
        bookResponseDto.setGenre(book.getGenre());
        bookResponseDto.setIssued(book.isIssued());
        bookResponseDto.setAuthorName(author.getName());
        return bookResponseDto;
    }

    public List<BookResponseDto> getAllBooks() {
        List<Book> allBooks = bookRepository.findAll();
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for(Book book : allBooks){
            BookResponseDto bookResponseDto = new BookResponseDto();
            bookResponseDto.setTitle(book.getTitle());
            bookResponseDto.setPrice(book.getPrice());
            bookResponseDto.setGenre(book.getGenre());
            bookResponseDto.setIssued(book.isIssued());

            Author author = authorRepository.findById(book.getAuthor().getId()).get();
            bookResponseDto.setAuthorName(author.getName());

            bookResponseDtoList.add(bookResponseDto);
        }
        return bookResponseDtoList;
    }

    public void deleteBookById(int id) throws Exception {
        Book book;
        try{
            book = bookRepository.findById(id).get();
        }
        catch (Exception e){
            throw new Exception("Invalid Book ID");
        }

        bookRepository.deleteById(id);
    }
}
