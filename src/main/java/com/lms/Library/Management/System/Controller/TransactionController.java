package com.lms.Library.Management.System.Controller;

import com.lms.Library.Management.System.Entity.IssueBookRequestDto;
import com.lms.Library.Management.System.Entity.ReturnBookRequestDto;
import com.lms.Library.Management.System.Entity.Transaction;
import com.lms.Library.Management.System.Service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/issue-book")
    public ResponseEntity issueBook(@RequestBody IssueBookRequestDto issueBookRequestDto){
        try{
            transactionService.issueBook(issueBookRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Transaction was successful. Book Issued", HttpStatus.OK);
    }

    @GetMapping("/return-book")
    public ResponseEntity returnBook(@RequestBody ReturnBookRequestDto returnBookRequestDto){
        try{
            transactionService.returnBook(returnBookRequestDto);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Transaction Successful. Book Returned", HttpStatus.ACCEPTED);
    }
}