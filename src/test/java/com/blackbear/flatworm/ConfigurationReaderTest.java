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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.blackbear.flatworm.errors.FlatwormConfigurationValueException;
import com.blackbear.flatworm.errors.FlatwormUnsetFieldValueException;

public class ConfigurationReaderTest {
  private FileFormat format;

  public void loadFileFormat(String configFile) {
    ConfigurationReader reader = new ConfigurationReader();
    try {
      format = reader.loadConfigurationFile(configFile);
    }
    catch (FlatwormUnsetFieldValueException e) {
      fail("Got a FlatwormUnsetFieldValueException - " + e);
    }
    catch (FlatwormConfigurationValueException e) {
      fail("Got a FlatwormConfigurationValueException - " + e);
    }
  }

  @Test
  public void testComplexRecordsRead() {
    loadFileFormat("complex-example.xml");
    assertNotNull(format);
    Record record = format.getRecord("dvd");
    assertNotNull(record);
    assertDvdRecord(record);
    assertNotNull(format.getRecord("videotape"));
    assertNotNull(format.getRecord("book"));
    assertNull(format.getRecord("cd"));
  }

  @Test
  public void testSegmentRecordsRead() {
    loadFileFormat("segment-example.xml");
    assertNotNull(format);
  }

  private void assertDvdRecord(Record dvd) {
    assertEquals('L', dvd.getIdentTypeFlag());
    assertEquals(85, dvd.getLengthIdentMin());
    assertEquals(85, dvd.getLengthIdentMax());
    RecordDefinition def = dvd.getRecordDefinition();
    assertNotNull(def);
    Map<String, Bean> beans = def.getBeansUsed();
    assertEquals(2, beans.size());
    assertNotNull(beans.get("dvd"));
    assertNotNull(beans.get("film"));
    List<Line> lines = def.getLines();
    assertEquals(1, lines.size());
    Line line = lines.get(0);
    assertEquals(6, line.getElements().size());
  }
}
