package software.ilhan.com.renkkodlar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
//github description:
// Color picker
//-picks a color from color wheel and converts it to hex and rgb values

public class MainActivity extends AppCompatActivity {

    ImageView colorWheel;
    ImageView picker;
    TextView txtResult;
    View colorView;

    EditText txtAddress;
    Socket myAppSocket = null;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorWheel = (ImageView) findViewById(R.id.colorWheel);
        picker = (ImageView) findViewById(R.id.picker);
        colorView = (View) findViewById(R.id.colorView);
        txtResult = (TextView) findViewById(R.id.txtResult);

        colorWheel.setDrawingCacheEnabled(true);
        colorWheel.buildDrawingCache(true);

        txtAddress = (EditText) findViewById(R.id.txtAddress);

        final float[] currentX = {466};
        final float[] currentY = {466};

        final String[] old_hex = {"test"};

////////////////////////////////////////////////////////////////////////////////////////////////////
        /*final String[] ip_address = {""};

        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText txtAddress = findViewById(R.id.txtAddress);

        // setup a dialog window
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ip_address[0] = txtAddress.getText().toString();
                Log.d("input_control", "ip adresi: "+ip_address[0]);
            }
        });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();*/

////////////////////////////////////////////////////////////////////////////////////////////////////

        TranslateAnimation animation = new TranslateAnimation(currentX[0], 0, currentY[0], 0);
        animation.setFillAfter(true);
        animation.setDuration(3000);
        picker.startAnimation(animation);

        //colorWheel on touch listener
        colorWheel.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = colorWheel.getDrawingCache();
                    if((int)event.getX()>=0 && (int) event.getX()< bitmap.getWidth() && (int) event.getY()< bitmap.getHeight() && (int)event.getY()>=0) {
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());

                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);

                        String hex = "#" + Integer.toHexString(pixel)
                        String str_R = Integer.toString(r);
                        String str_G = Integer.toString(g);
                        String str_B = Integer.toString(b);
                        String RGB = str_R + str_G + str_B;
                        
                    /*colorWheel.TranslateAnimation(float fromXDelta, float toXDelta,
                    float fromYDelta, float toYDelta)*/
//                        float centreX = colorWheel.getX() + colorView.getWidth() / 2;
//                        float centreY = colorWheel.getY() + colorView.getHeight() / 2;

                        float centreX = 480;
                        float centreY = 480;
                        Log.d("base_feedback", "CenterX: " + centreX + ", CenterY: " + centreY);
                        float newX = (int) event.getX();
                        float newY = (int) event.getY();
                        TranslateAnimation animation = new TranslateAnimation(currentX[0]-centreX, newX-centreX, currentY[0]-centreY, newY-centreY);
                        animation.setFillAfter(true);
                        animation.setDuration(10);
                        picker.startAnimation(animation);
                        currentX[0] = newX;
                        currentY[0] = newY;
                        Log.d("position_feedback", "X: " + newX + ", Y: " + newY);

                        colorView.setBackgroundColor(Color.rgb(r, g, b));

                        txtResult.setText("HEX: " + hex + "\nRGB: " + r + ", " + g + ", " + b);
//                        String old_hex = "";

                        if (!hex.equals(old_hex[0])) {
                            Log.d("hex_change_test", "old_hex: " + old_hex + ", new_hex: " + hex);
                            getIPandPort();
                            CMD = RGB + ".";
                            Socket_AsyncTask rgb = new Socket_AsyncTask();
                            rgb.execute();
                            Log.d("cmd", "HEX String: " + CMD);
                            old_hex[0] = hex;
                        }
                    }
                }
                return true;
            }

        });

        txtResult.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final android.content.ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                String srcText;
                ClipData clipData = ClipData.newPlainText("COLOR", txtResult.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(),"Renk değerleri panoya kopyalandı.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void getIPandPort()   // getIPandPort(String[] ip_address)
    {
        String iPandPort = txtAddress.getText().toString();   // String iPandPort = ip_address[0];
        Log.d("MYTEST","IP String: "+ iPandPort);
        String temp[]= iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST","IP:" +wifiModuleIp);
        Log.d("MY TEST","PORT:"+wifiModulePort);
    }
    public class Socket_AsyncTask extends AsyncTask<Void,Void,Void> {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIp);
                socket = new java.net.Socket(inetAddress, MainActivity.wifiModulePort);
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}

/*centreX=imageView.getX() + imageView.getWidth()  / 2;
 centreY=imageView.getY() + imageView.getHeight() / 2;*/
