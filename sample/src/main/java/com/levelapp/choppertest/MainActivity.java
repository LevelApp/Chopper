package com.levelapp.choppertest;

import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//  @BindView(R.id.Counter_Text)
//  TextView counter;
//
//  @BindView(R.id.Random_Text)
//  TextView random;
//
//  @Chopp
//  Object object = new Object();
//
//  @Chopp(value = SubscriptionChopperable.class, level = 20)
//  Subscription subscription = Subscriptions.empty();
//
//  @Chopp(SubscriptionChopperable.class)
//  CompositeSubscription compositeSubscription = new CompositeSubscription();
//
//  @Chopp(value = ButterKnifeUnbindChopperable.class, level = 100)
//  Unbinder unbinder;
//
//  @Chopp(value = RealmChopperable.class, level = 50)
//  Realm realm;
//
//  @Chopp(ChainChopperable.class)
//  ChainField chainField = new ChainField();
//
//  @Chopp({DisposableChopperable.class /*, SomeOtherChopperable.class */})
//  DisposeElement disposeField = new DisposeElement();

//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    initActivity();
//  }
//
//  @Override
//  protected void onPause() {
//    super.onPause();
//    Chopper.chopp(this, 20);
//  }
//
//  @Override
//  protected void onStop() {
//    super.onStop();
//    Chopper.chopp(this, 50);
//  }
//
//  @Override
//  protected void onDestroy() {
//    super.onDestroy();
//    Chopper.chopp(this, 100);
//  }
//
//  private void initActivity() {
//    Realm.init(this);
//    initChopper();
//    realm = Realm.getDefaultInstance();
//    setContentView(R.layout.activity_main);
//    unbinder = ButterKnife.bind(this);
//    startCounter();
//    startRandom();
//  }
//
//  private void initChopper() {
//    Chopper.init(new BetterProguardImpl());
//  }
//
//  private void startRandom() {
//    final Random randomizer = new Random();
//    Subscription randomSubscription = Observable.interval(1, TimeUnit.SECONDS)
//        .map(new Func1<Long, Long>() {
//          @Override
//          public Long call(Long aLong) {
//            return aLong * randomizer.nextInt();
//          }
//        })
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Action1<Long>() {
//          @Override
//          public void call(Long aLong) {
//            random.setText("Random: " + aLong);
//          }
//        });
//    compositeSubscription.add(randomSubscription);
//  }
//
//  private void startCounter() {
//    subscription = Observable.interval(1, TimeUnit.SECONDS)
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Action1<Long>() {
//          @Override
//          public void call(Long aLong) {
//            counter.setText("Count: " + aLong);
//          }
//        });
//  }
}
