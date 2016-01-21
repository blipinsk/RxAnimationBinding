/**
 * Copyright 2016 Bartosz Lipinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bartoszlipinski.rxanimationbinding;

import android.view.animation.Animation;

import rx.Observable;

import static com.bartoszlipinski.rxanimationbinding.internal.Preconditions.checkNotNull;

public class RxAnimation {

    public static Observable<Void> starts(Animation animation){
        checkNotNull(animation, "animation == null");
        return Observable.create(new AnimationListenerOnSubscribe(animation, AnimationEvent.START));
    }

    public static Observable<Void> ends(Animation animation){
        checkNotNull(animation, "animation == null");
        return Observable.create(new AnimationListenerOnSubscribe(animation, AnimationEvent.END));
    }

    public static Observable<Void> repeats(Animation animation){
        checkNotNull(animation, "animation == null");
        return Observable.create(new AnimationListenerOnSubscribe(animation, AnimationEvent.REPEAT));
    }
}
