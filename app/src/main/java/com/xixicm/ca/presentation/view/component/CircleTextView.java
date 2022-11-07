package com.xixicm.ca.presentation.view.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * Modified from https://github.com/aamirwahid5/CircleTextView
 */

public class CircleTextView extends AppCompatTextView {
    public static final int[] COLORS = {0xff44bb66,
            0xff55ccdd,
            0xffbb7733,
            0xffff6655,
            0xffffbb44,
            0xff44aaff};
    private float strokeWidth;
    Paint circlePaint;
    int strokeColor, solidColor;
    String Text;
    Float size = -1f;

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
        setWillNotDraw(false);

    }

    private void initViews(Context context, AttributeSet attrs) {

        //paint object for drawing in onDraw
        circlePaint = new Paint();

    }

    @Override
    public void draw(Canvas canvas) {
        final int diameter, radius, h, w;

        circlePaint.setColor(solidColor);
        circlePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        Paint strokePaint = new Paint();
        strokePaint.setColor(strokeColor);
        strokePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        //get Height and Width
        h = this.getHeight();
        w = this.getWidth();

        diameter = ((h > w) ? h : w);
        radius = diameter / 2;

        //setting Height and width
        this.setHeight(diameter);
        this.setWidth(diameter);
        this.setText(Text);

        if (size != -1f) {

            this.setTextSize(size);
        } else {

            this.setTextSize(diameter / 5);
        }

        canvas.drawCircle(diameter / 2, diameter / 2, radius - strokeWidth, circlePaint);
        super.draw(canvas);
    }

    public void setStrokeWidth(int dp) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        this.strokeWidth = dp * scale;

    }

    public void setStrokeColor(int color) {
        this.strokeColor = color;
    }

    public void setCustomText(String value) {

        this.Text = TextUtils.isEmpty(value) ? "" : String.valueOf(value.charAt(0));
    }

    public void setCustomTextSize(float value) {

        this.size = value;
    }

    public void setCustomText(String value, String colorSeed) {
        setCustomText(value);
        this.solidColor = getColorBySeed(colorSeed);
    }

    public int getColorBySeed(String seed) {
        if (TextUtils.isEmpty(seed)) {
            return COLORS[0];
        }
        return COLORS[Math.abs(seed.hashCode() % COLORS.length)];
    }
}
