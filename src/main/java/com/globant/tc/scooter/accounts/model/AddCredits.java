package com.globant.tc.scooter.accounts.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AddCredits
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-17T14:05:33.941416700+05:30[Asia/Calcutta]")
public class AddCredits   {
  @JsonProperty("accountId")
  private Integer accountId;

  @JsonProperty("credits")
  private Double credits;

  public AddCredits accountId(Integer accountId) {
    this.accountId = accountId;
    return this;
  }

  /**
   * Get accountId
   * @return accountId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public AddCredits credits(Double credits) {
    this.credits = credits;
    return this;
  }

  /**
   * Get credits
   * minimum: 0.0
   * @return credits
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull

@DecimalMin(value="0.0",inclusive=false)
  public Double getCredits() {
    return credits;
  }

  public void setCredits(Double credits) {
    this.credits = credits;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddCredits addCredits = (AddCredits) o;
    return Objects.equals(this.accountId, addCredits.accountId) &&
        Objects.equals(this.credits, addCredits.credits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, credits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCredits {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    credits: ").append(toIndentedString(credits)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

