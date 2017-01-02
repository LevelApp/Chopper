package com.levelapp.chopper;

import static org.junit.Assert.assertTrue;

import com.levelapp.annotation.Chopper;
import com.levelapp.chopper.dispose.DisposeElement;
import com.levelapp.chopper.inheritance.DetailFieldNull;
import com.levelapp.chopper.inheritance.VeryBaseFieldNull;
import com.levelapp.chopper.nullcheck.AllFieldToNull;
import com.levelapp.chopper.nullcheck.AllFieldToNull$$Chopperable;
import com.levelapp.chopper.nullcheck.AvoidNullFieldToNull;
import com.levelapp.chopper.nullcheck.SomeFieldToNull;
import com.levelapp.chopper.nullcheck.SomeFieldToNull$$Chopperable;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NullUnitTest {

  @Test
  public void nullCheckAllFieldTest() throws Exception {
    AllFieldToNull allFieldToNull = new AllFieldToNull("Test1", "Test2");
    AllFieldToNull$$Chopperable chopper = new AllFieldToNull$$Chopperable();
    chopper.chopp(allFieldToNull);
    assertTrue(allFieldToNull.s1 == null);
    assertTrue(allFieldToNull.s2 == null);
  }

  @Test
  public void nullCheckSomeFieldTest() throws Exception {
    SomeFieldToNull someFieldToNull = new SomeFieldToNull("Test1", "Test2");
    SomeFieldToNull$$Chopperable chopper = new SomeFieldToNull$$Chopperable();
    chopper.chopp(someFieldToNull);
    assertTrue(someFieldToNull.s1 == null);
    assertTrue(someFieldToNull.s2 != null);
  }

  @Test
  public void chopperAllFieldTest() throws Exception {
    AllFieldToNull allFieldToNull = new AllFieldToNull("Test1", "Test2");
    Chopper.chopp(allFieldToNull);
    assertTrue(allFieldToNull.s1 == null);
    assertTrue(allFieldToNull.s2 == null);
  }

  @Test
  public void chopperSomeFieldTest() throws Exception {
    SomeFieldToNull someFieldToNull = new SomeFieldToNull("Test1", "Test2");
    Chopper.chopp(someFieldToNull);
    assertTrue(someFieldToNull.s1 == null);
    assertTrue(someFieldToNull.s2 != null);
  }

  @Test
  public void chopperAvoidFieldTest() throws Exception {
    AvoidNullFieldToNull avoidNullFieldToNull = new AvoidNullFieldToNull("Test1", "Test2");
    Chopper.chopp(avoidNullFieldToNull);
    assertTrue(avoidNullFieldToNull.s1 != null);
    assertTrue(avoidNullFieldToNull.s2 != null);
  }

  @Test
  public void chopperInheritanceFieldTest() throws Exception {
    DetailFieldNull veryBaseFieldNull = new DetailFieldNull(new DisposeElement(), "Test2", "Test3");
    Chopper.chopp(veryBaseFieldNull);
    assertTrue(veryBaseFieldNull.s1 == null);
    assertTrue(veryBaseFieldNull.s2 == null);
    assertTrue(veryBaseFieldNull.s3 == null);
  }

  @Test
  public void chopperInheritance2FieldTest() throws Exception {
    VeryBaseFieldNull veryBaseFieldNull = new DetailFieldNull(new DisposeElement(), "Test2", "Test3");
    Chopper.chopp(veryBaseFieldNull);
    DetailFieldNull detailFieldNull = (DetailFieldNull) veryBaseFieldNull;
    assertTrue(detailFieldNull.s1 == null);
    assertTrue(detailFieldNull.s2 == null);
    assertTrue(detailFieldNull.s3 == null);
  }
}