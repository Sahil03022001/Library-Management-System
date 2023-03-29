package com.lms.Library.Management.System.Repository;

import com.lms.Library.Management.System.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

//    @Query(value = "select * from transaction t where t.book_id=:bookId and t.card_card_no=:cardId and t.issue_type='ISSUED'", nativeQuery = true)
//    Transaction getTxnOfCardIdAndBookId(int cardId, int bookId);
}
