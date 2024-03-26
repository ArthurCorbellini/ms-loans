package com.artcorb.loans.cfg;

import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "loans")
public class LoansEnvironments {

  private String message;
  private Map<String, String> contactDetails;
  private List<String> onCallSupport;
}
