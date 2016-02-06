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

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewPropertyAnimator;

import rx.Observable;

import static com.bartoszlipinski.rxanimationbinding.internal.Preconditions.checkNotNull;

public class RxViewPropertyAnimator {

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> starts(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(v.animate(), AnimationEvent.START));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> starts(ViewPropertyAnimator animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.START));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> ends(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(v.animate(), AnimationEvent.END));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> ends(ViewPropertyAnimator animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.END));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> cancels(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(v.animate(), AnimationEvent.CANCEL));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> cancels(ViewPropertyAnimator animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.CANCEL));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> repeats(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(v.animate(), AnimationEvent.REPEAT));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Observable<Animator> repeats(ViewPropertyAnimator animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.REPEAT));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Observable<ValueAnimator> updates(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(new ViewPropertyAnimatorUpdateListenerOnSubscribe(v.animate()));
    }

    @CheckResult
    @NonNull
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static Observable<ValueAnimator> updates(ViewPropertyAnimator animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(new ViewPropertyAnimatorUpdateListenerOnSubscribe(animator));
    }

}
