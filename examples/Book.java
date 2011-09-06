import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: turner Date: Jun 16, 2004 Time: 1:44:28 AM To change this template use File |
 * Settings | File Templates.
 */
public class Book
{
    private Date releaseDate;

    private String title;

    private String author;

    private double price;

    private String sku;

    public Date getReleaseDate()
    {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate)
    {
        this.releaseDate = releaseDate;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String toString()
    {
        return super.toString() + "[" + releaseDate + ", " + title + ", " + author + "," + getSku() + "," + getPrice()
                + "]";
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getSku()
    {
        return sku;
    }

    public void setSku(String sku)
    {
        this.sku = sku;
    }
}
