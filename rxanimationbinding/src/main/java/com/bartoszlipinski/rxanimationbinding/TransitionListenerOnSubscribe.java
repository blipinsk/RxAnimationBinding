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
import android.os.Build;
import android.transition.Transition;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

@TargetApi(Build.VERSION_CODES.KITKAT)
final class TransitionListenerOnSubscribe implements Observable.OnSubscribe<Void> {

    private final Transition mTransition;
    private final int mEventToCallOn;

    TransitionListenerOnSubscribe(Transition transition, int eventToCallOn) {
        mTransition = transition;
        mEventToCallOn = eventToCallOn;
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        
        final Transition.TransitionListener listener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                if (mEventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                if (mEventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                if (mEventToCallOn == AnimationEvent.CANCEL && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onTransitionPause(Transition transition) {
                if (mEventToCallOn == AnimationEvent.PAUSE && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }

            @Override
            public void onTransitionResume(Transition transition) {
                if (mEventToCallOn == AnimationEvent.RESUME && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(null);
                }
            }
        };

        mTransition.addListener(listener);

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                mTransition.removeListener(listener);
            }
        });
    }
}
