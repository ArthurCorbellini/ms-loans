package com.corbellini.loans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.corbellini.loans.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

}
