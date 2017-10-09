/**
 * Flatworm - A Java Flat File Importer Copyright (C) 2004 James M. Turner Extended by James Lawrence 2005, Josh Brackett 2011
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.blackbear.flatworm.errors.FlatwormParserException;

public class InputStreamFileParser extends FileParser {

  private final InputStream is;

  public InputStreamFileParser(String config, InputStream is) throws FlatwormParserException {
    super(config, null);
    this.is = is;
  }

  @Override
  public void open() throws IOException {
    String encoding = ff.getEncoding();
    bufIn = new BufferedReader(new InputStreamReader(is, encoding));
  }

}