package com.bartoszlipinski.rxanimationbinding;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Subscription;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class RxAnimationTest {
    @Rule
    public final ActivityTestRule<SampleViewTestActivity> activityRule =
            new ActivityTestRule<>(SampleViewTestActivity.class);


    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private FrameLayout parent;
    private View child;

    private Animation animation;

    @Before
    public void setUp() {
        SampleViewTestActivity activity = activityRule.getActivity();
        parent = activity.parent;
        child = activity.child;
        animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                Log.d("TESTS", "animation step: " + interpolatedTime);
            }
        };
        animation.setDuration(100);
    }

    @Test
    public void starts() {
        RecordingObserver<Void> o = new RecordingObserver<>();
        Subscription subscription = RxAnimation.starts(animation).subscribe(o);
        o.assertNoMoreEvents(); // No initial value.

        startAnimation();

        assertThat(o.takeNext()).isNull();
        o.assertNoMoreEvents();

        startAnimation();
        assertThat(o.takeNext()).isNull();

        subscription.unsubscribe();

        startAnimation();
        o.assertNoMoreEvents();
    }

    private void startAnimation() {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                parent.startAnimation(animation);
            }
        });
    }
}
