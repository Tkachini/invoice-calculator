package model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Usage {

  private Long clientId;
  private Long serviceId;
  private BigDecimal amountUsed;
}
