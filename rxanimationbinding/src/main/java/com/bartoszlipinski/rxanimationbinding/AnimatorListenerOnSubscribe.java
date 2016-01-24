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

final class AnimatorListenerOnSubscribe implements Observable.OnSubscribe<Animator> {

    private final Animator animator;
    private final int eventToCallOn;

    AnimatorListenerOnSubscribe(Animator animator, int eventToCallOn) {
        this.animator = animator;
        this.eventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Animator> subscriber) {
        final Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                if (eventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(animator);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (eventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(animator);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                if (eventToCallOn == AnimationEvent.CANCEL && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(animator);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                if (eventToCallOn == AnimationEvent.REPEAT && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(animator);
                }
            }
        };

        animator.addListener(listener);

        subscriber.add(new OnUnsubscribedCallback() {
            @Override
            protected void onUnsubscribe() {
                animator.removeListener(listener);
            }
        });
    }
}
