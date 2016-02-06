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

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

import com.bartoszlipinski.rxanimationbinding.AnimationEvent;
import com.bartoszlipinski.rxanimationbinding.OnUnsubscribedCallback;

import rx.Observable;
import rx.Subscriber;

final class ViewPropertyAnimatorListenerOnSubscribe implements Observable.OnSubscribe<View> {

    private final ViewPropertyAnimatorCompat animator;
    private final int eventToCallOn;

    public ViewPropertyAnimatorListenerOnSubscribe(ViewPropertyAnimatorCompat animator, int eventToCallOn) {
        this.animator = animator;
        this.eventToCallOn = eventToCallOn;
    }

    public void call(final Subscriber<? super View> subscriber) {
        final ViewPropertyAnimatorListenerWrapper listener = new ViewPropertyAnimatorListenerWrapper(this) {
            @Override
            void onStart(View view) {
                if (eventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(view);
                }
            }

            @Override
            void onEnd(View view) {
                if (eventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(view);
                }
            }

            @Override
            void onCancel(View view) {
                if (eventToCallOn == AnimationEvent.CANCEL && !subscriber.isUnsubscribed()) {
                    subscriber.onNext(view);
                }
            }
        };

        animator.setListener(listener);

        subscriber.add(new OnUnsubscribedCallback() {
            @Override
            protected void onUnsubscribe() {
                listener.onUnsubscribe();
            }
        });
    }

    private static abstract class ViewPropertyAnimatorListenerWrapper implements ViewPropertyAnimatorListener {
        ViewPropertyAnimatorListenerOnSubscribe onSubscribe;

        ViewPropertyAnimatorListenerWrapper(ViewPropertyAnimatorListenerOnSubscribe onSubscribe) {
            this.onSubscribe = onSubscribe;
        }

        void onUnsubscribe() {
            onSubscribe = null;
        }

        abstract void onStart(View view);

        abstract void onEnd(View view);

        abstract void onCancel(View view);

        @Override
        public void onAnimationStart(View view) {
            if (onSubscribe != null) {
                onStart(view);
            }
        }

        @Override
        public void onAnimationEnd(View view) {
            if (onSubscribe != null) {
                onEnd(view);
            }
        }

        @Override
        public void onAnimationCancel(View view) {
            if (onSubscribe != null) {
                onCancel(view);
            }
        }
    }
}
