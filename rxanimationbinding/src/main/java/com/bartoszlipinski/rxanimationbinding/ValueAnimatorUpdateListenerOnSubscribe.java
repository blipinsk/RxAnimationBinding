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

import android.animation.ValueAnimator;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

final class ValueAnimatorUpdateListenerOnSubscribe implements Observable.OnSubscribe<ValueAnimator> {

    private final ValueAnimator animator;

    ValueAnimatorUpdateListenerOnSubscribe(ValueAnimator animator) {
        this.animator = animator;
    }

    @Override
    public void call(final Subscriber<? super ValueAnimator> subscriber) {

        final ValueAnimator.AnimatorUpdateListener listener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(animator);
                }
            }
        };

        animator.addUpdateListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                animator.removeUpdateListener(listener);
            }
        });
    }
}
