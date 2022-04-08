package services;

import java.util.List;
import model.InvoiceTotal;
import model.PackageRate;
import model.PriceList;
import model.Usage;

public interface InvoiceCalculator {
  InvoiceTotal calculateInvoiceTotal(List<Usage> usages, PriceList priceList, PackageRate packageRate);
}
