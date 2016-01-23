package com.bartoszlipinski.rxanimationbinding;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Instrumentation;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Subscription;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public class RxAnimatorTest {
    public static final int ANIMATION_DURATION_MILLIS = 1500;
    @Rule
    public final ActivityTestRule<SampleViewTestActivity> activityRule =
            new ActivityTestRule<>(SampleViewTestActivity.class);


    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

    private ValueAnimator animator;

    @Before
    public void setUp() {
        animator = ValueAnimator.ofFloat(0.1f, 0.3f, 0.7f, 0.9f);
        animator.setDuration(ANIMATION_DURATION_MILLIS);
    }

    private void startAnimation() {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                animator.start();
            }
        });
    }

    private void cancelAnimation() {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                animator.cancel();
            }
        });
    }

    private void pauseAnimation() {
        instrumentation.runOnMainSync(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                animator.pause();
            }
        });
    }

    private void resumeAnimation() {
        instrumentation.runOnMainSync(new Runnable() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                animator.resume();
            }
        });
    }

    @Test
    public void testAnimatorStarts() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        Subscription subscription = RxAnimator.starts(animator).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //first animation start

        assertThat(o.takeNext()).isNotNull(); //emission in the first second of animation
        o.assertNoMoreEvents();               //no more emissions during this animation

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //second animation start
        assertThat(o.takeNext()).isNotNull(); //emission in the first second of animation

        subscription.unsubscribe();

        cancelAnimation();
        startAnimation(); //third animation start
        o.assertNoMoreEvents();             //no more emissions after unsubscribe
    }

    @Test
    public void testAnimatorEnds() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        Subscription subscription = RxAnimator.ends(animator).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //first animation start

        o.assertNoMoreEvents();               //no emissions in the first second of animation
        assertThat(o.takeNext()).isNotNull(); //emission in the second second of animation

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //second animation start
        assertThat(o.takeNext(ANIMATION_DURATION_MILLIS + 500)).isNotNull(); //emission in the two seconds of animation

        subscription.unsubscribe();

        cancelAnimation();
        startAnimation(); //third animation start
        o.assertNoMoreEvents(ANIMATION_DURATION_MILLIS + 500); //no more emissions after unsubscribe
    }

    @Test
    public void testAnimatorCancels() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        Subscription subscription = RxAnimator.cancels(animator).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel (there was no animation started)

        startAnimation(); //first animation start
        o.assertNoMoreEvents(); //no emissions after start

        cancelAnimation();
        assertThat(o.takeNext()).isNotNull(); //emission after cancel
        o.assertNoMoreEvents(); //no emissions after that

        subscription.unsubscribe();

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after that
    }

    @Test
    public void testAnimatorRepeats() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        animator.setRepeatCount(2);
        Subscription subscription = RxAnimator.repeats(animator).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //first animation start

        //              <-0.5sec->
        //              +--------+--------+--------+--------+--------+--------+--------+--------+--------+
        //              |        ANIMATION         |     ANIMATION (rep 1.)   |    ANIMATION (rep 2.)    |
        //              +--------+--------+--------+--------+--------+--------+--------+--------+--------+
        // emissions ->                            #                          #
        //              {------------ 1st window -----------}
        //                                                  {------------- 2nd window ----------}


        assertThat(o.takeNext((int) (ANIMATION_DURATION_MILLIS * 4f / 3f))).isNotNull();  //emission in the first window
        assertThat(o.takeNext((int) (ANIMATION_DURATION_MILLIS * 4f / 3f))).isNotNull();  //emission in the second window

        subscription.unsubscribe();

        o.assertNoMoreEvents((int) (ANIMATION_DURATION_MILLIS * 2f));

        cancelAnimation();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Test
    public void testAnimatorPauses() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        Subscription subscription = RxAnimator.pauses(animator).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //first animation start
        o.assertNoMoreEvents(ANIMATION_DURATION_MILLIS * 2); //no emissions during the whole animation

        cancelAnimation();
        startAnimation();       //second animation
        o.assertNoMoreEvents(500); //no emissions in the beginning of the animation

        pauseAnimation();
        assertThat(o.takeNext()).isNotNull(); //emission after pause
        o.assertNoMoreEvents(); //no emissions after that

        resumeAnimation();
        o.assertNoMoreEvents(); //no emissions after resume

        subscription.unsubscribe();

        startAnimation();
        pauseAnimation();
        o.assertNoMoreEvents();
    }

    @Test
    public void testAnimatorResumes() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        Subscription subscription = RxAnimator.resumes(animator).subscribe(o);

        o.assertNoMoreEvents(); // No initial value.

        cancelAnimation();
        o.assertNoMoreEvents(); //no emissions after cancel

        startAnimation(); //first animation start
        o.assertNoMoreEvents(ANIMATION_DURATION_MILLIS * 2); //no emissions during the whole animation

        cancelAnimation();
        startAnimation();       //second animation
        o.assertNoMoreEvents(500); //no emissions in the beginning of the animation

        pauseAnimation();
        o.assertNoMoreEvents(); //no emissions after pause

        resumeAnimation();
        assertThat(o.takeNext()).isNotNull(); //emission after res

        subscription.unsubscribe();

        startAnimation();
        pauseAnimation();
        resumeAnimation();
        o.assertNoMoreEvents();
    }
}
