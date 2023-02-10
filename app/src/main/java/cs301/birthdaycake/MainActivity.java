package cs301.birthdaycake;
import android.view.View;
import android.util.Log;


import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);
        CakeView view = findViewById(R.id.cakeview);
        CakeController controller = new CakeController(view);

        Button blowOutButton = findViewById(R.id.button);
        blowOutButton.setOnClickListener(controller);

        CompoundButton switch1 = findViewById(R.id.switch4);
        switch1.setOnCheckedChangeListener(controller);

        SeekBar seekBar1 = findViewById(R.id.seekBar2);
        seekBar1.setOnSeekBarChangeListener(controller);
    }
    public void goodbye(View button) {
        Log.i("button", "Goodbye");
    }
}
