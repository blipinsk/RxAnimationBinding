RxAnimationBinding
==================

[![License](https://img.shields.io/github/license/blipinsk/RxAnimationBinding.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.bartoszlipinski/rxanimationbinding.svg)](http://gradleplease.appspot.com/#rxanimationbinding)
[![Bintray](https://img.shields.io/bintray/v/blipinsk/maven/RxAnimationBinding.svg)](https://bintray.com/blipinsk/maven/RxAnimationBinding/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-RxAnimationBinding-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/3069)

RxJava binding APIs for Android's animations

Usage
-----

Base platform bindings:

```groovy
compile 'com.bartoszlipinski:rxanimationbinding:1.0.0'
```

Contents: `RxAnimation`, `RxAnimator`, `RxValueAnimator`, `RxViewPropertyAnimator`, `RxTransition` and `RxAnimatable2`.

*For a working implementation of this library see the `sample/` folder.*

Simple example:
--------------

    ValueAnimator animator = ValueAnimator.ofInt(4, 8, 15, 16, 23, 42);
    animator.setDuration(108);
    animator.setRepeatCount(ValueAnimator.INFINITE);

    RxValueAnimator.repeats(animator)
            .subscribe(new Action1<Animator>() {
                @Override
                public void call(Animator animator) {
                    pressTheExecuteButton();
                }
            });
    RxValueAnimator.cancels(animator)
            .subscribe(new Action1<Animator>() {
                @Override
                public void call(Animator animator) {
                    systemFailure();
                }
            });
                
**REMEMBER TO UNSUBSCRIBE YOUR SUBSCRIPTIONS WHEN YOU'RE DONE WITH THEM!**

Ongoing development
-------------------
  
Currently I'm working on bindings for `animators` included in the `support-v4`. Coming soon.

Credits
-------
This library has been both inspired and (let’s not lie to ourselves) heavily influenced by Jake Wharton’s RxBinding.

Few classes come directly from the library (I hope I marked them all). All credits for those goes to Jake and all other contributors of RxBinding.

Thanks!

License
=======

    Copyright 2016 Bartosz Lipiński
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
