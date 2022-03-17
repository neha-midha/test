package com.globant.tc.scooter.accounts.model.mercadoPago;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BuyCredits
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-17T14:05:34.657326900+05:30[Asia/Calcutta]")
public class BuyCredits   {
  @JsonProperty("mercadoPagoAccountId")
  private Integer mercadoPagoAccountId;

  @JsonProperty("credits")
  private Double credits;

  public BuyCredits mercadoPagoAccountId(Integer mercadoPagoAccountId) {
    this.mercadoPagoAccountId = mercadoPagoAccountId;
    return this;
  }

  /**
   * Get mercadoPagoAccountId
   * @return mercadoPagoAccountId
  */
  @ApiModelProperty(value = "")


  public Integer getMercadoPagoAccountId() {
    return mercadoPagoAccountId;
  }

  public void setMercadoPagoAccountId(Integer mercadoPagoAccountId) {
    this.mercadoPagoAccountId = mercadoPagoAccountId;
  }

  public BuyCredits credits(Double credits) {
    this.credits = credits;
    return this;
  }

  /**
   * Get credits
   * @return credits
  */
  @ApiModelProperty(value = "")


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
    BuyCredits buyCredits = (BuyCredits) o;
    return Objects.equals(this.mercadoPagoAccountId, buyCredits.mercadoPagoAccountId) &&
        Objects.equals(this.credits, buyCredits.credits);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mercadoPagoAccountId, credits);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BuyCredits {\n");
    
    sb.append("    mercadoPagoAccountId: ").append(toIndentedString(mercadoPagoAccountId)).append("\n");
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

