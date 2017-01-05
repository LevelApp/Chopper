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

  @Chopp(chopper = {SubscriptionChopperable.class})
  Subscription subscription = Subscriptions.empty();

  @Chopp(chopper = {SubscriptionChopperable.class})
  CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Chopp(chopper = {ButterKnifeChopperable.class})
  Unbinder unbinder;

  @Chopp(chopper = RealmChopperable.class)
  Realm realm;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Realm.init(this);
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
//  }
}
