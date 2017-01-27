package com.levelapp.chopper;

import static org.junit.Assert.assertTrue;

import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.choppertest.dispose.DisposeElement;
import com.levelapp.choppertest.inheritance.DetailFieldNull;
import com.levelapp.choppertest.inheritance.VeryBaseFieldNull;
import com.levelapp.choppertest.nullcheck.AllFieldToNull;
import com.levelapp.choppertest.nullcheck.AllFieldToNull_Chopperable;
import com.levelapp.choppertest.nullcheck.SomeFieldToNull;
import com.levelapp.choppertest.nullcheck.SomeFieldToNull_Chopperable;
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
    AllFieldToNull_Chopperable chopper = new AllFieldToNull_Chopperable();
    chopper.chopp(allFieldToNull, this, Chopperable.DEFAULT_LEVEL);
    assertTrue(allFieldToNull.s1 == null);
    assertTrue(allFieldToNull.s2 == null);
  }

  @Test
  public void nullCheckSomeFieldTest() throws Exception {
    SomeFieldToNull someFieldToNull = new SomeFieldToNull("Test1", "Test2");
    SomeFieldToNull_Chopperable chopper = new SomeFieldToNull_Chopperable();
    chopper.chopp(someFieldToNull, this, Chopperable.DEFAULT_LEVEL);
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
  public void chopperInheritanceFieldTest() throws Exception {
    DetailFieldNull veryBaseFieldNull = new DetailFieldNull(new DisposeElement(), "Test2", "Test3");
    Chopper.chopp(veryBaseFieldNull);
    assertTrue(veryBaseFieldNull.s1 == null);
    assertTrue(veryBaseFieldNull.s2 == null);
    assertTrue(veryBaseFieldNull.s3 == null);
  }

  @Test
  public void chopperInheritance2FieldTest() throws Exception {
    VeryBaseFieldNull veryBaseFieldNull = new DetailFieldNull(new DisposeElement(), "Test2",
        "Test3");
    Chopper.chopp(veryBaseFieldNull);
    DetailFieldNull detailFieldNull = (DetailFieldNull) veryBaseFieldNull;
    assertTrue(detailFieldNull.s1 == null);
    assertTrue(detailFieldNull.s2 == null);
    assertTrue(detailFieldNull.s3 == null);
  }
}