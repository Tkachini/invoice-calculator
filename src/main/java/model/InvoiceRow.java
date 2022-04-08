package model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceRow {

  public InvoiceRow(String serviceName) {
    this.serviceName = serviceName;
  }

  private String serviceName;
  private BigDecimal amount = BigDecimal.ZERO;
  private BigDecimal price = BigDecimal.ZERO;
}
