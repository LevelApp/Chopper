Chopper
============

Field destroying for java objects that delete references to and dispose/close object which uses annotation processing to generate boilerplate
code for you.

```java
class ExampleActivity extends Activity {
  @Chopper() 
  SomeObject someObject;

  @Chopper() 
  SomeOtherObject someOtherObject;
  
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
  compile 'com.github.levelapp.Chopper:chopperannotation:0.5'
  annotationProcessor 'com.github.levelapp.Chopper:chopperprocessor:0.5'
}

allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Other libraries support
--------
* ButterKnife
```java
  @Chopp(chopper = {ButterKnifeChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperbutterknife:0.5'
```

* RxJava
```java
  @Chopp(chopper = {SubscriptionChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava:0.5'
```

* RxJava2
```java
  @Chopp(chopper = {SubscriptionChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrxjava2:0.5'
```

* Realm
```java
  @Chopp(chopper = {RealmChopperable.class})
```
```groovy
  compile 'com.github.levelapp.Chopper:chopperrealm:0.5'
```

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