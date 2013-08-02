/* Any copyright is dedicated to the Public Domain.
   http://creativecommons.org/publicdomain/zero/1.0/ */

package org.mozilla.gecko.sync.crypto.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import org.junit.Test;
import org.mozilla.gecko.sync.Utils;
import org.mozilla.gecko.sync.crypto.PBKDF2;

public class TestPBKDF2 {

  @Test
  public final void testPBKDF2SHA1A() throws GeneralSecurityException, UnsupportedEncodingException {
    String  p = "password";
    String  s = "salt";
    int dkLen = 20;

    checkPBKDF2SHA1(p, s, 1, dkLen, "0c60c80f961f0e71f3a9b524af6012062fe037a6");
    checkPBKDF2SHA1(p, s, 2, dkLen, "ea6c014dc72d6f8ccd1ed92ace1d41f0d8de8957");
    checkPBKDF2SHA1(p, s, 4096, dkLen, "4b007901b765489abead49d926f721d065a429c1");
  }

  @Test
  public final void testPBKDF2SHA1B() throws GeneralSecurityException, UnsupportedEncodingException {
    String  p = "passwordPASSWORDpassword";
    String  s = "saltSALTsaltSALTsaltSALTsaltSALTsalt";
    int dkLen = 25;

    checkPBKDF2SHA1(p, s, 4096, dkLen, "3d2eec4fe41c849b80c8d83662c0e44a8b291a964cf2f07038");
  }

  @Test
  public final void testPBKDF2SHA256A() throws UnsupportedEncodingException, GeneralSecurityException {
    String  p = "password";
    String  s = "salt";
    int dkLen = 32;

    checkPBKDF2SHA256(p, s, 1, dkLen, "120fb6cffcf8b32c43e7225256c4f837a86548c92ccc35480805987cb70be17b");
    checkPBKDF2SHA256(p, s, 4096, dkLen, "c5e478d59288c841aa530db6845c4c8d962893a001ce4e11a4963873aa98134a");
  }

  @Test
  public final void testPBKDF2SHA256B() throws UnsupportedEncodingException, GeneralSecurityException {
    String  p = "passwordPASSWORDpassword";
    String  s = "saltSALTsaltSALTsaltSALTsaltSALTsalt";
    int dkLen = 40;

    checkPBKDF2SHA256(p, s, 4096, dkLen, "348c89dbcbd32b2f32d814b8116e84cf2b17347ebc1800181c4e2a1fb8dd53e1c635518c7dac47e9");
  }

  @Test
  public final void testPBKDF2SHA256scryptA() throws UnsupportedEncodingException, GeneralSecurityException {
    String  p = "passwd";
    String  s = "salt";
    int dkLen = 64;

    checkPBKDF2SHA256(p, s, 1, dkLen, "55ac046e56e3089fec1691c22544b605f94185216dde0465e68b9d57c20dacbc49ca9cccf179b645991664b39d77ef317c71b845b1e30bd509112041d3a19783");
  }

  @Test
  public final void testPBKDF2SHA256scryptB() throws UnsupportedEncodingException, GeneralSecurityException {
    String  p = "Password";
    String  s = "NaCl";
    int dkLen = 64;

    checkPBKDF2SHA256(p, s, 80000, dkLen, "4ddcd8f60b98be21830cee5ef22701f9641a4418d04c0414aeff08876b34ab56a1d425a1225833549adb841b51c9b3176a272bdebba1d078478f62b397f33c8d");
  }

  @Test
  public final void testPBKDF2SHA256C() throws UnsupportedEncodingException, GeneralSecurityException {
    String  p = "pass\0word";
    String  s = "sa\0lt";
    int dkLen = 16;

    checkPBKDF2SHA256(p, s, 4096, dkLen, "89b69d0516f829893c696226650a8687");
  }

  /*
  // This test takes two or three minutes to run, so we don't.
  public final void testPBKDF2SHA256D() throws UnsupportedEncodingException, GeneralSecurityException {
    String  p = "password";
    String  s = "salt";
    int dkLen = 32;

    checkPBKDF2SHA256(p, s, 16777216, dkLen, "cf81c66fe8cfc04d1f31ecb65dab4089f7f179e89b3b0bcb17ad10e3ac6eba46");
  }
  */

  @Test
  public final void testTimePBKDF2SHA256() throws UnsupportedEncodingException, GeneralSecurityException {
    checkPBKDF2SHA256("password", "salt", 80000, 32, null);
  }

  private void checkPBKDF2SHA1(String p, String s, int c, int dkLen,
                              final String expectedStr)
                                                    throws GeneralSecurityException,
                                                    UnsupportedEncodingException {
    long start = System.currentTimeMillis();
    byte[] key = PBKDF2.pbkdf2SHA1SC(p.getBytes("US-ASCII"), s.getBytes("US-ASCII"), c, dkLen);
    long end = System.currentTimeMillis();
    System.err.println("SHA-1 " + c + " took " + (end - start) + "ms");
    assertExpectedBytes(expectedStr, key);
  }

  private void checkPBKDF2SHA256(String p, String s, int c, int dkLen,
                                final String expectedStr)
                                                    throws GeneralSecurityException, UnsupportedEncodingException {
    long start = System.currentTimeMillis();
    byte[] key = PBKDF2.pbkdf2SHA256(p.getBytes("US-ASCII"), s.getBytes("US-ASCII"), c, dkLen);
    assertNotNull(key);

    long end = System.currentTimeMillis();

    System.err.println("SHA-256 " + c + " took " + (end - start) + "ms");
    if (expectedStr == null) {
      return;
    }

    assertEquals(dkLen, Utils.hex2Byte(expectedStr).length);
    assertExpectedBytes(expectedStr, key);
  }

  public static void assertExpectedBytes(final String expectedStr, byte[] key) {
    assertEquals(expectedStr, Utils.byte2hex(key));
    byte[] expected = Utils.hex2Byte(expectedStr);

    assertEquals(expected.length, key.length);
    for (int i = 0; i < key.length; i++) {
      assertEquals(expected[i], key[i]);
    }
  }
}
