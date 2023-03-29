package com.lms.Library.Management.System.Service;

import com.lms.Library.Management.System.Entity.*;
import com.lms.Library.Management.System.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    public void addAuthor(AuthorRequestDto authorRequestDto) {
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setEmail(authorRequestDto.getEmail());
        author.setMobNo(authorRequestDto.getMobNo());
        authorRepository.save(author);
    }

    public AllAuthorsResponseDto getAuthorById(int id) {
        Author author;
        try{
            author = authorRepository.findById(id).get();
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        AllAuthorsResponseDto allAuthorsResponseDto = new AllAuthorsResponseDto();
        allAuthorsResponseDto.setName(author.getName());
        allAuthorsResponseDto.setEmail(author.getEmail());
        allAuthorsResponseDto.setMobNo(author.getMobNo());

        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        List<Book> books = author.getBooks();
        for(Book book : books){
            BookResponseDto bookResponseDto = new BookResponseDto();
            bookResponseDto.setTitle(book.getTitle());
            bookResponseDto.setGenre(book.getGenre());
            bookResponseDto.setIssued(book.isIssued());
            bookResponseDto.setPrice(book.getPrice());
            bookResponseDtoList.add(bookResponseDto);
        }
        allAuthorsResponseDto.setBooks(bookResponseDtoList);
        return allAuthorsResponseDto;
    }

    public List<AllAuthorsResponseDto> getAllAuthors(){
        List<Author> authorList;
        try{
            authorList = authorRepository.findAll();
        }
        catch (Exception e){
            throw new RuntimeException();
        }

        List<AllAuthorsResponseDto> allAuthorsResponseDtoList = new ArrayList<>();
        for(Author author : authorList){
            AllAuthorsResponseDto allAuthorsResponseDto = new AllAuthorsResponseDto();
            allAuthorsResponseDto.setName(author.getName());
            allAuthorsResponseDto.setEmail(author.getEmail());
            allAuthorsResponseDto.setMobNo(author.getMobNo());

            List<Book> bookList = author.getBooks();
            List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
            for(Book book : bookList){
                BookResponseDto bookResponseDto = new BookResponseDto();
                bookResponseDto.setTitle(book.getTitle());
                bookResponseDto.setGenre(book.getGenre());
                bookResponseDto.setIssued(book.isIssued());
                bookResponseDto.setPrice(book.getPrice());
                bookResponseDtoList.add(bookResponseDto);
            }
            allAuthorsResponseDto.setBooks(bookResponseDtoList);
            allAuthorsResponseDtoList.add(allAuthorsResponseDto);
        }
        return allAuthorsResponseDtoList;
    }

    public void updateAuthorById(Author author) {
        try{
            authorRepository.findById(author.getId()).get();
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        authorRepository.save(author);
    }

    public AuthorResponseDto updateEmailID(AuthorUpdateEmailRequestDto authorUpdateEmailRequestDto) {
        Author author;
        try{
            author = authorRepository.findById(authorUpdateEmailRequestDto.getId()).get();
        }
        catch(Exception e){
            throw new RuntimeException();
        }

        //update the mobile number of student
        author.setEmail(authorUpdateEmailRequestDto.getEmail());

        //make the Student Response DTO to return
        AuthorResponseDto authorResponseDto = new AuthorResponseDto();
        authorResponseDto.setName(author.getName());
        authorResponseDto.setEmail(author.getEmail());
        authorResponseDto.setMessage("Email Updated Successfully");

        return authorResponseDto;
    }
}
