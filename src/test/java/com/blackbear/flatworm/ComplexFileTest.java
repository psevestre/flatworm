/**
 * Flatworm - A Java Flat File Importer Copyright (C) 2004 James M. Turner
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
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import org.junit.Test;

import domain.Book;
import domain.Dvd;
import domain.Film;
import domain.Videotape;

public class ComplexFileTest {
  @Test
  public void testFileRead() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    ConfigurationReader parser = new ConfigurationReader();
    try {
      FileFormat ff = parser.loadConfigurationFile("complex-example.xml");
      InputStream in = getClass().getResourceAsStream("/complex_input.txt");
      BufferedReader bufIn = new BufferedReader(new InputStreamReader(in));

      MatchedRecord results = ff.getNextRecord(bufIn);
      assertEquals("dvd", results.getRecordName());
      Dvd dvd = (Dvd) results.getBean("dvd");
      Film film = (Film) results.getBean("film");
      assertEquals("55512121", dvd.getSku());
      assertEquals(49.95, dvd.getPrice(), 0.01);
      assertEquals("Y", dvd.getDualLayer());
      assertEquals("2004/01/15", format.format(film.getReleaseDate()));
      assertEquals("DIAL J FOR JAVA", film.getTitle());
      assertEquals("RUN ANYWHERE STUDIO", film.getStudio());

      results = ff.getNextRecord(bufIn);
      assertEquals("book", results.getRecordName());
      Book book = (Book) results.getBean("book");
      assertEquals("546234476", book.getSku());
      assertEquals("HE KNOWS WHEN YOU\"RE CODING", book.getTitle());
      assertEquals("JAVALANG OBJECT", book.getAuthor());
      assertEquals(13.95, book.getPrice(), 0.01);
      assertEquals("2003/11/10", format.format(book.getReleaseDate()));

      results = ff.getNextRecord(bufIn);
      assertEquals("videotape", results.getRecordName());
      Videotape tape = (Videotape) results.getBean("video");
      film = (Film) results.getBean("film");
      assertEquals("2346542", tape.getSku());
      assertEquals(23.55, tape.getPrice(), 0.01);
      assertEquals("2003/03/12", format.format(film.getReleaseDate()));
      assertEquals("WHEN A STRANGER IMPLEMENTS", film.getTitle());
      assertEquals("NULL POINTER PRODUCTIONS", film.getStudio());

      results = ff.getNextRecord(bufIn);
      assertEquals("book", results.getRecordName());

      results = ff.getNextRecord(bufIn);
      assertEquals("videotape", results.getRecordName());
    }
    catch (Exception e) {
      fail("Caught an exception of type " + e.getClass().getSimpleName());
    }
  }

}
