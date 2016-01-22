package com.bartoszlipinski.rxanimationbinding;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Subscription;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class RxAnimationTest {
    public static final int ANIMATION_DURATION_MILLIS = 1500;
    @Rule
    public final ActivityTestRule<SampleViewTestActivity> activityRule =
            new ActivityTestRule<>(SampleViewTestActivity.class);


    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private FrameLayout parent;

    private Animation animation;

    @Before
    public void setUp() {
        SampleViewTestActivity activity = activityRule.getActivity();
        parent = activity.parent;
        animation = new Animation() {};
        animation.setDuration(ANIMATION_DURATION_MILLIS);
    }

    private void startAnimation() {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                parent.startAnimation(animation);
            }
        });
    }

    @Test
    public void starts() throws InterruptedException {
        RecordingObserver<Void> o = new RecordingObserver<>();
        animation.setRepeatCount(0);
        Subscription subscription = RxAnimation.starts(animation).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        startAnimation(); //first animation start

        assertThat(o.takeNext()).isNull();  //emission in the first second of animation
        o.assertNoMoreEvents();             //no more emissions during this animation

        startAnimation(); //second animation start

        assertThat(o.takeNext()).isNull(); //emission in the first second of animation

        subscription.unsubscribe();

        startAnimation(); //third animation start
        o.assertNoMoreEvents();             //no more emissions after unsubscribe
    }


    @Test
    public void ends() {
        RecordingObserver<Void> o = new RecordingObserver<>();
        animation.setRepeatCount(0);
        Subscription subscription = RxAnimation.ends(animation).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        startAnimation(); //first animation start

        o.assertNoMoreEvents();             //no emissions in the first second of animation
        assertThat(o.takeNext()).isNull();  //emission in the second second of animation

        startAnimation(); //second animation start
        assertThat(o.takeNext(ANIMATION_DURATION_MILLIS + 500)).isNull(); //emission in the two seconds of animation

        subscription.unsubscribe();

        startAnimation(); //third animation start
        o.assertNoMoreEvents(ANIMATION_DURATION_MILLIS + 500); //no more emissions after unsubscribe
    }

    @Test
    public void repeats() {
        RecordingObserver<Void> o = new RecordingObserver<>();
        animation.setRepeatCount(2);
        Subscription subscription = RxAnimation.repeats(animation).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        startAnimation(); //first animation start

        // <-0.5sec->
        //              +--------+--------+--------+--------+--------+--------+--------+--------+--------+
        //              |        ANIMATION         |     ANIMATION (rep 1.)   |    ANIMATION (rep 2.)    |
        //              +--------+--------+--------+--------+--------+--------+--------+--------+--------+
        // emissions ->                            #                          #
        //              {------------ 1st window -----------}
        //                                                  {------------- 2nd window ----------}


        assertThat(o.takeNext((int) (ANIMATION_DURATION_MILLIS * 4f/3f))).isNull();  //emission in the first window
        assertThat(o.takeNext((int) (ANIMATION_DURATION_MILLIS * 4f/3f))).isNull();  //emission in the second window

        subscription.unsubscribe();

        o.assertNoMoreEvents();
        o.assertNoMoreEvents();

        animation.cancel();
    }
}
