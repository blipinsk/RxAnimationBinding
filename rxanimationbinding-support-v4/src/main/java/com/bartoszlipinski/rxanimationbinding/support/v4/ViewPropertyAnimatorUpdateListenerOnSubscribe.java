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

@TargetApi(Build.VERSION_CODES.KITKAT)
final class ViewPropertyAnimatorUpdateListenerOnSubscribe implements Observable.OnSubscribe<View> {

//    private final ViewPropertyAnimator animator;
//
//    public ViewPropertyAnimatorUpdateListenerOnSubscribe(ViewPropertyAnimator animator) {
//        this.animator = animator;
//    }

    public void call(final Subscriber<? super View> subscriber) {
//        final ViewPropertyAnimatorUpdateListenerWrapper updateListener =
//                new ViewPropertyAnimatorUpdateListenerWrapper(this) {
//                    @Override
//                    void onUpdate(ValueAnimator animator) {
//                        if (!subscriber.isUnsubscribed()) {
//                            subscriber.onNext(animator);
//                        }
//                    }
//                };
//
//        animator.setUpdateListener(updateListener);
//
//        subscriber.add(new OnUnsubscribedCallback() {
//            @Override
//            protected void onUnsubscribe() {
//                updateListener.onUnsubscribe();
//            }
//        });
    }

//    private static abstract class ViewPropertyAnimatorUpdateListenerWrapper implements ValueAnimator.AnimatorUpdateListener {
//        ViewPropertyAnimatorUpdateListenerOnSubscribe onSubscribe;
//
//        ViewPropertyAnimatorUpdateListenerWrapper(ViewPropertyAnimatorUpdateListenerOnSubscribe onSubscribe) {
//            this.onSubscribe = onSubscribe;
//        }
//
//        void onUnsubscribe() {
//            onSubscribe = null;
//        }
//
//        abstract void onUpdate(ValueAnimator animator);
//
//        @Override
//        public void onAnimationUpdate(ValueAnimator animator) {
//            if (onSubscribe != null) {
//                onUpdate(animator);
//            }
//        }
//    }
}
