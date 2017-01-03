package com.levelapp.chopper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.levelapp.annotation.Chopp;
import com.levelapp.annotation.Chopper;
import com.levelapp.butterknifechopper.ButterKnifeChopperable;
import com.levelapp.chopper.nullcheck.AllFieldToNull;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Chopp(chopper = {ButterKnifeChopperable.class})
  Unbinder unbinder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    setSupportActionBar(toolbar);
  }

  @Override
  protected void onDestroy() {
    Chopper.chopp(this);
    super.onDestroy();
  }
}
