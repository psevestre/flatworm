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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import domain.segment.ClassPeriod;

public class SegmentTabsParserTest {
  private static final String configFile = "segment-class-tabs.xml";
  private static final String dataFile = "../test/resources/segment-class-tabs-input.txt";
  private int recordNumber = 0;
  private FileParser parser;

  @Test
  public void testParser() {
    try {
      parser = new FileParser(configFile, dataFile);
      parser.setBeanHandler("class", this, "handleClass");

      parser.open();
      parser.read();
      assertEquals(3, recordNumber);
    }
    catch (Exception e) {
      e.printStackTrace();
      fail("An Exception occurred " + e);
    }
    finally {
      if (parser != null) {
        try {
          parser.close();
        }
        catch (IOException e) {
        }
      }
    }
  }

  public void handleClass(MatchedRecord record) {
    ClassPeriod classRecord = (ClassPeriod) record.getBean("class");
    assertNotNull(classRecord);
    ++recordNumber;
    switch (recordNumber) {
    case 1:
      assertEquals("Ms Buffington", classRecord.getTeacher());
      assertEquals("109", classRecord.getRoom());
      assertEquals(4, classRecord.getStudents().size());
      break;
    case 2:
      assertEquals("Arithmetic", classRecord.getSubject());
      assertEquals(3, classRecord.getStudents().size());
      assertEquals(3, classRecord.getGradeLevel());
      break;
    case 3:
      assertEquals("112", classRecord.getRoom());
      assertEquals(5, classRecord.getStudents().size());
      assertEquals("Laver", classRecord.getStudents().get(2).getLastName());
      break;
    default:
      break;
    }
  }
}
