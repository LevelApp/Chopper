package com.levelapp.choppertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.levelapp.annotation.ChainChopperable;
import com.levelapp.annotation.Chopp;
import com.levelapp.annotation.Chopper;
import com.levelapp.betterproguard.BetterProguardImpl_Chopperable;
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
  
  @Chopp()
  Object object = new Object();

  @Chopp(chopper = {SubscriptionChopperable.class})
  Subscription subscription = Subscriptions.empty();

  @Chopp(chopper = {SubscriptionChopperable.class})
  CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Chopp(chopper = {ButterKnifeChopperable.class})
  Unbinder unbinder;

  @Chopp(chopper = RealmChopperable.class)
  Realm realm;

  @Chopp(chopper = ChainChopperable.class)
  ChainField chainField = new ChainField();

  @Chopp(chopper = {DisposableChopperable.class /*, SomeOtherChopperable.class */})
  DisposeElement disposeField = new DisposeElement();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initActivity();
  }

  @Override
  protected void onDestroy() {
//    implement and call dispose()
//    or just:
    Chopper.chopp(this);
    super.onDestroy();
  }

//  private void dispose() {
//    if (unbinder != null){
//      unbinder.unbind();
//    }
//    unbinder = null;
//    if (subscription != null && subscription.isUnsubscribed() == false){
//      subscription.unsubscribe();
//    }
//    subscription = null;
//    if (compositeSubscription != null && compositeSubscription.isUnsubscribed() == false){
//      compositeSubscription.unsubscribe();
//    }
//    compositeSubscription = null;
//    if (realm != null && realm.isClosed() == false){
//      realm.close();
//    }
//    realm = null;
//    if (disposeField != null){
//      disposeField.dispose();
//    }
//    disposeField = null;
//  }

  private void initActivity() {
    Realm.init(this);
    Chopper.init(new BetterProguardImpl_Chopperable());
    realm = Realm.getDefaultInstance();
    setContentView(R.layout.activity_main);
    unbinder = ButterKnife.bind(this);
    startCounter();
    startRandom();
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
