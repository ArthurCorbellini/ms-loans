package com.corbellini.loans.entity;

import com.corbellini.loans.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Loan extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long loanId;

  private String mobileNumber;

  private String loanNumber;

  private String loanType;

  private Long totalLoan;

  private Long amountPaid;

  private Long outstandingAmount;
}
