package examples;

import java.io.IOException;

import com.blackbear.flatworm.FileCreator;
import com.blackbear.flatworm.FileParser;
import com.blackbear.flatworm.errors.FlatwormCreatorException;
import com.blackbear.flatworm.errors.FlatwormParserException;

public class WriteInventory
{
    public static void main(String[] args)
    {

        String xmlConfigFileIn = args[0];
        String inputFile = args[1];
        String xmlConfigFileOut = args[2];
        String outputFile = args[3];

        try
        {
            FileParser parser = new FileParser(xmlConfigFileIn, inputFile);
            FileCreator writer = new FileCreator(xmlConfigFileOut, outputFile);
            writer.setRecordSeperator("\n");

            writer.open();
            parser.open();

            // Instantiate object responsible for handling callbacks
            InventoryHandler handler = new InventoryHandler(writer);

            // set callback methods
            // Args are: bean name (from flatworm xml file), handler object, handler method name
            parser.setBeanHandler("dvd", handler, "handleDvd");
            parser.setBeanHandler("videotape", handler, "handleVideotape");
            parser.setBeanHandler("book", handler, "handleBook");

            // Args are handler object, exception handling method name
            parser.setExceptionHandler(handler, "handleException");

            parser.read();

            parser.close();
            writer.close();

        } catch (NoSuchMethodException ex)
        {
            System.out
                    .println("NoSuchMethodException: Most likely, you didn't implement (or named incorrectly) the handler methods: "
                            + ex.getMessage());
        } catch (IOException ex)
        {
            System.out.println("IOException: Something bad happend while opening,reading,closing the input file: "
                    + ex.getMessage());
        } catch (FlatwormParserException ex)
        {
            System.out.println("FlatwormParserException: Something happened that the parser did not like: "
                    + ex.getMessage());
        } catch (FlatwormCreatorException ex)
        {
            System.out.println("FlatwormCreatorException: Something happened that the creator did not like: "
                    + ex.getMessage());
        }

    }

}
