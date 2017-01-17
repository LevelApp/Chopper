package com.levelapp.choppertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.levelapp.annotation.annotations.ChoppOnPause;
import com.levelapp.annotation.annotations.ChoppOnStop;
import com.levelapp.annotation.chopperable.ChainChopperable;
import com.levelapp.annotation.Chopper;
import com.levelapp.annotation.annotations.ChoppOnDestroy;
import com.levelapp.betterproguard.BetterProguardFactoryImpl;
import com.levelapp.betterproguard.BetterProguardImpl_ChopperableOnDestroy;
import com.levelapp.betterproguard.BetterProguardImpl_ChopperableOnDestroyView;
import com.levelapp.betterproguard.BetterProguardImpl_ChopperableOnPause;
import com.levelapp.betterproguard.BetterProguardImpl_ChopperableOnStop;
import com.levelapp.butterknifechopper.ButterKnifeChopperable;
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

  @ChoppOnDestroy
  Object object = new Object();

  @ChoppOnStop(SubscriptionChopperable.class)
  Subscription subscription = Subscriptions.empty();

  @ChoppOnStop(SubscriptionChopperable.class)
  CompositeSubscription compositeSubscription = new CompositeSubscription();

  @ChoppOnDestroy(ButterKnifeChopperable.class)
  Unbinder unbinder;

  @ChoppOnDestroy(RealmChopperable.class)
  Realm realm;

  @ChoppOnPause(ChainChopperable.class)
  ChainField chainField = new ChainField();

  @ChoppOnDestroy({DisposableChopperable.class /*, SomeOtherChopperable.class */})
  DisposeElement disposeField = new DisposeElement();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initActivity();
  }

  @Override
  protected void onPause() {
    super.onPause();
    Chopper.onPause(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    Chopper.onStop(this);
  }

  @Override
  protected void onDestroy() {
    Chopper.onDestroy(this);
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
    Chopper.init(new BetterProguardFactoryImpl());
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
          public void call(Long rand) {
            random.setText("Random: " + rand);
          }
        });
    compositeSubscription.add(randomSubscription);
  }

  private void startCounter() {
    subscription = Observable.interval(1, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Long>() {
          @Override
          public void call(Long count) {
            counter.setText("Count: " + count);
          }
        });
  }
}
