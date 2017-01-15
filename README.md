Chopper
============

Field cleaning and destroying for java objects that delete references to and dispose/close object which uses annotation processing to generate boilerplate
code for you.

```java
class ExampleActivity extends Activity {
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
  protected void onDestroy() {
    super.onDestroy();
    Chopper.chopp(this);
  }
}
```

Download
--------

```groovy
dependencies {
  compile 'com.github.levelapp.Chopper:chopperannotation:0.8.0'
  annotationProcessor 'com.github.levelapp.Chopper:chopperprocessor:0.8.0'
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
  @Chopp(chopper = {BroadcastReceiverChopperable.class})
  @Chopp(chopper = {RecyclerViewChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperandroid:0.8.0'
```

Other libraries support
--------

* ButterKnife
```java
  @Chopp(chopper = {ButterKnifeChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperbutterknife:0.8.0'
```

* RxJava
```java
  @Chopp(chopper = {SubscriptionChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava:0.8.0'
```

* RxJava2
```java
  @Chopp(chopper = {SubscriptionChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava2:0.8.0'
```

* Realm
```java
  @Chopp(chopper = {RealmChopperable.class})
  @Chopp(chopper = {RealmObjectChangeListenerChopperable.class})
  @Chopp(chopper = {RealmResultChangeListenerChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrealm:0.8.0'
```

Proguard
--------


With `BetterProguardProcessor`

Call `init()` before first call `Chopper`, e.g. in `Application`.
With `BetterProguardProcessor` all classes can be fully minified.
BetterProguardImpl_Chopperable will be created after first success build.


```java
  Chopper.init(new BetterProguardImpl_Chopperable());
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
```


Own Chopperable
--------

Create own `Chopperable` implementation to destroy object in right way


```java
public class DisposableChopperable implements Chopperable<Disposable, Object> {

  @Override
  public void chopp(Disposable target, Object enclosed) {
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