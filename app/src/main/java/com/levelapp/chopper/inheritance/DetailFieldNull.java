package com.levelapp.chopper.inheritance;

import com.levelapp.annotation.Chopp;

/**
 * Created by rafaldziuryk on 01.01.17.
 */

public class DetailFieldNull extends BaseFieldNull {

  @Chopp
  public String s3;

  public DetailFieldNull(String s1, String s2, String s3){
    this.s1 = s1;
    this.s2 = s2;
    this.s3 = s3;
  }
}
