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
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.ViewPropertyAnimator;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

final class ViewPropertyAnimatorListenerOnSubscribe implements Observable.OnSubscribe<Void> {

    private final ViewPropertyAnimator animator;
    private final int eventToCallOn;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    static ViewPropertyAnimatorListenerOnSubscribe forTypicalEvents(ViewPropertyAnimator animator, int eventToCallOn){
        if (!AnimationEvent.isTypical(eventToCallOn)){
            throw new IllegalArgumentException("forTypicalEvents method is only for typical events");
        }
        return new ViewPropertyAnimatorListenerOnSubscribe(animator, eventToCallOn);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    static ViewPropertyAnimatorListenerOnSubscribe forUpdates(ViewPropertyAnimator animator){
        return new ViewPropertyAnimatorListenerOnSubscribe(animator, AnimationEvent.UPDATE);
    }

    private ViewPropertyAnimatorListenerOnSubscribe(ViewPropertyAnimator animator, int eventToCallOn) {
        this.animator = animator;
        this.eventToCallOn = eventToCallOn;
    }

    @SuppressLint("NewApi")
    @Override
    public void call(final Subscriber<? super Void> subscriber) {

        ViewPropertyAnimatorListenerWrapper listenerWrapper = null;
        ViewPropertyAnimatorUpdateListenerWrapper updateListenerWrapper = null;

        switch (eventToCallOn) {
            case AnimationEvent.START:
            case AnimationEvent.END:
            case AnimationEvent.CANCEL:
            case AnimationEvent.REPEAT:
                listenerWrapper = new ViewPropertyAnimatorListenerWrapper(this) {
                    @Override
                    void onStart(Animator animation) {
                        if (eventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }

                    @Override
                    void onEnd(Animator animation) {
                        if (eventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }

                    @Override
                    void onCancel(Animator animation) {
                        if (eventToCallOn == AnimationEvent.CANCEL && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }

                    @Override
                    void onRepeat(Animator animation) {
                        if (eventToCallOn == AnimationEvent.REPEAT && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }
                };
                break;
            case AnimationEvent.UPDATE:
                updateListenerWrapper = new ViewPropertyAnimatorUpdateListenerWrapper(this) {
                    @Override
                    void onUpdate(ValueAnimator animation) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(null);
                        }
                    }
                };
                break;
        }

        final ViewPropertyAnimatorListenerWrapper listener = listenerWrapper;
        if (listener != null) {
            animator.setListener(listener);
        }

        final ViewPropertyAnimatorUpdateListenerWrapper updateListener = updateListenerWrapper;
        if (updateListener != null) {
            animator.setUpdateListener(updateListener);
        }

        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                if (listener != null) {
                    listener.onUnsubscribe();
                }
                if (updateListener != null) {
                    updateListener.onUnsubscribe();
                }
            }
        });
    }

    private static abstract class ViewPropertyAnimatorListenerWrapper implements Animator.AnimatorListener {
        ViewPropertyAnimatorListenerOnSubscribe onSubscribe;

        ViewPropertyAnimatorListenerWrapper(ViewPropertyAnimatorListenerOnSubscribe onSubscribe) {
            this.onSubscribe = onSubscribe;
        }

        void onUnsubscribe() {
            onSubscribe = null;
        }

        abstract void onStart(Animator animation);

        abstract void onEnd(Animator animation);

        abstract void onCancel(Animator animation);

        abstract void onRepeat(Animator animation);

        @Override
        public void onAnimationStart(Animator animation) {
            if (onSubscribe != null) {
                onStart(animation);
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (onSubscribe != null) {
                onEnd(animation);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            if (onSubscribe != null) {
                onCancel(animation);
            }
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            if (onSubscribe != null) {
                onRepeat(animation);
            }
        }
    }


    private static abstract class ViewPropertyAnimatorUpdateListenerWrapper implements ValueAnimator.AnimatorUpdateListener {
        ViewPropertyAnimatorListenerOnSubscribe onSubscribe;

        ViewPropertyAnimatorUpdateListenerWrapper(ViewPropertyAnimatorListenerOnSubscribe onSubscribe) {
            this.onSubscribe = onSubscribe;
        }

        void onUnsubscribe() {
            onSubscribe = null;
        }

        abstract void onUpdate(ValueAnimator animation);

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (onSubscribe != null) {
                onUpdate(animation);
            }
        }
    }
}
