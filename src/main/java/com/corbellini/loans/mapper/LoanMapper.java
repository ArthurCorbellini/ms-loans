package com.corbellini.loans.mapper;

import com.corbellini.loans.dto.LoanDto;
import com.corbellini.loans.entity.Loan;

public class LoanMapper {

  public static LoanDto mapToLoanDto(Loan loans, LoanDto loansDto) {
    loansDto.setLoanNumber(loans.getLoanNumber());
    loansDto.setLoanType(loans.getLoanType());
    loansDto.setMobileNumber(loans.getMobileNumber());
    loansDto.setTotalLoan(loans.getTotalLoan());
    loansDto.setAmountPaid(loans.getAmountPaid());
    loansDto.setOutstandingAmount(loans.getOutstandingAmount());
    return loansDto;
  }

  public static Loan mapToLoan(LoanDto loansDto, Loan loans) {
    loans.setLoanNumber(loansDto.getLoanNumber());
    loans.setLoanType(loansDto.getLoanType());
    loans.setMobileNumber(loansDto.getMobileNumber());
    loans.setTotalLoan(loansDto.getTotalLoan());
    loans.setAmountPaid(loansDto.getAmountPaid());
    loans.setOutstandingAmount(loansDto.getOutstandingAmount());
    return loans;
  }
}
