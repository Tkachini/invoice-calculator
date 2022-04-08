package model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PackageRate {

  private String name;
  private BigDecimal availableMinutes;
  private BigDecimal availableSms;
  private BigDecimal price;
}
