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
package com.bartoszlipinski.rxanimationbinding.support.v4;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

import com.bartoszlipinski.rxanimationbinding.AnimationEvent;

import rx.Observable;

import static com.bartoszlipinski.rxanimationbinding.internal.Preconditions.checkNotNull;

public final class RxViewPropertyAnimatorCompat {

    @CheckResult
    @NonNull
    public static Observable<View> starts(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(
                new ViewPropertyAnimatorListenerOnSubscribe(ViewCompat.animate(v), AnimationEvent.START));
    }

    @CheckResult
    @NonNull
    public static Observable<View> starts(ViewPropertyAnimatorCompat animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(
                new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.START));
    }

    @CheckResult
    @NonNull
    public static Observable<View> ends(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(
                new ViewPropertyAnimatorListenerOnSubscribe(ViewCompat.animate(v), AnimationEvent.END));
    }

    @CheckResult
    @NonNull
    public static Observable<View> ends(ViewPropertyAnimatorCompat animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(
                new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.END));
    }

    @CheckResult
    @NonNull
    public static Observable<View> cancels(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(
                new ViewPropertyAnimatorListenerOnSubscribe(ViewCompat.animate(v), AnimationEvent.CANCEL));
    }

    @CheckResult
    @NonNull
    public static Observable<View> cancels(ViewPropertyAnimatorCompat animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(
                new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.CANCEL));
    }

    @CheckResult
    @NonNull
    public static Observable<View> updates(View v) {
        checkNotNull(v, "v == null");
        return Observable.create(
                new ViewPropertyAnimatorUpdateListenerOnSubscribe(ViewCompat.animate(v)));
    }

    @CheckResult
    @NonNull
    public static Observable<View> updates(ViewPropertyAnimatorCompat animator) {
        checkNotNull(animator, "animator == null");
        return Observable.create(
                new ViewPropertyAnimatorUpdateListenerOnSubscribe(animator));
    }

    private RxViewPropertyAnimatorCompat() {
        throw new AssertionError("No instances.");
    }

}
