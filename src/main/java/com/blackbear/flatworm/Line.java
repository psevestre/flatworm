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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.blackbear.flatworm.errors.FlatwormConversionException;
import com.blackbear.flatworm.errors.FlatwormCreatorException;
import com.blackbear.flatworm.errors.FlatwormInputLineLengthException;
import com.blackbear.flatworm.errors.FlatwormInvalidRecordException;
import com.blackbear.flatworm.errors.FlatwormUnsetFieldValueException;

/**
 * Bean class used to store the values from the Line XML tag
 */
class Line {
  private static Log log = LogFactory.getLog(Line.class);

  private List<LineElement> elements = new ArrayList<LineElement>();
  private String delimit = null;
  private char chrQuote = '\0';
  private ConversionHelper convHelper;
  private Map<String, Object> beans;
  private BeanMappingStrategy mappingStrategy = new PropertyUtilsMappingStrategy();

  // properties used for processing delimited input
  private String[] delimitedFields;
  private int currentField = 0;

  public Line() {
  }

  /**
   * <b>NOTE:</b> Only the first character in the string is considered
   */
  public void setQuoteChar(String quote) {
    chrQuote = quote.charAt(0);
  }

  public boolean isDelimeted() {
    return (null != delimit);
  }

  public void setDelimeter(String delimit) {
    this.delimit = delimit;
  }

  public String getDelimeter() {
    return delimit;
  }

  public List<LineElement> getElements() {
    return Collections.unmodifiableList(elements);
  }

  public void setElements(List<LineElement> recordElements) {
    this.elements.clear();
    this.elements.addAll(recordElements);
  }

  public void addElement(LineElement re) {
    elements.add(re);
  }

  @Override
  public String toString() {
    StringBuffer b = new StringBuffer();
    b.append(super.toString() + "[");
    b.append("elements = " + elements);
    b.append("]");
    return b.toString();
  }

  /**
   * 
   * @param inputLine
   *          A single line from file to be parsed into its corresponding bean
   * @param beans
   *          A Hashmap containing a collection of beans which will be populated
   *          with parsed data
   * @param convHelper
   *          A ConversionHelper which aids in the conversion of datatypes and
   *          string formatting
   * 
   * @throws FlatwormInputLineLengthException
   *           , FlatwormConversionException, FlatwormUnsetFieldValueException
   * @throws FlatwormInvalidRecordException
   * @throws FlatwormCreatorException
   */
  public void parseInput(String inputLine, Map<String, Object> beans, ConversionHelper convHelper)
      throws FlatwormInputLineLengthException, FlatwormConversionException,
      FlatwormUnsetFieldValueException, FlatwormInvalidRecordException, FlatwormCreatorException {
    this.convHelper = convHelper;
    this.beans = beans;

    // JBL - check for delimited status
    if (isDelimeted()) {
      // Don't parse empty lines
      if (inputLine != null && !inputLine.isEmpty()) {
        parseInputDelimited(inputLine);
        return;
      }
    }

    int charPos = 0;
    for (int i = 0; i < elements.size(); i++) {
      LineElement le = elements.get(i);
      if (le instanceof RecordElement) {
        RecordElement re = (RecordElement) le;
        int start = charPos;
        int end = charPos;
        if (re.isFieldStartSet())
          start = re.getFieldStart();
        if (re.isFieldEndSet()) {
          end = re.getFieldEnd();
          charPos = end;
        }
        if (re.isFieldLengthSet()) {
          end = start + re.getFieldLength();
          charPos = end;
        }
        if (end > inputLine.length())
          throw new FlatwormInputLineLengthException("Looking for field " + re.getBeanRef()
              + " at pos " + start + ", end " + end + ", input length = " + inputLine.length());
        String beanRef = re.getBeanRef();
        if (beanRef != null) {
          String fieldChars = inputLine.substring(start, end);

          // JBL - to keep from dup. code, moved this to a private method
          mapField(fieldChars, re);
        }
      }
      else if (le instanceof SegmentElement) {
        SegmentElement se = (SegmentElement) le;
        /* TODO - to be added. For now we only support delimited. But there really is no reason not to
                   support fixed-format as well
        int start = charPos;
        int end = charPos;
        if (se.isFieldStartSet())
            start = se.getFieldStart();
        if (se.isFieldEndSet())
        {
            end = se.getFieldEnd();
            charPos = end;
        }
        if (se.isFieldLengthSet())
        {
            end = start + se.getFieldLength();
            charPos = end;
        }
        if (end > inputLine.length())
            throw new FlatwormInputLineLengthException("Looking for field " + se.getBeanRef() + " at pos " + start
                    + ", end " + end + ", input length = " + inputLine.length());
        String beanRef = se.getBeanRef();
        if (beanRef != null)
        {
            String fieldChars = inputLine.substring(start, end);

            // JBL - to keep from dup. code, moved this to a private method
            mapField(convHelper, fieldChars, se, beans);

        }
         */
      }
    }
  }

  /**
   * Convert string field from file into appropriate type and set bean's value<br>
   * 
   * @param fieldChars
   *          the raw string data read from the field
   * @param re
   *          the RecordElement, which contains detailed information about the
   *          field
   * 
   * @throws FlatwormInputLineLengthException
   *           , FlatwormConversionException, FlatwormUnsetFieldValueException -
   *           wraps
   *           IllegalAccessException,InvocationTargetException,NoSuchMethodException
   */
  private void mapField(String fieldChars, RecordElement re)
      throws FlatwormInputLineLengthException, FlatwormConversionException,
      FlatwormUnsetFieldValueException {

    Object value = convHelper.convert(re.getType(), fieldChars, re.getConversionOptions(),
        re.getBeanRef());

    String beanRef = re.getBeanRef();
    int posOfFirstDot = beanRef.indexOf('.');
    String beanName = beanRef.substring(0, posOfFirstDot);
    String property = beanRef.substring(posOfFirstDot + 1);

    Object bean = beans.get(beanName);

    mappingStrategy.mapBean(bean, beanName, property, value, re.getConversionOptions());
  }

  /**
   * Convert string field from file into appropriate type and set bean's value.
   * This is used for delimited files only<br>
   * 
   * @param inputLine
   *          the line of data read from the data file
   * 
   * @throws FlatwormInputLineLengthException
   *           , FlatwormConversionException, FlatwormUnsetFieldValueException -
   *           wraps
   *           IllegalAccessException,InvocationTargetException,NoSuchMethodException
   * @throws FlatwormInvalidRecordException
   * @throws FlatwormCreatorException
   */
  private void parseInputDelimited(String inputLine) throws FlatwormInputLineLengthException,
      FlatwormConversionException, FlatwormUnsetFieldValueException,
      FlatwormInvalidRecordException, FlatwormCreatorException {

    char split = delimit.charAt(0);
    if (delimit.length() == 2 && delimit.charAt(0) == '\\') {
      char specialChar = delimit.charAt(1);
      switch (specialChar) {
      case 't':
        split = '\t';
        break;
      case 'n':
        split = '\n';
        break;
      case 'r':
        split = '\r';
        break;
      case 'f':
        split = '\f';
        break;
      case '\\':
        split = '\\';
        break;
      default:
        break;
      }
    }
    delimitedFields = Util.split(inputLine, split, chrQuote);
    currentField = 0;
    doParseDelimitedInput(elements);
  }

  private void doParseDelimitedInput(List<LineElement> elements)
      throws FlatwormInputLineLengthException, FlatwormConversionException,
      FlatwormUnsetFieldValueException, FlatwormCreatorException, FlatwormInvalidRecordException {
    for (int i = 0; i < elements.size(); ++i) {
      LineElement le = elements.get(i);
      if (le instanceof RecordElement) {
        try {
          parseDelimitedRecordElement((RecordElement) le, delimitedFields[currentField]);
          ++currentField;
        }
        catch (ArrayIndexOutOfBoundsException ex) {
          log.warn("Ran out of data on field " + i);
          // throw new
          // FlatwormInputLineLengthException("No data available for record-element "
          // + i);
        }
      }
      else if (le instanceof SegmentElement) {
        parseDelimitedSegmentElement((SegmentElement) le);
      }
    }
  }

  private void parseDelimitedRecordElement(RecordElement re, String fieldStr)
      throws FlatwormInputLineLengthException, FlatwormConversionException,
      FlatwormUnsetFieldValueException {
    String beanRef = re.getBeanRef();
    if (beanRef != null) {
      // JBL - to keep from dup. code, moved this to a private method
      mapField(fieldStr, re);
    }
  }

  private void parseDelimitedSegmentElement(SegmentElement segment)
      throws FlatwormCreatorException, FlatwormInputLineLengthException,
      FlatwormConversionException, FlatwormUnsetFieldValueException, FlatwormInvalidRecordException {
    int minCount = segment.getMinCount();
    int maxCount = segment.getMaxCount();
    if (maxCount <= 0) {
      maxCount = Integer.MAX_VALUE;
    }
    if (minCount < 0) {
      minCount = 0;
    }
    // TODO: handle allowance for a single instance that is for a field rather
    // than a list
    String beanRef = segment.getBeanRef();
    if (!segment.matchesId(delimitedFields[currentField]) && minCount > 0) {
      log.error("Segment " + segment.getName() + " with minimun required count of " + minCount
          + " missing.");
    }
    int cardinality = 0;
    try {
      while (currentField < delimitedFields.length
          && segment.matchesId(delimitedFields[currentField])) {
        if (beanRef != null) {
          ++cardinality;
          String parentRef = segment.getParentBeanRef();
          String addMethod = segment.getAddMethod();
          if (parentRef != null && addMethod != null) {
            Object instance = ParseUtils.newBeanInstance(beans.get(beanRef));
            beans.put(beanRef, instance);
            if (cardinality > maxCount) {
              if (segment.getCardinalityMode() == CardinalityMode.STRICT) {
                throw new FlatwormInvalidRecordException(
                    "Cardinality exceeded with mode set to STRICT");
              }
              else if (segment.getCardinalityMode() != CardinalityMode.RESTRICTED) {
                ParseUtils.invokeAddMethod(beans.get(parentRef), addMethod, instance);
              }
            }
            else {
              ParseUtils.invokeAddMethod(beans.get(parentRef), addMethod, instance);
            }
          }
          doParseDelimitedInput(segment.getElements());
        }
      }
    }
    finally {
      if (cardinality > maxCount) {
        log.error("Segment '" + segment.getName() + "' with maximum of " + maxCount
            + " encountered actual count of " + cardinality);
      }
    }
  }
}