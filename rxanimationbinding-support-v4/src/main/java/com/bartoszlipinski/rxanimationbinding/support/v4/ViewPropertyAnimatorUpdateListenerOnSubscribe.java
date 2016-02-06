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
import android.support.v4.view.ViewPropertyAnimatorUpdateListener;
import android.view.View;

import com.bartoszlipinski.rxanimationbinding.OnUnsubscribedCallback;

import rx.Observable;
import rx.Subscriber;

final class ViewPropertyAnimatorUpdateListenerOnSubscribe implements Observable.OnSubscribe<View> {

    private final ViewPropertyAnimatorCompat animator;

    public ViewPropertyAnimatorUpdateListenerOnSubscribe(ViewPropertyAnimatorCompat animator) {
        this.animator = animator;
    }

    public void call(final Subscriber<? super View> subscriber) {
        final ViewPropertyAnimatorUpdateListenerWrapper updateListener =
                new ViewPropertyAnimatorUpdateListenerWrapper(this) {
                    @Override
                    void onUpdate(View view) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(view);
                        }
                    }
                };

        animator.setUpdateListener(updateListener);

        subscriber.add(new OnUnsubscribedCallback() {
            @Override
            protected void onUnsubscribe() {
                updateListener.onUnsubscribe();
            }
        });
    }

    private static abstract class ViewPropertyAnimatorUpdateListenerWrapper implements ViewPropertyAnimatorUpdateListener {
        ViewPropertyAnimatorUpdateListenerOnSubscribe onSubscribe;

        ViewPropertyAnimatorUpdateListenerWrapper(ViewPropertyAnimatorUpdateListenerOnSubscribe onSubscribe) {
            this.onSubscribe = onSubscribe;
        }

        void onUnsubscribe() {
            onSubscribe = null;
        }

        abstract void onUpdate(View view);

        @Override
        public void onAnimationUpdate(View view) {
            if (onSubscribe != null) {
                onUpdate(view);
            }
        }
    }
}
