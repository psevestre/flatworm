/*
 * Created on Feb 21, 2005
 * 
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 * Comments
 */
package examples;

import java.io.IOException;

import com.blackbear.flatworm.FileCreator;
import com.blackbear.flatworm.MatchedRecord;
import com.blackbear.flatworm.errors.FlatwormCreatorException;

import domain.Inventory;

/**
 * @author e50633
 * 
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code
 * and Comments
 */
public class InventoryHandler
{

    private FileCreator writer;

    public InventoryHandler(FileCreator writer)
    {
        this.writer = writer;
    }

    public void handleDvd(MatchedRecord results)
    {

        Dvd dvd = (Dvd) results.getBean("dvd");
        Film film = (Film) results.getBean("film");

        writeInventory(InventoryFactory.getInventoryFor(dvd, film));
        System.out.println("Processing Dvd...");
    }

    public void handleVideotape(MatchedRecord results)
    {
        Videotape video = (Videotape) results.getBean("video");
        Film film = (Film) results.getBean("film");

        writeInventory(InventoryFactory.getInventoryFor(video, film));
        System.out.println("Handling VideoTape...");
    }

    public void handleBook(MatchedRecord results)
    {
        Book book = (Book) results.getBean("book");

        writeInventory(InventoryFactory.getInventoryFor(book));
        System.out.println("Handling Book...");
    }

    public void handleException(String exception, String lastLine)
    {

        System.out.println("HandlingException\n - " + exception + "\n - " + lastLine);
    }

    private void writeInventory(Inventory inventory)
    {
        try
        {
            writer.setBean("inventory", inventory);
            writer.write("inventory");
        } catch (IOException ex)
        {
            System.out.println("IOException: Something bad happend while opening,reading,closing the input file: "
                    + ex.getMessage());
        } catch (FlatwormCreatorException ex)
        {
            System.out.println("FlatwormCreatorException: Something happened that the creator did not like: "
                    + ex.getMessage());
        }
    }
}
