RxAnimationBinding
==================

[![License](https://img.shields.io/github/license/blipinsk/RxAnimationBinding.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/com.bartoszlipinski/rxanimationbinding.svg)](http://gradleplease.appspot.com/#rxanimationbinding)
[![Bintray](https://img.shields.io/bintray/v/blipinsk/maven/RxAnimationBinding.svg)](https://bintray.com/blipinsk/maven/RxAnimationBinding/_latestVersion)

RxJava binding APIs for Android's animations

Usage
-----

Base platform bindings:

```groovy
compile 'com.bartoszlipinski:rxanimationbinding:1.0.0-beta1'
```

Contents: `RxAnimation`, `RxAnimator`, `RxValueAnimator`, `RxViewPropertyAnimator`, `RxTransition` and `RxAnimatable2`.

Ongoing development
-------------------

The published version (`1.0.0-beta1`) is a pre-release. When I finish writing tests, I will publish a solid `1.0.0` release.
  
Apart from that I'm working on bindings for `animators` included in the `support-v4`. Comming soon.

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
