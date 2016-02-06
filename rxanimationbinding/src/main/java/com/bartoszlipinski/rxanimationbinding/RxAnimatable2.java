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
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import rx.Observable;

import static com.bartoszlipinski.rxanimationbinding.internal.Preconditions.checkNotNull;

@TargetApi(Build.VERSION_CODES.M)
public class RxAnimatable2 {

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.M)
    public static Observable<Drawable> starts(Animatable2 animatable2){
        checkNotNull(animatable2, "animatable2 == null");
        return Observable.create(new Animatable2ListenerOnSubscribe(animatable2, AnimationEvent.START));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.M)
    public static Observable<Drawable> ends(Animatable2 animatable2){
        checkNotNull(animatable2, "animatable2 == null");
        return Observable.create(new Animatable2ListenerOnSubscribe(animatable2, AnimationEvent.END));
    }
}
