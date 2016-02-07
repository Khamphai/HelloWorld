package com.noobswe.kphai.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by k- on 1/7/2016.
 */
public class CustomView extends View {

    private boolean isBlue = false;
    private boolean isDown = false;
    private GestureDetector gestureDetector;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs, 0, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    //    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(21)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                //Decide: Care or not care ?

                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // Do whatever you want
                getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                //Action Up
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2,
                                   float velocityX, float velocityY) {
                isBlue = !isBlue;
                invalidate();
                return true;
            }
        });

        //Enable Click Mode
        //setOnClickListener available
        //selector enabled
        setClickable(true);
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomView,
                defStyleAttr, defStyleRes);

        try {
            isBlue = a.getBoolean(R.styleable.CustomView_isBlue, false);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        if (isBlue)
            p.setColor(0xFF0000FF);
        else
            p.setColor(0x00FFFF0000);//AARRGGBB
        canvas.drawLine(0, 0, getMeasuredWidth(), getMeasuredHeight(), p);

        if (isDown) {
            p.setColor(0xFF00FF00);
            //convert dp to px
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    2,
                    getContext().getResources().getDisplayMetrics()
            );

            p.setStrokeWidth(px);

            //Draw Line
            canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(),0, p);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Press event to GestureDetector
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                isDown = false;
                return true;
            case MotionEvent.ACTION_CANCEL:
                invalidate();
                isDown = false;
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        //CustomViewSavedState savedState = new CustomViewSavedState(superState);
        BundleSavedState savedState = new BundleSavedState(superState);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBlue", isBlue);
        savedState.setBundle(bundle);
        //savedState.setBlue(isBlue);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        //CustomViewSavedState savedState = (CustomViewSavedState) state;
        BundleSavedState savedState = (BundleSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        Bundle bundle = savedState.getBundle();
        isBlue = bundle.getBoolean("isBlue");
        //isBlue = savedState.isBlue();

    }



}// End of Main Class----------------------------------------------------------------
