package domain;

/**
 * Created by IntelliJ IDEA. User: turner Date: Jun 16, 2004 Time: 1:44:16 AM To
 * change this template use File | Settings | File Templates.
 */
public class Dvd {
  private String sku;

  private String dualLayer;

  private double price;

  public String getSku() {
    return sku;
  }

  public void setSku(String sku) {
    this.sku = sku;
  }

  public String getDualLayer() {
    return dualLayer;
  }

  public void setDualLayer(String dualLayer) {
    this.dualLayer = dualLayer;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String toString() {
    return super.toString() + "[" + sku + ", " + price + ", " + dualLayer + "]";
  }

}
