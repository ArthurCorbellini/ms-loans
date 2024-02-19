package com.corbellini.loans.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.corbellini.loans.constants.DefaultConstants;
import com.corbellini.loans.dto.ResponseDto;
import com.corbellini.loans.dto.ResponseErrorDto;
import com.corbellini.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(name = "CRUD REST API for Loans", description = "CREATE, READ, UPDATE and DELETE loans")
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoanController {

  private ILoanService iLoanService;

  @Operation(summary = "Create Loan REST API", description = "REST API to create new loan")
  @ApiResponses({
      @ApiResponse(responseCode = DefaultConstants.STATUS_201,
          description = DefaultConstants.MESSAGE_201),
      @ApiResponse(responseCode = DefaultConstants.STATUS_500,
          description = DefaultConstants.MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber) {
    iLoanService.createLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new ResponseDto(DefaultConstants.STATUS_201, DefaultConstants.MESSAGE_201));
  }

}
