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
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import domain.segment.Account;
import domain.segment.Address;
import domain.segment.Consumer;
import domain.segment.Phone;

public class SegmentFileTest {
  @Test
  public void testFileRead() {
    ConfigurationReader parser = new ConfigurationReader();
    BufferedReader bufIn = null;
    try {
      FileFormat ff = parser.loadConfigurationFile("segment-example.xml");
      InputStream in = getClass().getResourceAsStream("/segment_input.txt");
      bufIn = new BufferedReader(new InputStreamReader(in));

      MatchedRecord results = ff.getNextRecord(bufIn);
      assertEquals("account", results.getRecordName());
      Account account = (Account) results.getBean("account");
      for (int cnt = 1; cnt < 4; ++cnt) {
        results = ff.getNextRecord(bufIn);
      }
      account = (Account) results.getBean("account");
      assertComplexAccount(account);
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

  private void assertComplexAccount(Account account) {
    assertEquals("20080430", new SimpleDateFormat("yyyyMMdd").format(account.getReportingDate()));
    assertEquals("2033878922838", account.getAccountNumber());
    assertEquals("F", account.getAccountCode());
    assertEquals("NC", account.getServiceType());
    assertEquals("AREA", account.getCompanyId());
    List<Consumer> consumers = account.getConsumers();
    assertEquals("Wrong number of  consumers", 5, consumers.size());
    Consumer consumer = consumers.get(2);
    assertEquals("ROBINSON", consumer.getLastName());
    assertEquals("G", consumer.getFirstName());
    assertEquals("", consumer.getMiddleName());
    assertEquals("", consumer.getGender());
    assertEquals(0, consumer.getPhone().size());
    consumer = consumers.get(3);
    assertEquals("ROBINSON", consumer.getLastName());
    assertEquals("SHANETTE", consumer.getFirstName());
    assertEquals("", consumer.getMiddleName());
    assertEquals("F", consumer.getGender());
    assertEquals(1, consumer.getPhone().size());
    Phone phone = consumer.getPhone().get(0);
    assertEquals("S", phone.getType());
    assertEquals("P", phone.getPublishInd());
    assertEquals("2033878922", phone.getNumber());
    assertEquals(1, account.getAddresses().size());
    Address address = account.getAddresses().get(0);
    assertEquals("B", address.getType());
    assertEquals("205 HIGH TOP CIR W", address.getNumber() + " " + address.getStreet() + " "
        + address.getStreetType());
    assertEquals("HMDN", address.getCity());
    assertEquals("CT", address.getState());
    assertEquals("06514", address.getZip());
  }
}
