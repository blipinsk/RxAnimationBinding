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

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;

import rx.Observable;

import static com.bartoszlipinski.rxanimationbinding.internal.Preconditions.checkNotNull;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class RxTransition {

    public static Observable<Transition> starts(Transition transition) {
        checkNotNull(transition, "transition == null");
        return Observable.create(new TransitionListenerOnSubscribe(transition, AnimationEvent.START));
    }

    public static Observable<Transition> ends(Transition transition) {
        checkNotNull(transition, "transition == null");
        return Observable.create(new TransitionListenerOnSubscribe(transition, AnimationEvent.END));
    }

    public static Observable<Transition> cancels(Transition transition) {
        checkNotNull(transition, "transition == null");
        return Observable.create(new TransitionListenerOnSubscribe(transition, AnimationEvent.CANCEL));
    }

    public static Observable<Transition> pauses(Transition transition) {
        checkNotNull(transition, "transition == null");
        return Observable.create(new TransitionListenerOnSubscribe(transition, AnimationEvent.PAUSE));
    }

    public static Observable<Transition> resumes(Transition transition) {
        checkNotNull(transition, "transition == null");
        return Observable.create(new TransitionListenerOnSubscribe(transition, AnimationEvent.RESUME));
    }

}
