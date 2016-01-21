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

final class AnimationEvent {

    static final int START = 0;
    static final int END = 1;
    static final int REPEAT = 2;
    static final int CANCEL = 3;
    static final int PAUSE = 4;
    static final int RESUME = 5;
    static final int UPDATE = 6;

    private AnimationEvent() {
        throw new AssertionError("No instances.");
    }
}
