package domain;

/**
 * Created by IntelliJ IDEA. User: turner Date: Jun 16, 2004 Time: 1:44:16 AM To change this template use File |
 * Settings | File Templates.
 */
public class Videotape
{
    private String sku;

    private double price;

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String toString()
    {
        return super.toString() + "[" + sku + ", " + price + "]";
    }

}
