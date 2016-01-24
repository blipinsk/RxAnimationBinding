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

import rx.Observable;
import rx.Subscriber;

@TargetApi(Build.VERSION_CODES.M)
final class Animatable2ListenerOnSubscribe implements Observable.OnSubscribe<Drawable> {

    private final Animatable2 animatable2;
    private final int eventToCallOn;

    Animatable2ListenerOnSubscribe(Animatable2 animatable2, int eventToCallOn) {
        this.animatable2 = animatable2;
        this.eventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Drawable> subscriber) {

        final Animatable2.AnimationCallback callback = new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationStart(Drawable drawable) {
                if (eventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(drawable);
                }
            }

            @Override
            public void onAnimationEnd(Drawable drawable) {
                if (eventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(drawable);
                }
            }
        };

        animatable2.registerAnimationCallback(callback);

        subscriber.add(new OnUnsubscribedCallback() {
            @Override
            protected void onUnsubscribe() {
                animatable2.unregisterAnimationCallback(callback);
            }
        });
    }
}
