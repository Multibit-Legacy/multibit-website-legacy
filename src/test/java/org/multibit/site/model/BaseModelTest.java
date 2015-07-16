package org.multibit.site.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;

import static org.fest.assertions.api.Assertions.assertThat;

public class BaseModelTest {

  @Rule
  public ExpectedException thrown= ExpectedException.none();

  @Test
  public void testInitialise_Malformed() throws Exception {

    thrown.expect(IllegalArgumentException.class);
    new BaseModel("/",true, Locale.UK);

  }

  @Test
  public void testGetNavBar_EN() throws Exception {

    BaseModel testObject = new BaseModel("/en/index.html",true, Locale.UK);

    assertThat(testObject.getNavBar()).contains("Download");

  }

  @Test
  public void testGetNavBar_EN_Malformed() throws Exception {

    thrown.expect(IllegalArgumentException.class);

    // Specific path overrides locale
    BaseModel testObject = new BaseModel("/en/index.html",true, Locale.JAPANESE);

  }

  @Test
  public void testGetNavBar_JA() throws Exception {

    BaseModel testObject = new BaseModel("/ja/index.html",true, Locale.JAPANESE);

    assertThat(testObject.getNavBar()).contains("\u30c0\u30a6\u30f3\u30ed\u30fc\u30c9");

  }

  @Test
  public void testGetNavBar_ZH() throws Exception {

    BaseModel testObject = new BaseModel("/zh/index.html",true, Locale.CHINESE);

    assertThat(testObject.getNavBar()).contains("\u4e0b\u8f7d");

  }

}