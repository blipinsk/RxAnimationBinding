package com.bartoszlipinski.rxanimationbinding;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Bartosz Lipinski
 * 21.01.2016
 */
public final class SampleViewTestActivity extends Activity {
    FrameLayout parent;
    View child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = new FrameLayout(this);
        child = new View(this);
        setContentView(parent);
    }
}