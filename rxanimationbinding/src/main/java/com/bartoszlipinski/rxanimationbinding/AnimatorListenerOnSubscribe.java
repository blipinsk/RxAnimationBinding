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

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

final class AnimatorListenerOnSubscribe implements Observable.OnSubscribe<Void> {

    private final Animator animator;
    private final int eventToCallOn;

    AnimatorListenerOnSubscribe(Animator animator, int eventToCallOn) {
        this.animator = animator;
        this.eventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        final Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (eventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (eventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                if (eventToCallOn == AnimationEvent.CANCEL && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                if (eventToCallOn == AnimationEvent.REPEAT && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };

        animator.addListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                animator.removeListener(listener);
            }
        });
    }
}
