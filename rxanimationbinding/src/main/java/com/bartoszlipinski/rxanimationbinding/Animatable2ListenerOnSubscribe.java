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
import rx.android.MainThreadSubscription;

@TargetApi(Build.VERSION_CODES.M)
final class Animatable2ListenerOnSubscribe implements Observable.OnSubscribe<Void> {

    private final Animatable2 mAnimatable2;
    private final int mEventToCallOn;

    Animatable2ListenerOnSubscribe(Animatable2 animatable2, int eventToCallOn) {
        mAnimatable2 = animatable2;
        mEventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {

        final Animatable2.AnimationCallback callback = new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationStart(Drawable drawable) {
                if (mEventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onAnimationEnd(Drawable drawable) {
                if (mEventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };

        mAnimatable2.registerAnimationCallback(callback);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                mAnimatable2.unregisterAnimationCallback(callback);
            }
        });
    }
}
