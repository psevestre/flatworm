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

import static org.junit.Assert.fail;

import java.io.IOException;

import com.blackbear.flatworm.errors.FlatwormParserException;

/*
 * Subclass this class to create a class for parsing a flat file.
 * Pass the name of the configuration file, and the input file to the constructor. Then set callbacks for each record type to be processed.
 */
public abstract class AbstractCallbackReader
{
    private FileParser parser;
    
    protected AbstractCallbackReader(String configurationFileName, String inputFileName) throws FlatwormParserException
    {
        parser = new FileParser(configurationFileName, inputFileName);
    }
    
    protected void process()
    {
        try
        {
            parser.open();
            parser.read();
        } 
        catch (Exception e)
        {
            fail("Got an exception in the parser " + e.getMessage());
        } 
        finally
        {
            if(parser != null)
            {
                try
                {
                    parser.close();
                } 
                catch (IOException e)
                {
                }
            }
        }
    }
    
    protected void setCallback(String recordName, Object target, String callbackMethod) throws NoSuchMethodException
    {
        parser.setBeanHandler(recordName, target, callbackMethod);
    }
    
    protected void addRecordCallback(String recordName, RecordCallback callback)
    {
        parser.addRecordCallback(recordName, callback);
    }
}
