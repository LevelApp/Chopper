package com.levelapp.chopper;

import com.levelapp.annotation.Chopperable;
import com.levelapp.chopper.dispose.Disposable;
import com.levelapp.chopper.dispose.DisposableChopperable;
import com.levelapp.chopper.dispose.DisposeElement;
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
    Chopperable<Disposable, Object> chopper = new DisposableChopperable();
    DisposeElement disposeElement = new DisposeElement();
    Assert.assertFalse(disposeElement.isDisposed());
    chopper.chopp(disposeElement, this);
    Assert.assertTrue(disposeElement.isDisposed());
  }
}