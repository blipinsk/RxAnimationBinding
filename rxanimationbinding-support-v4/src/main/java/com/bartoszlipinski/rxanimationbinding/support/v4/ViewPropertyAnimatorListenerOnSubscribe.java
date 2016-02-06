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

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

import rx.Observable;
import rx.Subscriber;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
final class ViewPropertyAnimatorListenerOnSubscribe implements Observable.OnSubscribe<View> {

//    private final ViewPropertyAnimatorCompat animator;
//    private final int eventToCallOn;
//
//    public ViewPropertyAnimatorListenerOnSubscribe(ViewPropertyAnimatorCompat animator, int eventToCallOn) {
//        this.animator = animator;
//        this.eventToCallOn = eventToCallOn;
//    }

    public void call(final Subscriber<? super View> subscriber) {
//        final ViewPropertyAnimatorListenerWrapper listener = new ViewPropertyAnimatorListenerWrapper(this) {
//            @Override
//            void onStart(ViewPropertyAnimatorCompat animator) {
//                if (eventToCallOn == AnimationEvent.START && !subscriber.isUnsubscribed()) {
//                    subscriber.onNext(animator);
//                }
//            }
//
//            @Override
//            void onEnd(Animator animator) {
//                if (eventToCallOn == AnimationEvent.END && !subscriber.isUnsubscribed()) {
//                    subscriber.onNext(animator);
//                }
//            }
//
//            @Override
//            void onCancel(Animator animator) {
//                if (eventToCallOn == AnimationEvent.CANCEL && !subscriber.isUnsubscribed()) {
//                    subscriber.onNext(animator);
//                }
//            }
//
//            @Override
//            void onRepeat(Animator animator) {
//                if (eventToCallOn == AnimationEvent.REPEAT && !subscriber.isUnsubscribed()) {
//                    subscriber.onNext(animator);
//                }
//            }
//        };
//        animator.setListener(listener);
//
//        subscriber.add(new OnUnsubscribedCallback() {
//            @Override
//            protected void onUnsubscribe() {
//                listener.onUnsubscribe();
//            }
//        });
    }
//
//    private static abstract class ViewPropertyAnimatorListenerWrapper implements Animator.AnimatorListener {
//        ViewPropertyAnimatorListenerOnSubscribe onSubscribe;
//
//        ViewPropertyAnimatorListenerWrapper(ViewPropertyAnimatorListenerOnSubscribe onSubscribe) {
//            this.onSubscribe = onSubscribe;
//        }
//
//        void onUnsubscribe() {
//            onSubscribe = null;
//        }
//
//        abstract void onStart(ViewPropertyAnimatorCompat animator);
//
//        abstract void onEnd(ViewPropertyAnimatorCompat animator);
//
//        abstract void onCancel(ViewPropertyAnimatorCompat animator);
//
//        @Override
//        public void onAnimationStart(ViewPropertyAnimatorCompat animator) {
//            if (onSubscribe != null) {
//                onStart(animator);
//            }
//        }
//
//        @Override
//        public void onAnimationEnd(ViewPropertyAnimatorCompat animator) {
//            if (onSubscribe != null) {
//                onEnd(animator);
//            }
//        }
//
//        @Override
//        public void onAnimationCancel(ViewPropertyAnimatorCompat animator) {
//            if (onSubscribe != null) {
//                onCancel(animator);
//            }
//        }
//    }
}
