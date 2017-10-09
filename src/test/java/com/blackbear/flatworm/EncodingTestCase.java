/**
 * Flatworm - A Java Flat File Importer Copyright (C) 2004 James M. Turner Extended by James Lawrence - 2005
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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import com.blackbear.flatworm.errors.FlatwormConversionException;
import com.blackbear.flatworm.errors.FlatwormCreatorException;
import com.blackbear.flatworm.errors.FlatwormInputLineLengthException;
import com.blackbear.flatworm.errors.FlatwormInvalidRecordException;
import com.blackbear.flatworm.errors.FlatwormUnsetFieldValueException;

import domain.Film;

public class EncodingTestCase extends TestCase {
  protected FileFormat ff;

  protected BufferedReader reader;

  private static String layout = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
      + "<file-format encoding=\"iso-8859-2\">\r\n"
      + "    <converter name=\"char\" class=\"com.blackbear.flatworm.converters.CoreConverters\" method=\"convertChar\"\r\n"
      + "        return-type=\"java.lang.String\" />\r\n" + "    <record name=\"dvd\">\r\n"
      + "       <record-ident>\r\n"
      + "           <length-ident minlength=\"0\" maxlength=\"9999\" />\r\n"
      + "       </record-ident>\r\n" + "       <record-definition>\r\n"
      + "           <bean name=\"film\" class=\"domain.Film\" />\r\n" + "           <line>\r\n"
      + "               <record-element length=\"30\" beanref=\"film.title\"\r\n"
      + "                   type=\"char\">\r\n"
      + "                   <conversion-option name=\"justify\" value=\"left\" />\r\n"
      + "               </record-element>\r\n" + "           </line>\r\n"
      + "       </record-definition>\r\n" + "   </record>\r\n" + "</file-format>";

  public EncodingTestCase(String name) {
    super(name);
  }

  protected void setContent(byte[] content) throws Exception {
    ConfigurationReader parser = new ConfigurationReader();
    ff = getFileFormat(parser);
    reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content),
        ff.getEncoding()));
  }

  protected FileFormat getFileFormat(ConfigurationReader parser) throws Exception {
    InputStream is = new ByteArrayInputStream(layout.getBytes());
    return parser.loadConfigurationFile(is);
  }

  protected Object getNextBean() throws FlatwormInvalidRecordException,
      FlatwormInputLineLengthException, FlatwormConversionException,
      FlatwormUnsetFieldValueException, FlatwormCreatorException {
    MatchedRecord results = ff.getNextRecord(reader);
    return results.getBean(getBeanName());
  }

  protected String getBeanName() {
    return "film";
  }

  public void testNormalString() throws Exception {
    prepareContent("foobar                        ");
    Film film = (Film) getNextBean();
    assertNotNull(film);
    assertEquals("foobar", film.getTitle());
  }

  public void testSpecialChar() throws Exception {
    prepareContent("\u0104                             ");
    Film film = (Film) getNextBean();
    assertNotNull(film);
    assertEquals("\u0104", film.getTitle());
  }

  private void prepareContent(String string) throws UnsupportedEncodingException, IOException,
      Exception {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    OutputStreamWriter sw = new OutputStreamWriter(os, "iso-8859-2");
    sw.write(string);
    sw.flush();
    byte[] bytes = os.toByteArray();
    setContent(bytes);
  }
}
