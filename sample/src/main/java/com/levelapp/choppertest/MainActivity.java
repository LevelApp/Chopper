package com.levelapp.choppertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.annotations.Chopp;
import com.levelapp.annotation.chopperable.ChainChopperable;
import com.levelapp.betterproguard.BetterProguardImpl;
import com.levelapp.butterknifechopper.ButterKnifeUnbindChopperable;
import com.levelapp.chopper.R;
import com.levelapp.chopper.SubscriptionChopperable;
import com.levelapp.choppertest.chain.ChainField;
import com.levelapp.choppertest.dispose.DisposableChopperable;
import com.levelapp.choppertest.dispose.DisposeElement;
import com.levelapp.realmchopper.RealmChopperable;
import io.realm.Realm;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.Counter_Text)
  TextView counter;

  @BindView(R.id.Random_Text)
  TextView random;

  @Chopp
  Object object = new Object();

  @Chopp(value = SubscriptionChopperable.class, level = 20)
  Subscription subscription = Subscriptions.empty();

  @Chopp(SubscriptionChopperable.class)
  CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Chopp(value = ButterKnifeUnbindChopperable.class, level = 100)
  Unbinder unbinder;

  @Chopp(value = RealmChopperable.class, level = 50)
  Realm realm;

  @Chopp(ChainChopperable.class)
  ChainField chainField = new ChainField();

  @Chopp({DisposableChopperable.class /*, SomeOtherChopperable.class */})
  DisposeElement disposeField = new DisposeElement();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initActivity();
  }

  @Override
  protected void onPause() {
    super.onPause();
    Chopper.chopp(this, 0);
  }

  @Override
  protected void onStop() {
    super.onStop();
    Chopper.chopp(this, 0);
  }

  @Override
  protected void onDestroy() {
    Chopper.chopp(this, 0);
    super.onDestroy();
  }

  private void initActivity() {
    Realm.init(this);
    initChopper();
    realm = Realm.getDefaultInstance();
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    startCounter();
    startRandom();
  }

  private void initChopper() {
    Chopper.init(new BetterProguardImpl());
  }

  private void startRandom() {
    final Random randomizer = new Random();
    Subscription randomSubscription = Observable.interval(1, TimeUnit.SECONDS)
        .map(new Func1<Long, Long>() {
          @Override
          public Long call(Long aLong) {
            return aLong * randomizer.nextInt();
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Long>() {
          @Override
          public void call(Long aLong) {
            random.setText("Random: " + aLong);
          }
        });
    compositeSubscription.add(randomSubscription);
  }

  private void startCounter() {
    subscription = Observable.interval(1, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Long>() {
          @Override
          public void call(Long aLong) {
            counter.setText("Count: " + aLong);
          }
        });
  }
}
