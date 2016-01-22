/**
 * Copyright 2015 Jake Wharton
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

import android.util.Log;

import java.util.NoSuchElementException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import rx.Observer;

import static com.google.common.truth.Truth.assertThat;

/**
 * This class was introduced as a part of Jake Wharton's RxBinding library.
 * All credits for it should go to him and everyone else who contributed to it.
 */
public final class RecordingObserver<T> implements Observer<T> {
    private static final int DEFAULT_BLOCK_DURATION_MILLIS = 1000;
    private static final String TAG = "RecordingObserver";

    private final BlockingDeque<Object> events = new LinkedBlockingDeque<>();

    @Override
    public void onCompleted() {
        Log.v(TAG, "onCompleted");
        events.addLast(new OnCompleted());
    }

    @Override
    public void onError(Throwable e) {
        Log.v(TAG, "onError", e);
        events.addLast(new OnError(e));
    }

    @Override
    public void onNext(T t) {
        Log.v(TAG, "onNext " + t);
        events.addLast(new OnNext(t));
    }

    private <E> E takeEvent(Class<E> wanted, int durationMillis) {
        Object event;
        try {
            event = events.pollFirst(durationMillis, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (event == null) {
            throw new NoSuchElementException(
                    "No event found while waiting for " + wanted.getSimpleName());
        }
        assertThat(event).isInstanceOf(wanted);
        return wanted.cast(event);
    }

    public T takeNext() {
        return takeNext(DEFAULT_BLOCK_DURATION_MILLIS);
    }

    public T takeNext(int blockDurationMillis) {
        OnNext event = takeEvent(OnNext.class, blockDurationMillis);
        return event.value;
    }

    public Throwable takeError() {
        return takeEvent(OnError.class, DEFAULT_BLOCK_DURATION_MILLIS).throwable;
    }

    public void assertOnCompleted() {
        takeEvent(OnCompleted.class, DEFAULT_BLOCK_DURATION_MILLIS);
    }

    public void assertNoMoreEvents() {
        assertNoMoreEvents(DEFAULT_BLOCK_DURATION_MILLIS);
    }

    public void assertNoMoreEvents(int blockDurationMillis) {
        try {
            Object event = takeEvent(Object.class, blockDurationMillis);
            throw new IllegalStateException("Expected no more events but got " + event);
        } catch (NoSuchElementException ignored) {
        }
    }

    private final class OnNext {
        final T value;

        private OnNext(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "OnNext[" + value + "]";
        }
    }

    private final class OnCompleted {
        @Override
        public String toString() {
            return "OnCompleted";
        }
    }

    private final class OnError {
        private final Throwable throwable;

        private OnError(Throwable throwable) {
            this.throwable = throwable;
        }

        @Override
        public String toString() {
            return "OnError[" + throwable + "]";
        }
    }
}
