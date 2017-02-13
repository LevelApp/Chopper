Chopper
============

Field cleaning and destroying for java objects that delete references to and dispose/close object which uses annotation processing to generate boilerplate
code for you.

```java
class ExampleActivity extends Activity {
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
    Chopper.chopp(this, 20);
  }

  @Override
  protected void onStop() {
    super.onStop();
    Chopper.chopp(this, 50);
  }

  @Override
  protected void onDestroy() {
    Chopper.chopp(this, 100);
    super.onDestroy();
  }
}
```

Lifecycle
--------

Use annotation to make variable chopperable

```java
  @Chopp
```

Download
--------

```groovy
dependencies {
  compile 'com.github.levelapp.Chopper:chopperannotation:1.0.1'
  annotationProcessor 'com.github.levelapp.Chopper:chopperprocessor:1.0.1'
}

allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Android support
--------
```java
  @ChoppOnX(BroadcastReceiverChopperable.class)
  @ChoppOnX(RecyclerViewChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperandroid:1.0.1'
```

Other libraries support
--------

* ButterKnife
```java
  @ChoppOnX(ButterKnifeChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperbutterknife:1.0.1'
```

* RxJava
```java
  @ChoppOnX(SubscriptionChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava:1.0.1'
```

* RxJava2
```java
  @ChoppOnX(SubscriptionChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava2:1.0.1'
```

* Realm
```java
  @ChoppOnX(chopper = {RealmChopperable.class})
  @ChoppOnX(chopper = {RealmObjectChangeListenerChopperable.class})
  @ChoppOnX(chopper = {RealmResultChangeListenerChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrealm:1.0.1'
```

Proguard
--------


With `BetterProguardProcessor`

Call `init()` before first call `Chopper`, e.g. in `Application`.
With `BetterProguardProcessor` all classes can be fully minified.
BetterProguardFactoryImpl will be created after first success build.


```java
  Chopper.init(new BetterProguardFactoryImpl());
```


Without `BetterProguardProcessor`


```
#Chopper
-dontwarn com.levelapp.chopper.**
-keep class com.levelapp.chopper.** { *; }
-keep class **$$Chopperable { *; }
-keepclasseswithmembernames class * {
    @com.levelapp.annotation.* <fields>;
}
-keepnames class * { @com.levelapp.annotation.annotations.Chopp *;}
```


Own Chopperable
--------

Create own `Chopperable` implementation to destroy object in right way


```java
public class DisposableChopperable implements Chopperable<Disposable, Object> {

  @Override
  public void chopp(Disposable target, Object enclosed, int level) {
    if (target != null){
      target.dispose();
    }
  }
}
```

ChainChopperable
--------

With `ChainChopperable` `Chopper` create DESTROY chain.
Look at sample for better understanding

License
-------

    Copyright 2016 Rafał Dziuryk

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.