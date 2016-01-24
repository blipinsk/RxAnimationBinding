package com.bartoszlipinski.rxanimationbinding;

import rx.Subscription;

/**
 * Created by Bartosz Lipinski
 * 23.01.2016
 */

abstract class OnUnsubscribedCallback implements Subscription {
    private boolean mUnsubscribed;

    @Override
    public final void unsubscribe() {
        mUnsubscribed = true;
        onUnsubscribe();
    }

    protected abstract void onUnsubscribe();

    @Override
    public final boolean isUnsubscribed() {
        return mUnsubscribed;
    }
}
