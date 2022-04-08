package services;

import java.math.BigDecimal;
import java.util.List;
import model.InvoiceTotal;
import model.PackageRate;
import model.PriceList;
import model.Usage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvoiceCalculatorImplTest {

  public static final long CLIENT_ID = 100L;
  public static final PackageRate PACKAGE_S = new PackageRate("Package S", BigDecimal.valueOf(10),
      BigDecimal.valueOf(50),
      BigDecimal.valueOf(5));
  public static final PackageRate PACKAGE_M = new PackageRate("Package M", BigDecimal.valueOf(50),
      BigDecimal.valueOf(100),
      BigDecimal.valueOf(10));

  @Test
  void calculateInvoiceTotalPackageSTest() {

    List<Usage> usages = List.of(
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.MINUTES_SERVICE_ID, BigDecimal.valueOf(7)),
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.SMS_SERVICE_ID, BigDecimal.valueOf(45)));

    PriceList priceList = new PriceList(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.3));

    PackageRate packageS = PACKAGE_S;

    InvoiceCalculatorImpl invoiceCalculator = new InvoiceCalculatorImpl();
    InvoiceTotal invoiceTotal = invoiceCalculator.calculateInvoiceTotal(usages, priceList,
        packageS);

    Assertions.assertEquals(packageS.getPrice(), invoiceTotal.getTotalPrice());
  }

  @Test
  void calculateInvoiceTotalPackageSExtraMinutesTest() {

    List<Usage> usages = List.of(
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.MINUTES_SERVICE_ID, BigDecimal.valueOf(13)),
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.SMS_SERVICE_ID, BigDecimal.valueOf(49)));

    PriceList priceList = new PriceList(BigDecimal.valueOf(0.1), BigDecimal.valueOf(0.2));

    InvoiceCalculatorImpl invoiceCalculator = new InvoiceCalculatorImpl();
    InvoiceTotal invoiceTotal = invoiceCalculator.calculateInvoiceTotal(usages, priceList,
        PACKAGE_S);

    Assertions.assertEquals(BigDecimal.valueOf(5.3), invoiceTotal.getTotalPrice());
  }

  @Test
  void calculateInvoiceTotalPackageSExtraSmsTest() {

    List<Usage> usages = List.of(
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.MINUTES_SERVICE_ID, BigDecimal.valueOf(5)),
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.SMS_SERVICE_ID, BigDecimal.valueOf(57)));

    PriceList priceList = new PriceList(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.3));

    InvoiceCalculatorImpl invoiceCalculator = new InvoiceCalculatorImpl();
    InvoiceTotal invoiceTotal = invoiceCalculator.calculateInvoiceTotal(usages, priceList,
        PACKAGE_S);

    Assertions.assertEquals(BigDecimal.valueOf(7.1), invoiceTotal.getTotalPrice());
  }

  @Test
  void calculateInvoiceTotalPackageMTest() {

    List<Usage> usages = List.of(
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.MINUTES_SERVICE_ID, BigDecimal.valueOf(5)),
        new Usage(CLIENT_ID, InvoiceCalculatorImpl.SMS_SERVICE_ID, BigDecimal.valueOf(57)));

    PriceList priceList = new PriceList(BigDecimal.valueOf(0.5), BigDecimal.valueOf(0.3));

    InvoiceCalculatorImpl invoiceCalculator = new InvoiceCalculatorImpl();
    InvoiceTotal invoiceTotal = invoiceCalculator.calculateInvoiceTotal(usages, priceList,
        PACKAGE_M);

    Assertions.assertEquals(BigDecimal.valueOf(10), invoiceTotal.getTotalPrice());
  }
}
