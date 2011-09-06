/*
 * Created on Apr 5, 2005
 * 
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 * Comments
 */
package examples;

import domain.Book;
import domain.Dvd;
import domain.Film;
import domain.Inventory;
import domain.Videotape;

/**
 * @author e50633
 * 
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code
 * and Comments
 */
public final class InventoryFactory
{
    public static Inventory getInventoryFor(Dvd dvd, Film film)
    {
        Inventory inventory = new Inventory();

        inventory.setPrice(dvd.getPrice());
        inventory.setDescription(film.getTitle() + " - (" + film.getStudio() + ")");
        inventory.setQuantity(0);
        inventory.setSku(dvd.getSku());

        return inventory;
    }

    public static Inventory getInventoryFor(Videotape video, Film film)
    {
        Inventory inventory = new Inventory();

        inventory.setPrice(video.getPrice());
        inventory.setDescription(film.getTitle() + " - (" + film.getStudio() + ")");
        inventory.setQuantity(10);
        inventory.setSku(video.getSku());

        return inventory;
    }

    public static Inventory getInventoryFor(Book book)
    {
        Inventory inventory = new Inventory();

        inventory.setPrice(book.getPrice());
        inventory.setDescription(book.getTitle() + " by " + book.getAuthor());
        inventory.setQuantity(5);
        inventory.setSku(book.getSku());

        return inventory;
    }
}
