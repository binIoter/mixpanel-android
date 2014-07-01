package com.mixpanel.android.abtesting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class TestView extends FrameLayout {
    public TestView(Context context) {
        super(context);

        mAllViews = new HashSet<View>();
        mAllViews.add(this);

        mSecondLayer = new HashSet<View>();
        mThirdLayer = new HashSet<View>();
        mFourthLayer = new HashSet<View>();

        setId(ROOT_ID);
        setTag(CRAZY_TAG);

        ViewGroup linear = new LinearLayout(getContext());
        linear.setId(LINEAR_ID);

        addView(linear);
        mAllViews.add(linear);

        mSecondLayer.add(linear);

        View text1 = new TextView(getContext());
        text1.setId(TEXT_VIEW_ID);
        text1.setTag(CRAZY_TAG);

        linear.addView(text1);
        mAllViews.add(text1);
        mThirdLayer.add(text1);

        View text2 = new TextView(getContext());
        text2.setTag(SIMPLE_TAG);

        linear.addView(text2);
        mAllViews.add(text2);
        mThirdLayer.add(text2);

        ViewGroup buttons = new LinearLayout(getContext());
        linear.addView(buttons);
        mAllViews.add(buttons);
        mThirdLayer.add(buttons);

        mAdHocButton1 = new AdHocButton1(getContext());
        mAdHocButton1.setTag(SIMPLE_TAG);
        buttons.addView(mAdHocButton1);
        mAllViews.add(mAdHocButton1);
        mFourthLayer.add(mAdHocButton1);

        mAdHocButton2 = new AdHocButton2(getContext());
        buttons.addView(mAdHocButton2);
        mAllViews.add(mAdHocButton2);
        mFourthLayer.add(mAdHocButton2);

        mAdHocButton3 = new AdHocButton3(getContext());
        mAdHocButton3.setId(BUTTON_ID);
        buttons.addView(mAdHocButton3);
        mAllViews.add(mAdHocButton3);
        mFourthLayer.add(mAdHocButton3);

        mButtonParentView = buttons;
    }

    public int viewCount() {
        return 1 + mSecondLayer.size() + mThirdLayer.size() + mFourthLayer.size();
    }

    public static class AdHocButton1 extends Button {
        public AdHocButton1(Context context) {
            super(context);
        }

        public String getCustomProperty() {
            return SIMPLE_TAG;
        }

        public void setCustomProperty(CharSequence s) {
            ; // OK
        }
    }

    public static class AdHocButton2 extends Button {
        public AdHocButton2(Context context) {
            super(context);
        }

        private String getCustomProperty() {
            return SIMPLE_TAG;
        }
    }

    public static class AdHocButton3 extends Button {
        public AdHocButton3(Context context) {
            super(context);
        }

        public int getCustomProperty() {
            return BUTTON_ID;
        }

        public void setCustomProperty(CharSequence s) {
            throw new RuntimeException("BANG!");
        }
    }

    public final Set<View> mAllViews;
    public final View mButtonParentView;
    public final View mAdHocButton1;
    public final View mAdHocButton2;
    public final View mAdHocButton3;
    public final Set<View> mSecondLayer;
    public final Set<View> mThirdLayer;
    public final Set<View> mFourthLayer;

    public static final int ROOT_ID = 1000;
    public static final int BUTTON_ID = 2000;
    public static final int TEXT_VIEW_ID = 3000;
    public static final int LINEAR_ID = 4000;
    public static final String SIMPLE_TAG = "this_is_a_simple_tag";
    public static final String CRAZY_TAG = "this is a long and \"CRAZY\" \\\"Tag";
    public static final String DOUBLE_QUOTED_CRAZY_TAG = "\"this is a long and \\\"CRAZY\\\" \\\\\\\"Tag\"";
    public static final String SINGLE_QUOTED_CRAZY_TAG = "'this is a long and \\\"CRAZY\\\" \\\\\\\"Tag'";
}
