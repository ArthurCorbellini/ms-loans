package com.artcorb.loans.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.artcorb.loans.cfg.LoansEnvironments;
import com.artcorb.loans.controller.base.BaseController;
import com.artcorb.loans.dto.LoanDto;
import com.artcorb.loans.dto.ResponseDto;
import com.artcorb.loans.dto.ResponseErrorDto;
import com.artcorb.loans.service.ILoanService;
import com.artcorb.loans.util.FilterUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(name = "CRUD REST API for Loans", description = "CREATE, READ, UPDATE and DELETE loans")
@RestController
@Validated
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoanController extends BaseController {

  private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

  private ILoanService iLoanService;
  private LoansEnvironments environmentConfig;

  @Operation(summary = "Create Loan REST API", description = "REST API to create new loan")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_201),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PostMapping("/create")
  public ResponseEntity<ResponseDto> createLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
      message = "Mobile number must be 10 digits") String mobileNumber) {
    iLoanService.createLoan(mobileNumber);
    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(STATUS_201, MESSAGE_201));
  }

  @Operation(summary = "Fetch Loan REST API",
      description = "REST API to fetch loan based on a mobile number")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/fetch")
  public ResponseEntity<LoanDto> fetchLoanDetails(
      @RequestHeader(FilterUtil.CORRELATION_ID) String correlationId,
      @RequestParam @Pattern(regexp = "(^$|[0-9]{10})",
          message = "Mobile number must be 10 digits") String mobileNumber) {
    logger.debug("fetchLoanDetails method start");
    LoanDto dto = iLoanService.fetchLoan(mobileNumber);
    logger.debug("fetchLoanDetails method end");
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

  @Operation(summary = "Update Loan REST API",
      description = "REST API to update loan based on a loan number")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_417, description = MESSAGE_417_UPDATE),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @PutMapping("/update")
  public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoanDto dto) {
    if (iLoanService.updateLoan(dto)) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(STATUS_417, MESSAGE_417_UPDATE));
    }
  }

  @Operation(summary = "Delete Loan REST API",
      description = "REST API to delete Loan based on a mobile number")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_417, description = MESSAGE_417_DELETE),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @DeleteMapping("/delete")
  public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam @Pattern(
      regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
    if (iLoanService.deleteLoan(mobileNumber)) {
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(STATUS_200, MESSAGE_200));
    } else {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
          .body(new ResponseDto(STATUS_417, MESSAGE_417_DELETE));
    }
  }

  @Operation(summary = "Get Contact Info",
      description = "Contact Info details that can be reached out in case of any issues")
  @ApiResponses({@ApiResponse(responseCode = STATUS_200, description = MESSAGE_200),
      @ApiResponse(responseCode = STATUS_500, description = MESSAGE_500,
          content = @Content(schema = @Schema(implementation = ResponseErrorDto.class)))})
  @GetMapping("/contact-info")
  public ResponseEntity<LoansEnvironments> getContactInfo() {
    return ResponseEntity.status(HttpStatus.OK).body(environmentConfig);
  }
}
