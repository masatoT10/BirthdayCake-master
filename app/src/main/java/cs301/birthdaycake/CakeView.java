package cs301.birthdaycake;

import android.app.Notification;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class CakeView extends SurfaceView {

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint textPaint = new Paint();

    Paint balloonPaint = new Paint();
    Paint Black = new Paint();

    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 50.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;



    private CakeModel secondCake;

    //getter
    public CakeModel getCake() {
        return secondCake;
    }



    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //This is essential or your onDraw method won't get called4
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFFFA500);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(70);


        balloonPaint.setColor(Color.BLUE);

        balloonPaint.setStyle(Paint.Style.FILL);

        Black.setColor(Color.BLACK);


        setBackgroundColor(Color.WHITE);  //better than black default
        secondCake = new CakeModel();

    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);

        if (secondCake.candleLit == true) {
            //draw the outer flame
            float flameCenterX = left + candleWidth / 2;
            float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

            //draw the inner flame
            flameCenterY += outerFlameRadius / 3;
            canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);

        }

        //draw the wick
        float wickLeft = left + candleWidth / 2 - wickWidth / 2;
        float wickTop = bottom - wickHeight - candleHeight;
        canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);

    }

    public void drawSqaure(Canvas canvas, float x, float y) {
        Paint red = new Paint();
        red.setColor(Color.RED);
        Paint green = new Paint();
        green.setColor(Color.GREEN);

       int w = 100;

        canvas.drawRect(x - w/2, y - w/2, x, y,  red);
        canvas.drawRect(x, y, x + w/2, y + w/2, red);
        canvas.drawRect(x, y - w/2, x+ w/2, y , green);
        canvas.drawRect(x - w/2, y, x, y + w/2, green);
    }




    /**
     * onDraw is like "paint" in a regular Java program.  While a Canvas is
     * conceptually similar to a Graphics in javax.swing, the implementation has
     * many subtle differences.  Show care and read the documentation.
     * <p>
     * This method will draw a birthday cake
     */
    @Override
    public void onDraw(Canvas canvas) {
        //top and bottom are used to keep a running tally as we progress down the cake layers
        float top = cakeTop;
        float bottom = cakeTop + frostHeight;

        //Frosting on top
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
        top += layerHeight;
        bottom += frostHeight;

        //Then a second frosting layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
        top += frostHeight;
        bottom += layerHeight;

        //Then a second cake layer
        canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

        if (secondCake.hasCandle == true) {
            //Now a candle in the center
            for (int i = 1; i < secondCake.numCandle + 1; i++) {
                drawCandle(canvas, cakeLeft + i * (cakeWidth / (secondCake.numCandle + 1)) - candleWidth / 2, cakeTop);
            }
        }
        if (secondCake.hasTouched) {
            float ballH = 200.0f;
            float ballW = 130.0f;
            canvas.drawRect(secondCake.touchX + ballW / 2 - 50, secondCake.touchY + ballH - 5, secondCake.touchX + ballW / 2 - 15, secondCake.touchY + ballH + 200, Black);
            canvas.drawOval(secondCake.touchX - ballW / 2, secondCake.touchY - ballH / 2, secondCake.touchX + ballW, secondCake.touchY + ballH, balloonPaint);

        }
        drawTheText(canvas);


    drawSqaure(canvas, secondCake.touchX, secondCake.touchY);
    }//onDraw


    public void drawTheText(Canvas canvas) {

        canvas.drawText("X:" + secondCake.touchX + "  Y:" + secondCake.touchY, 1300, 600, textPaint);
    }

}//class CakeView

