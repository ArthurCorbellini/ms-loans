package com.corbellini.loans.service.impl;

import java.util.Optional;
import java.util.Random;
import org.springframework.stereotype.Service;
import com.corbellini.loans.dto.LoanDto;
import com.corbellini.loans.entity.Loan;
import com.corbellini.loans.exception.LoanAlreadyExistsException;
import com.corbellini.loans.repository.LoanRepository;
import com.corbellini.loans.service.ILoanService;
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
    newLoan.setTotalLoan(1000L);
    newLoan.setAmountPaid(0L);
    newLoan.setOutstandingAmount(2000L);

    return newLoan;
  }

  @Override
  public LoanDto fetchLoan(String mobileNumber) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fetchLoan'");
  }

  @Override
  public boolean updateLoan(LoanDto loanDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'updateLoan'");
  }

  @Override
  public boolean deleteLoan(String mobileNumber) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteLoan'");
  }

}
