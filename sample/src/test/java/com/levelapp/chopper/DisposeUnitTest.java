package com.levelapp.chopper;

import com.levelapp.annotation.chopperable.Chopperable;
import com.levelapp.choppertest.dispose.Disposable;
import com.levelapp.choppertest.dispose.DisposableChopperable;
import com.levelapp.choppertest.dispose.DisposeElement;
import com.levelapp.choppertest.dispose.DisposeField;
import org.junit.Assert;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DisposeUnitTest {

  @Test
  public void disposeSimpleTest() throws Exception {
    DisposeField.disposed = false;
    Chopperable<Disposable, Object> chopper = new DisposableChopperable();
    DisposeElement disposeElement = new DisposeElement();
    Assert.assertFalse(DisposeField.disposed);
    chopper.chopp(disposeElement, this);
    Assert.assertTrue(DisposeField.disposed);
  }
}