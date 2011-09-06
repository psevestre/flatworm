/*
 * Created on Feb 21, 2005
 * 
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 * Comments
 */
package examples;

import com.blackbear.flatworm.MatchedRecord;

/**
 * @author e50633
 * 
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code
 * and Comments
 */
public class ProductHandler
{

    public void handleDvd(MatchedRecord results)
    {
        Dvd dvd = (Dvd) results.getBean("dvd");
        Film film = (Film) results.getBean("film");

        System.out.println("Handling Dvd\n - " + dvd + "\n - " + film);
    }

    public void handleVideotape(MatchedRecord results)
    {
        Videotape video = (Videotape) results.getBean("video");
        Film film = (Film) results.getBean("film");

        System.out.println("Handling VideoTape\n - " + video + "\n - " + film);
    }

    public void handleBook(MatchedRecord results)
    {
        Book book = (Book) results.getBean("book");

        System.out.println("Handling Book\n - " + book);
    }

    public void handleException(String exception, String lastLine)
    {

        System.out.println("HandlingException\n - " + exception + "\n - " + lastLine);
    }

}
