package org.multibit.site.utils;

import org.joda.time.DateTime;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * <p>JAXB adapter to provide the following to Joda DateTime:</p>
 * <ul>
 * <li>JAXB un/marshalling</li>
 * </ul>
 *
 * @since 3.0.0
 *  
 */
public class DateTimeJaxbAdapter extends XmlAdapter<String, DateTime> {

  public DateTime unmarshal(String v) throws Exception {
    return new DateTime(v);
  }

  public String marshal(DateTime v) throws Exception {
    return v.toString();
  }

}