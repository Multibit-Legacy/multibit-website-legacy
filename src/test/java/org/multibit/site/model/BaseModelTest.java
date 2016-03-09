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
    new BaseModel("/", Locale.UK, 0);

  }

  @Test
  public void testGetNavBar_EN() throws Exception {

    BaseModel testObject = new BaseModel("/en/index.html", Locale.UK, 0);

    assertThat(testObject.getNavBar()).contains("Download");

  }

  @Test
  public void testGetNavBar_EN_Malformed() throws Exception {

    thrown.expect(IllegalArgumentException.class);

    // Specific path overrides locale
    new BaseModel("/en/index.html", Locale.JAPANESE, 0);

  }

  @Test
  public void testGetNavBar_JA() throws Exception {

    BaseModel testObject = new BaseModel("/ja/index.html", Locale.JAPANESE, 0);

    assertThat(testObject.getNavBar()).contains("\u30c0\u30a6\u30f3\u30ed\u30fc\u30c9");

  }

  @Test
  public void testGetNavBar_ZH() throws Exception {

    BaseModel testObject = new BaseModel("/zh/index.html", Locale.CHINESE, 0);

    assertThat(testObject.getNavBar()).contains("\u4e0b\u8f7d");

  }

  @Test
  public void testGetNavBar_ES() throws Exception {

    BaseModel testObject = new BaseModel("/es/index.html", new Locale("es"), 0);

    assertThat(testObject.getNavBar()).contains("descargar");

  }

  @Test
  public void testGetNavBar_RU() throws Exception {

    BaseModel testObject = new BaseModel("/ru/index.html", new Locale("ru"), 0);

    assertThat(testObject.getNavBar()).contains("\u0421\u043a\u0430\u0447\u0430\u0442\u044c");

  }

}