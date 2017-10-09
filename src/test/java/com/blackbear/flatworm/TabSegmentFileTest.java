/**
 * Flatworm - A Java Flat File Importer Copyright (C) 2004 James M. Turner Extended by James Lawrence 2005 and Josh Brackett 2011
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import domain.segment.ClassPeriod;

public class TabSegmentFileTest {
  @Test
  public void testFileRead() {
    ConfigurationReader parser = new ConfigurationReader();
    BufferedReader bufIn = null;
    try {
      FileFormat ff = parser.loadConfigurationFile("segment-class-tabs.xml");
      InputStream in = getClass().getResourceAsStream("/segment-class-tabs-input.txt");
      bufIn = new BufferedReader(new InputStreamReader(in));

      MatchedRecord results = ff.getNextRecord(bufIn);
      assertEquals("class", results.getRecordName());
      ClassPeriod cl = (ClassPeriod) results.getBean("class");
      for (int cnt = 1; cnt < 4; ++cnt) {
        results = ff.getNextRecord(bufIn);
      }
    }
    catch (Exception e) {
      fail("Caught an exception of type " + e.getClass().getSimpleName() + ": " + e.getMessage());
    }
    finally {
      if (bufIn != null) {
        try {
          bufIn.close();
        }
        catch (IOException e) {
        }
      }
    }
  }

}
