package com.lms.Library.Management.System.Service;

import com.lms.Library.Management.System.Entity.*;
import com.lms.Library.Management.System.Enum.CardStatus;
import com.lms.Library.Management.System.Enum.IssueType;
import com.lms.Library.Management.System.Enum.TransactionStatus;
import com.lms.Library.Management.System.Repository.BookRepository;
import com.lms.Library.Management.System.Repository.CardRepository;
import com.lms.Library.Management.System.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public void issueBook(IssueBookRequestDto issueBookRequestDto) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));

        LibraryCard card;
        try{
            card = cardRepository.findById(issueBookRequestDto.getCardId()).get();
        }
        catch(Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Failed due to invalid card id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card ID");
        }

        Book book;
        try{
            book = bookRepository.findById(issueBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Failed due to invalid book id");
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book ID");
        }

        if(card.getCardStatus() != CardStatus.ACTIVATED){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Failed due to card is not activated");
            transactionRepository.save(transaction);
            throw new Exception("Card is not activated");
        }

        if(book.isIssued()){
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transaction.setMessage("Failed due to book is already issued");
            transactionRepository.save(transaction);
            throw new Exception("Book is already issued");
        }

        //set card and book for transaction
        transaction.setCard(card);
        transaction.setBook(book);

        //i can issue a book
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        transaction.setMessage("Transaction is successful");
        transaction.setIssueType(IssueType.ISSUED);
        book.setIssued(true);
        book.setCard(card);
        book.getTransactionList().add(transaction);
        card.getTransactionList().add(transaction);
        card.getBooksIssued().add(book);

       cardRepository.save(card);
    }

    public void returnBook(ReturnBookRequestDto returnBookRequestDto) throws Exception {

        Transaction transaction = new Transaction();
        transaction.setTransactionNumber(String.valueOf(UUID.randomUUID()));

        LibraryCard card;
        try{
            card = cardRepository.findById(returnBookRequestDto.getCardId()).get();
        }
        catch (Exception e){
            transaction.setMessage("Transaction Failed");
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Invalid Card ID");
        }
        Book book;
        try{
            book = bookRepository.findById(returnBookRequestDto.getBookId()).get();
        }
        catch (Exception e){
            transaction.setMessage("Transaction Failed");
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.save(transaction);
            throw new Exception("Invalid Book ID");
        }

        //check if the given book id is present in card
        List<Book> bookList = card.getBooksIssued();
        for(int i=0; i<bookList.size(); i++){
            if(bookList.get(i).getId() == book.getId()){
                transaction.setMessage("Transaction Successful");
                transaction.setIssueType(IssueType.RETURNED);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);

                transaction.setCard(card);
                transaction.setBook(book);

                book.setIssued(false);
                book.getTransactionList().add(transaction);
                card.getTransactionList().add(transaction);
                bookList.remove(i);
                book.setCard(null);
                transactionRepository.save(transaction);
                return;
            }
        }

        transaction.setMessage("Transaction Failed");
        transaction.setTransactionStatus(TransactionStatus.FAILED);
        transactionRepository.save(transaction);
    }
}