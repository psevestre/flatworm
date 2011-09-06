package examples;

import java.util.HashMap;

import org.apache.log4j.Category;

public class MyConverters
{

    public String convertChar(String str, HashMap options)
    {
        // nothing extra to do, since convHelper calls removePadding now
        return "*** " + str + " ***";
    }

    static Category cat = Category.getInstance(MyConverters.class);

}