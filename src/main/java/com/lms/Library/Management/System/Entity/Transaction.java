package com.lms.Library.Management.System.Entity;

import com.lms.Library.Management.System.Enum.IssueType;
import com.lms.Library.Management.System.Enum.TransactionStatus;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transaction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String transactionNumber;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date transactionDate;

    private String message;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @JoinColumn
    @ManyToOne
    Book book;

    @JoinColumn
    @ManyToOne
    LibraryCard card;
}