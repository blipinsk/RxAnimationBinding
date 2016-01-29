package com.bartoszlipinski.rxanimationbinding.sample.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.widget.Button;

import com.bartoszlipinski.rxanimationbinding.RxValueAnimator;
import com.bartoszlipinski.rxanimationbinding.sample.R;
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.jakewharton.rxbinding.view.RxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by Bartosz Lipinski
 */
public class MainActivity extends Activity {

    private static final long BLINK_DURATION = 50;

    @Bind(R.id.button_start_cancel)
    Button buttonStartCancel;
    @Bind(R.id.button_pause_resume)
    Button buttonPauseResume;
    @Bind(R.id.animated)
    View animatedView;
    @Bind({R.id.emission_start,
            R.id.emission_end,
            R.id.emission_cancel,
            R.id.emission_repeat,
            R.id.emission_pause,
            R.id.emission_resume,
            R.id.emission_update})
    View[] emissionViews;
    Handler startHandler = new Handler();
    Handler endHandler = new Handler();
    Handler cancelHandler = new Handler();
    Handler repeatHandler = new Handler();
    Handler pauseHandler = new Handler();
    Handler resumeHandler = new Handler();
    Handler updateHandler = new Handler();
    ValueAnimator animator;
    List<Subscription> subscriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupAnimation();
        setupViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanup();
        ButterKnife.unbind(this);
    }

    private void cleanup() {
        for (Subscription subscription : subscriptions) {
            subscription.unsubscribe();
        }
        startHandler.removeCallbacksAndMessages(null);
        endHandler.removeCallbacksAndMessages(null);
        cancelHandler.removeCallbacksAndMessages(null);
        repeatHandler.removeCallbacksAndMessages(null);
        pauseHandler.removeCallbacksAndMessages(null);
        resumeHandler.removeCallbacksAndMessages(null);
        updateHandler.removeCallbacksAndMessages(null);
    }

    private void setupAnimation() {
        final int finalSize = getResources().getDimensionPixelSize(R.dimen.size_large);
        animator = ViewPropertyObjectAnimator.animate(animatedView)
                .width(finalSize)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setDuration(3000)
                .get();

        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(2);

        subscriptions.add(RxValueAnimator.starts(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(0);
                        buttonStartCancel.setText(R.string.cancel);
                        buttonPauseResume.setText(R.string.pause);
                        buttonPauseResume.setEnabled(true);
                    }
                }));

        subscriptions.add(RxValueAnimator.ends(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(1);
                        buttonStartCancel.setText(R.string.start);
                        buttonStartCancel.setSelected(false);
                        buttonPauseResume.setText(R.string.pause);
                        buttonPauseResume.setEnabled(false);
                        buttonPauseResume.setSelected(false);
                    }
                }));

        subscriptions.add(RxValueAnimator.cancels(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(2);
                    }
                }));

        subscriptions.add(RxValueAnimator.repeats(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(3);
                    }
                }));

        subscriptions.add(RxValueAnimator.pauses(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(4);
                        buttonPauseResume.setText(R.string.resume);
                    }
                }));

        subscriptions.add(RxValueAnimator.resumes(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(5);
                        buttonPauseResume.setText(R.string.pause);
                    }
                }));

        subscriptions.add(RxValueAnimator.updates(animator)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Animator>() {
                    @Override
                    public void call(Animator animator) {
                        blinkEmissionView(6);
                    }
                }));
    }

    private void setupViews() {
        subscriptions.add(RxView.clicks(buttonStartCancel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        boolean isSelectedAfterClick = !buttonStartCancel.isSelected();
                        buttonStartCancel.setSelected(isSelectedAfterClick);
                        if (isSelectedAfterClick) {
                            animator.start();
                        } else {
                            animator.cancel();
                        }
                    }
                }));

        buttonPauseResume.setEnabled(false);
        subscriptions.add(RxView.clicks(buttonPauseResume)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        boolean isSelectedAfterClick = !buttonPauseResume.isSelected();
                        buttonPauseResume.setSelected(isSelectedAfterClick);
                        if (isSelectedAfterClick) {
                            animator.pause();
                        } else {
                            animator.resume();
                        }
                    }
                }));
    }

    public void blinkEmissionView(final int position) {
        Handler handler;
        switch (position) {
            case 0:
                handler = startHandler;
                break;
            case 1:
                handler = endHandler;
                break;
            case 2:
                handler = cancelHandler;
                break;
            case 3:
                handler = repeatHandler;
                break;
            case 4:
                handler = pauseHandler;
                break;
            case 5:
                handler = resumeHandler;
                break;
            default:
                handler = updateHandler;
                break;
        }
        if (emissionViews[position].isSelected()) {
            handler.removeCallbacksAndMessages(null);
            emissionViews[position].setSelected(false);
        } else {
            emissionViews[position].setSelected(true);
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (emissionViews != null && emissionViews[position] != null) {
                        emissionViews[position].setSelected(false);
                    }
                }
            }, BLINK_DURATION);
        }
    }
}
