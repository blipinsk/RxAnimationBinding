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

import android.view.animation.Animation;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

final class AnimationListenerOnSubscribe implements Observable.OnSubscribe<Void> {

    private final Animation mAnimation;
    private final int mEventToCallOn;

    AnimationListenerOnSubscribe(Animation animation, int eventToCallOn) {
        mAnimation = animation;
        mEventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {

        final AnimationListenerWrapper listener = new AnimationListenerWrapper(this) {
            @Override
            void onStart(Animation animation) {
                if (mEventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            void onEnd(Animation animation) {
                if (mEventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            void onRepeat(Animation animation) {
                if (mEventToCallOn == AnimationEvent.REPEAT && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };

        mAnimation.setAnimationListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                listener.onUnsubscribe();
            }
        });
    }

    private static abstract class AnimationListenerWrapper implements Animation.AnimationListener {
        AnimationListenerOnSubscribe mOnSubscribe;

        AnimationListenerWrapper(AnimationListenerOnSubscribe onSubscribe) {
            this.mOnSubscribe = onSubscribe;
        }

        abstract void onStart(Animation animation);

        abstract void onEnd(Animation animation);

        abstract void onRepeat(Animation animation);

        void onUnsubscribe() {
            mOnSubscribe = null;
        }

        @Override
        public final void onAnimationStart(Animation animation) {
            if (mOnSubscribe != null) {
                onStart(animation);
            }
        }

        @Override
        public final void onAnimationEnd(Animation animation) {
            if (mOnSubscribe != null) {
                onEnd(animation);
            }
        }

        @Override
        public final void onAnimationRepeat(Animation animation) {
            if (mOnSubscribe != null) {
                onRepeat(animation);
            }
        }
    }
}
