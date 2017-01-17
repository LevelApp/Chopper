Chopper
============

Field cleaning and destroying for java objects that delete references to and dispose/close object which uses annotation processing to generate boilerplate
code for you.

```java
class ExampleActivity extends Activity {
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
}
```

Lifecycle
--------

You can use one of 4 lifecycle method. 

```java
  @ChoppOnPause
  @ChoppOnStop
  @ChoppOnDestroyView
  @ChoppOnDestroy
```
```

Download
--------

```groovy
dependencies {
  compile 'com.github.levelapp.Chopper:chopperannotation:0.9.0'
  annotationProcessor 'com.github.levelapp.Chopper:chopperprocessor:0.9.0'
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
  compile 'com.github.levelapp.Chopper:chopperandroid:0.9.0'
```

Other libraries support
--------

* ButterKnife
```java
  @ChoppOnX(ButterKnifeChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperbutterknife:0.9.0'
```

* RxJava
```java
  @ChoppOnX(SubscriptionChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava:0.9.0'
```

* RxJava2
```java
  @ChoppOnX(SubscriptionChopperable.class)
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava2:0.9.0'
```

* Realm
```java
  @ChoppOnX(chopper = {RealmChopperable.class})
  @ChoppOnX(chopper = {RealmObjectChangeListenerChopperable.class})
  @ChoppOnX(chopper = {RealmResultChangeListenerChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrealm:0..0'
```

Proguard
--------


With `BetterProguardProcessor`

Call `init()` before first call `Chopper`, e.g. in `Application`.
With `BetterProguardProcessor` all classes can be fully minified.
BetterProguardImpl_Chopperable will be created after first success build.


```java
  Chopper.init(new BetterProguardFactoryImpl_Chopperable());
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
-keepnames class * { @com.levelapp.annotation.annotations.ChoppOnPause *;}
-keepnames class * { @com.levelapp.annotation.annotations.ChoppOnStop *;}
-keepnames class * { @com.levelapp.annotation.annotations.ChoppOnDestroyView *;}
-keepnames class * { @com.levelapp.annotation.annotations.ChoppOnDestroy *;}
```


Own Chopperable
--------

Create own `Chopperable` implementation to destroy object in right way


```java
public class DisposableChopperable implements Chopperable<Disposable, Object> {

  @Override
  public void chopp(Disposable target, Object enclosed, Lifecycle lifecycle) {
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