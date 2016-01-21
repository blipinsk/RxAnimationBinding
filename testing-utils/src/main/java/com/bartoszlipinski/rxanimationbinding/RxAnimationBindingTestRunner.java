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

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.test.runner.AndroidJUnitRunner;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;

/**
 * This class is based on RxBindingTestRunner from Jake Wharton's RxBinding library.
 * All credits for it should go to him and everyone else who contributed to it.
 */
public final class RxAnimationBindingTestRunner extends AndroidJUnitRunner {
    private PowerManager.WakeLock wakeLock;

    @Override
    public void onStart() {
        Context app = getTargetContext().getApplicationContext();

        String name = RxAnimationBindingTestRunner.class.getSimpleName();
        // Unlock the device so that the tests can input keystrokes.
        KeyguardManager keyguard = (KeyguardManager) app.getSystemService(KEYGUARD_SERVICE);
        keyguard.newKeyguardLock(name).disableKeyguard();
        // Wake up the screen.
        PowerManager power = (PowerManager) app.getSystemService(POWER_SERVICE);
        wakeLock = power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, name);
        wakeLock.acquire();

        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wakeLock.release();
    }
}