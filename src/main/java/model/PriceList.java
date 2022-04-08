package model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceList {

  private BigDecimal minutes;
  private BigDecimal sms;
}
