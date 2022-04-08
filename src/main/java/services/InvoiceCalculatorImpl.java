package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.InvoiceRow;
import model.InvoiceTotal;
import model.PackageRate;
import model.PriceList;
import model.Usage;

public class InvoiceCalculatorImpl implements InvoiceCalculator {

  public static final Long MINUTES_SERVICE_ID = 2L;
  public static final Long SMS_SERVICE_ID = 1L;
  private static final String SMS_SERVICE_NAME = "sms";
  private static final String MINUTES_SERVICE_NAME = "minutes";

  @Override
  public InvoiceTotal calculateInvoiceTotal(List<Usage> usages, PriceList priceList,
      PackageRate packageRate) {
    Usage minutesUsage = findService(usages, MINUTES_SERVICE_ID);
    ArrayList<InvoiceRow> invoiceRows = new ArrayList<>();
    invoiceRows.add(calculateExtraAmount(minutesUsage, priceList.getMinutes(),
        packageRate.getAvailableMinutes(), MINUTES_SERVICE_NAME));

    Usage smsUsage = findService(usages, SMS_SERVICE_ID);
    invoiceRows.add(calculateExtraAmount(smsUsage, priceList.getSms(),
        packageRate.getAvailableSms(), SMS_SERVICE_NAME));

    BigDecimal extraPrice = invoiceRows.stream()
        .map(InvoiceRow::getPrice)
        .reduce(BigDecimal::add)
        .orElse(BigDecimal.ZERO);

    BigDecimal total = packageRate.getPrice().add(extraPrice);

    InvoiceTotal invoiceTotal = new InvoiceTotal(packageRate, invoiceRows, total);

    printInvoice(invoiceTotal);

    return invoiceTotal;
  }

  private void printInvoice(InvoiceTotal invoiceTotal) {
    System.out.printf("%s - %s eur%n", invoiceTotal.getPackageRate().getName(),
        invoiceTotal.getPackageRate().getPrice());
    invoiceTotal.getRows().forEach(
        invoiceRow -> System.out.printf("%s extra %s - %s eur%n", invoiceRow.getAmount(),
            invoiceRow.getServiceName(), invoiceRow.getPrice()));
    System.out.printf("Total - %s eur%n%n", invoiceTotal.getTotalPrice());
  }

  private InvoiceRow calculateExtraAmount(Usage usage, BigDecimal extraPrice,
      BigDecimal availableUnits, String serviceName) {
    BigDecimal extraMinutes = usage.getAmountUsed().subtract(availableUnits);
    if (extraMinutes.compareTo(BigDecimal.ZERO) > 0) {
      return new InvoiceRow(serviceName, extraMinutes, extraMinutes.multiply(extraPrice));
    }
    return new InvoiceRow(serviceName);
  }

  private Usage findService(List<Usage> usages, Long serviceId) {
    return usages.stream().filter(usage -> serviceId.equals(usage.getServiceId()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Service not found - " + serviceId));
  }
}
