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
 * LinkAccountToMercadoPago
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-03-17T14:05:33.941416700+05:30[Asia/Calcutta]")
public class LinkAccountToMercadoPago   {
  @JsonProperty("accountId")
  private Integer accountId;

  @JsonProperty("mercadoPagoId")
  private Integer mercadoPagoId;

  public LinkAccountToMercadoPago accountId(Integer accountId) {
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

  public LinkAccountToMercadoPago mercadoPagoId(Integer mercadoPagoId) {
    this.mercadoPagoId = mercadoPagoId;
    return this;
  }

  /**
   * Get mercadoPagoId
   * @return mercadoPagoId
  */
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getMercadoPagoId() {
    return mercadoPagoId;
  }

  public void setMercadoPagoId(Integer mercadoPagoId) {
    this.mercadoPagoId = mercadoPagoId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LinkAccountToMercadoPago linkAccountToMercadoPago = (LinkAccountToMercadoPago) o;
    return Objects.equals(this.accountId, linkAccountToMercadoPago.accountId) &&
        Objects.equals(this.mercadoPagoId, linkAccountToMercadoPago.mercadoPagoId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, mercadoPagoId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LinkAccountToMercadoPago {\n");
    
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    mercadoPagoId: ").append(toIndentedString(mercadoPagoId)).append("\n");
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

