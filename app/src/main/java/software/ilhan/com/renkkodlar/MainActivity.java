package software.ilhan.com.renkkodlar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

public class MainActivity extends AppCompatActivity {

    ColorPickerView colorPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSelect = (Button) findViewById(R.id.btnselect);
        TextView textView2 = (TextView) findViewById(R.id.textView2);

        final ColorEnvelope colorEnvelope = null;
        btnSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                ColorEnvelope colorEnvelope;
                TextView textView2 = (TextView) findViewById(R.id.textView2);
                if (colorEnvelope.getColorHtml() != null) {
                    textView2.setText(colorEnvelope.getColorHtml());
                }
                else{
                    Toast.makeText(getApplicationContext(),"java.lang.String com.skydoves.colorpickerpreference.ColorEnvelope.getColorHtml()' on a null object reference", Toast.LENGTH_LONG).show();
                }
            }
        });

        /*colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                    LinearLayout linearLayout = findViewById(R.id.linearLayout);
                    linearLayout.setBackgroundColor(colorEnvelope.getColor());
            }
        });*/

    }
}
