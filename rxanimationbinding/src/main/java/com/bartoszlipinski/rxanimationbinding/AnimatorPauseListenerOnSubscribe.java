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
import android.annotation.TargetApi;
import android.os.Build;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

@TargetApi(Build.VERSION_CODES.KITKAT)
final class AnimatorPauseListenerOnSubscribe implements Observable.OnSubscribe<Void> {

    private final Animator mAnimator;
    private final int mEventToCallOn;

    AnimatorPauseListenerOnSubscribe(Animator animator, int eventToCallOn) {
        mAnimator = animator;
        mEventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {

        final Animator.AnimatorPauseListener listener = new Animator.AnimatorPauseListener() {
            @Override
            public void onAnimationPause(Animator animation) {
                if (mEventToCallOn == AnimationEvent.PAUSE && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onAnimationResume(Animator animation) {
                if (mEventToCallOn == AnimationEvent.RESUME && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };

        mAnimator.addPauseListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                mAnimator.removePauseListener(listener);
            }
        });
    }
}
