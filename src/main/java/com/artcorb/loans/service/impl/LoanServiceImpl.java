package com.artcorb.loans.service.impl;

import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import com.artcorb.loans.dto.LoanDto;
import com.artcorb.loans.entity.Loan;
import com.artcorb.loans.exception.LoanAlreadyExistsException;
import com.artcorb.loans.exception.ResourceNotFoundException;
import com.artcorb.loans.mapper.LoanMapper;
import com.artcorb.loans.repository.LoanRepository;
import com.artcorb.loans.service.ILoanService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {

  private LoanRepository loanRepository;

  @Override
  public void createLoan(String mobileNumber) {
    Optional<Loan> optionalLoan = loanRepository.findByMobileNumber(mobileNumber);
    if (optionalLoan.isPresent()) {
      throw new LoanAlreadyExistsException(
          "Loan already registered with given mobileNumber " + mobileNumber);
    }
    loanRepository.save(createNewLoan(mobileNumber));
  }

  /**
   * @param mobileNumber - Mobile Number of the Customer
   * @return the new loan details
   */
  private Loan createNewLoan(String mobileNumber) {
    Loan newLoan = new Loan();
    long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
    newLoan.setLoanNumber(Long.toString(randomLoanNumber));
    newLoan.setMobileNumber(mobileNumber);
    newLoan.setLoanType("Default loan type");
    newLoan.setTotalLoan(1000);
    newLoan.setAmountPaid(0);
    newLoan.setOutstandingAmount(2000);

    return newLoan;
  }

  @Override
  public LoanDto fetchLoan(String mobileNumber) {
    Loan loan = loanRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    return LoanMapper.mapToLoanDto(loan, new LoanDto());
  }

  @Override
  public boolean updateLoan(LoanDto loanDto) {
    Loan loan = loanRepository.findByLoanNumber(loanDto.getLoanNumber()).orElseThrow(
        () -> new ResourceNotFoundException("Loan", "LoanNumber", loanDto.getLoanNumber()));
    loanRepository.save(LoanMapper.mapToLoan(loanDto, loan));
    return true;
  }

  @Override
  public boolean deleteLoan(String mobileNumber) {
    Loan loan = loanRepository.findByMobileNumber(mobileNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber));
    loanRepository.deleteById(loan.getLoanId());
    return true;
  }
}
