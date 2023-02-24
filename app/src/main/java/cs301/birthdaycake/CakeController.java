package cs301.birthdaycake;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private CakeModel cakeModel;
    private CakeView cakeView;

    public CakeController(CakeView initCakeView) {
        cakeView = initCakeView;
        cakeModel = cakeView.getCake();
    }


    @Override
    public void onClick(View view) {
        Log.d("CakeController", " Button clocked");
        if (cakeModel.candleLit == false) {
            cakeModel.candleLit = true;
        } else {
            cakeModel.candleLit = false;
        }
        cakeView.invalidate();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (cakeModel.hasCandle == true) {
            cakeModel.hasCandle = false;
        } else {
            cakeModel.hasCandle = true;
        }
        cakeView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        cakeModel.numCandle = i;
        cakeView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //Get XY Coordinates
        cakeModel.touchX = motionEvent.getX();
        cakeModel.touchY = motionEvent.getY();
        addBalloon(cakeModel.touchX,cakeModel.touchY);
        addRect(motionEvent.getX(), motionEvent.getY());

        //Reset after finished
        cakeView.invalidate();
        return true;
    }
    private void addRect(float x, float y) {
        cakeModel.touchX=x;
        cakeModel.touchY = y;
        cakeModel.hasTouched = true;
        cakeView.invalidate();
    }

    public void addBalloon(float x, float y) {
        cakeModel.touchX=x;
        cakeModel.touchY = y;
        cakeModel.hasTouched = true;
        cakeView.invalidate();
    }
}
