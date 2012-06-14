/*
 * Created on Apr 5, 2005
 * 
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 * Comments
 */
package domain;

/**
 * @author e50633
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Inventory {

  private String sku;

  private double price;

  private int quantity;

  private String description;

  /**
   * @return
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return
   */
  public double getPrice() {
    return price;
  }

  /**
   * @return
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * @return
   */
  public String getSku() {
    return sku;
  }

  /**
   * @param string
   */
  public void setDescription(String string) {
    description = string;
  }

  /**
   * @param d
   */
  public void setPrice(double d) {
    price = d;
  }

  /**
   * @param i
   */
  public void setQuantity(int i) {
    quantity = i;
  }

  /**
   * @param string
   */
  public void setSku(String string) {
    sku = string;
  }

}
