/**
 * Flatworm - A Java Flat File Importer Copyright (C) 2004 James M. Turner Extended by James Lawrence 2005
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 */

package com.blackbear.flatworm;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.blackbear.flatworm.errors.FlatwormParserException;

import domain.Book;
import domain.Dvd;
import domain.Film;
import domain.Videotape;


public class ComplexFileCallbackTest extends AbstractCallbackReader
{
    private int recordsRead;
    
    public ComplexFileCallbackTest() throws FlatwormParserException
    {
        super("complex-example.xml", "../test/resources/complex_input.txt");
    }
    
    @Test
    public void testReaderCallback() throws NoSuchMethodException
    {
        setCallback("dvd", this, "processRecord");
        setCallback("videotape", this, "processRecord");
        setCallback("book", this, "processRecord");
        recordsRead = 0;
        super.process();
        assertEquals(5, recordsRead);
    }
    
    @Test
    public void testRecordCallback()
    {
        recordsRead = 0;
        RecordCallback callback = new RecordCallback()
            {
                public void processRecord(MatchedRecord record)
                {
                    ComplexFileCallbackTest.this.processRecord(record);
                }
            };
        addRecordCallback("dvd", callback);
        addRecordCallback("videotape", callback);
        addRecordCallback("book", callback);
        super.process();
        assertEquals(5, recordsRead);
    }

    public void processRecord(MatchedRecord record)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        switch(++recordsRead)
        {
            case 1:
                assertEquals("dvd", record.getRecordName());
                Dvd dvd = (Dvd)record.getBean("dvd");
                Film film = (Film)record.getBean("film");
                assertEquals("55512121", dvd.getSku());
                assertEquals(49.95, dvd.getPrice(), 0.01);
                assertEquals("Y", dvd.getDualLayer());
                assertEquals("2004/01/15", format.format(film.getReleaseDate()));
                assertEquals("DIAL J FOR JAVA", film.getTitle());
                assertEquals("RUN ANYWHERE STUDIO", film.getStudio());
                break;
            case 2:
                assertEquals("book", record.getRecordName());
                Book book = (Book)record.getBean("book");
                assertEquals("546234476", book.getSku());
                assertEquals("HE KNOWS WHEN YOU\"RE CODING", book.getTitle());
                assertEquals("JAVALANG OBJECT", book.getAuthor());
                assertEquals(13.95, book.getPrice(), 0.01);
                assertEquals("2003/11/10", format.format(book.getReleaseDate()));
                break;
            case 3:
                assertEquals("videotape", record.getRecordName());
                Videotape tape = (Videotape)record.getBean("video");
                film = (Film)record.getBean("film");
                assertEquals("2346542", tape.getSku());
                assertEquals(23.55, tape.getPrice(), 0.01);
                assertEquals("2003/03/12", format.format(film.getReleaseDate()));
                assertEquals("WHEN A STRANGER IMPLEMENTS", film.getTitle());
                assertEquals("NULL POINTER PRODUCTIONS", film.getStudio());
                break;
            case 4:
                assertEquals("book", record.getRecordName());
                break;
            case 5:
                assertEquals("videotape", record.getRecordName());
            default:
                break;
        }
    }
}
