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

class Bean {
  private String beanName;
  private String beanClass;
  private Class beanObjectClass;

  public String getBeanName() {
    return beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public String getBeanClass() {
    return beanClass;
  }

  public void setBeanClass(String beanClass) {
    this.beanClass = beanClass;
  }

  public Class getBeanObjectClass() {
    return beanObjectClass;
  }

  public void setBeanObjectClass(Class beanObjectClass) {
    this.beanObjectClass = beanObjectClass;
  }

}