package com.levelapp.chopper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.levelapp.annotation.Chopp;
import com.levelapp.annotation.Chopper;
import com.levelapp.chopper.nullcheck.AllFieldToNull;

public class MainActivity extends AppCompatActivity {

  @Chopp
  AllFieldToNull allFieldToNull = new AllFieldToNull("s1,", "s2");

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Chopper.chopp(this);
  }
}
