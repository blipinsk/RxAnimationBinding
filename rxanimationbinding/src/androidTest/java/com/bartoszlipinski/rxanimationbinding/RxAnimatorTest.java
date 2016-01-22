package com.bartoszlipinski.rxanimationbinding;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.FrameLayout;

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
    private FrameLayout parent;

    private ValueAnimator animator;

    @Before
    public void setUp() {
        SampleViewTestActivity activity = activityRule.getActivity();
        parent = activity.parent;
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

//    @Test
//    public void testAnimationEnds() {
//        RecordingObserver<Animation> o = new RecordingObserver<>();
//        animation.setRepeatCount(0);
//        Subscription subscription = RxAnimation.ends(animation).subscribe(o);
//
//        o.assertNoMoreEvents(); // No initial value.
//
//        startAnimation(); //first animation start
//
//        o.assertNoMoreEvents();               //no emissions in the first second of animation
//        assertThat(o.takeNext()).isNotNull(); //emission in the second second of animation
//
//        startAnimation(); //second animation start
//        assertThat(o.takeNext(ANIMATION_DURATION_MILLIS + 500)).isNotNull(); //emission in the two seconds of animation
//
//        subscription.unsubscribe();
//
//        startAnimation(); //third animation start
//        o.assertNoMoreEvents(ANIMATION_DURATION_MILLIS + 500); //no more emissions after unsubscribe
//    }
//
//    @Test
//    public void testAnimationRepeats() {
//        RecordingObserver<Animation> o = new RecordingObserver<>();
//        animation.setRepeatCount(2);
//        Subscription subscription = RxAnimation.repeats(animation).subscribe(o);
//
//        o.assertNoMoreEvents(); // No initial value.
//
//        startAnimation(); //first animation start
//
//        // <-0.5sec->
//        //              +--------+--------+--------+--------+--------+--------+--------+--------+--------+
//        //              |        ANIMATION         |     ANIMATION (rep 1.)   |    ANIMATION (rep 2.)    |
//        //              +--------+--------+--------+--------+--------+--------+--------+--------+--------+
//        // emissions ->                            #                          #
//        //              {------------ 1st window -----------}
//        //                                                  {------------- 2nd window ----------}
//
//
//        assertThat(o.takeNext((int) (ANIMATION_DURATION_MILLIS * 4f / 3f))).isNotNull();  //emission in the first window
//        assertThat(o.takeNext((int) (ANIMATION_DURATION_MILLIS * 4f / 3f))).isNotNull();  //emission in the second window
//
//        subscription.unsubscribe();
//
//        o.assertNoMoreEvents();
//        o.assertNoMoreEvents();
//
//        animation.cancel();
//    }

    @Test
    public void testAnimatorStarts() throws Exception {
        RecordingObserver<Animator> o = new RecordingObserver<>();
        animator.setRepeatCount(0);
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
        animator.setRepeatCount(0);
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

        o.assertNoMoreEvents();
        o.assertNoMoreEvents();

        cancelAnimation();
    }

    @Test
    public void testAnimatorPauses() throws Exception {

    }

    @Test
    public void testAnimatorResumes() throws Exception {

    }
}
