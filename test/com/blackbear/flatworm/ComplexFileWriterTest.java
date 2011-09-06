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

import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

import com.blackbear.flatworm.errors.FlatwormCreatorException;
import com.blackbear.flatworm.errors.FlatwormParserException;


public class ComplexFileWriterTest extends AbstractTestFileWriter
{
    private class FileParser extends AbstractCallbackReader
    {
        public FileParser() throws FlatwormParserException, NoSuchMethodException
        {
            super(configFileName, inputFileName);
            setCallback("dvd", ComplexFileWriterTest.this, "processRecord");
            setCallback("videotape", ComplexFileWriterTest.this, "processRecord");
            setCallback("book", ComplexFileWriterTest.this, "processRecord");
        }
    }
    
    private String configFileName = "complex-example.xml";
    private String inputFileName = "../test/resources/complex_input.txt";
    private String outputFileName = "../output/complex_output.txt";
    
    @Test
    public void testWriteComplexFile()
    {
        performTest();
    }
    
    @Override
    protected AbstractCallbackReader fileParser() throws FlatwormParserException, NoSuchMethodException
    {
        return new FileParser();
    }

    @Override
    protected FileCreator performOutputProcessing() throws FlatwormCreatorException, IOException
    {
        FileCreator writer = new FileCreator(configFileName, outputFileName);
        writer.setRecordSeperator("\n");
        writer.open();
        for(Iterator<MatchedRecord> iter = records.iterator(); iter.hasNext();)
        {
            MatchedRecord record = iter.next();
            prepareBeans(writer, record);
            writer.write(record.getRecordName());
        }
        return writer;
    }
    
    private void prepareBeans(FileCreator writer, MatchedRecord record)
    {
        if(record.getRecordName().equals("dvd"))
        {
            writer.setBean("dvd", record.getBean("dvd"));
            writer.setBean("film", record.getBean("film"));
        }
        else if(record.getRecordName().equals("videotape"))
        {
            writer.setBean("video", record.getBean("video"));
            writer.setBean("film", record.getBean("film"));
        }
        else if(record.getRecordName().equals("book"))
        {
            writer.setBean("book", record.getBean("book"));
        }
    }
}


