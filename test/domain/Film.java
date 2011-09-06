package domain;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: turner Date: Jun 16, 2004 Time: 1:44:28 AM To change this template use File |
 * Settings | File Templates.
 */
public class Film
{
    private Date releaseDate;

    private String title;

    private String studio;

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

    public String getStudio()
    {
        return studio;
    }

    public void setStudio(String studio)
    {
        this.studio = studio;
    }

    public String toString()
    {
        return super.toString() + "[" + releaseDate + ", " + title + ", " + studio + "]";
    }
}
