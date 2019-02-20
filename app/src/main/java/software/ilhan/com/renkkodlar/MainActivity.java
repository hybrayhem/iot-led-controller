package software.ilhan.com.renkkodlar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//github description:
// Color picker
//-picks a color from color wheel and converts it to hex and rgb values

public class MainActivity extends AppCompatActivity {

    ImageView colorWheel;
    TextView txtResult;
    View colorView;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorWheel = findViewById(R.id.colorWheel);
        colorView = findViewById(R.id.colorView);
        txtResult = findViewById(R.id.txtResult);

        colorWheel.setDrawingCacheEnabled(true);
        colorWheel.buildDrawingCache(true);

        //colorWheel on touch listener
        colorWheel.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = colorWheel.getDrawingCache();

                    int pixel = bitmap.getPixel((int)event.getX(), (int)event.getY());

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int b = Color.blue(pixel);

                    String hex = "#" + Integer.toHexString(pixel);



                    colorView.setBackgroundColor(Color.rgb(r,g,b));

                    txtResult.setText("HEX: "+hex+"\nRGB: "+r+", "+g+", "+b);

                }
                return true;
            }

        });

    }
}

