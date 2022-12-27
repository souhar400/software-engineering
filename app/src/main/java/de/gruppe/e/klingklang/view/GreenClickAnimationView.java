package de.gruppe.e.klingklang.view;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.core.content.ContextCompat;

import de.gruppe.e.klingklang.R;

public class GreenClickAnimationView  extends View{
    private static final int ANIMATION_DURATION = 100;
    private static final long ANIMATION_DELAY = 500;
    private static final int COLOR_ADJUSTER = 5;

    private float mX;
    private float mY;

    private float mRadius;
    private final Paint mPaint = new Paint();
    private final Paint cPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private AnimatorSet mPulseAnimatorSet = new AnimatorSet();


    public GreenClickAnimationView(Context context) {
        this(context, null);
    }

    public GreenClickAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setShadowLayer(10.0f, 0.0f, 2.0f, 0xFF000000);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        this.cPaint.setStyle(Paint.Style.STROKE);
        cPaint.setStrokeWidth(2f);
        this.cPaint.setColor(Color.GREEN);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        ObjectAnimator growAnimator = ObjectAnimator.ofFloat(this,
                "radius", 80, 40);
        growAnimator.setDuration(ANIMATION_DURATION);
        growAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator shrinkAnimator = ObjectAnimator.ofFloat(this,
                "radius", 40, 0);
        mPulseAnimatorSet.play(growAnimator).before(shrinkAnimator);
//        mPulseAnimatorSet.play(repeatAnimator).after(shrinkAnimator);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Where the center of the circle will be.
        mX = event.getX();
        mY = event.getY();

        // If there is an animation running, cancel it.
        // This resets the AnimatorSet and its animations to the starting values.
        if(mPulseAnimatorSet != null && mPulseAnimatorSet.isRunning()) {
            mPulseAnimatorSet.cancel();
        }
        // Start the animation sequence.
        mPulseAnimatorSet.start();

        return super.onTouchEvent(event);
    }

    public void setRadius(float radius) {
        mRadius = radius;
        float percent = (radius -40)/ 40;
        mPaint.setColor((int) new ArgbEvaluator().evaluate(percent, Color.WHITE, Color.GREEN));
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mPaint.getColor()== Color.RED){
            canvas.drawCircle(mX,mY, (4/5)* mRadius, cPaint);
        }
        canvas.drawCircle(mX, mY, mRadius, mPaint);
    }
}
