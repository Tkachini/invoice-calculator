package model;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvoiceTotal {

  private PackageRate packageRate;
  private List<InvoiceRow> rows;
  private BigDecimal totalPrice;
}
