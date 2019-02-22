package software.ilhan.com.renkkodlar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
//github description:
// Color picker
//-picks a color from color wheel and converts it to hex and rgb values

public class MainActivity extends AppCompatActivity {

    ImageView colorWheel;
    ImageView picker;
    TextView txtResult;
    View colorView;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorWheel = findViewById(R.id.colorWheel);
        picker = findViewById(R.id.picker);
        colorView = findViewById(R.id.colorView);
        txtResult = findViewById(R.id.txtResult);

        colorWheel.setDrawingCacheEnabled(true);
        colorWheel.buildDrawingCache(true);

        final float[] currentX = {466};
        final float[] currentY = {466};

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

                        String hex = "#" + Integer.toHexString(pixel);

                    /*colorWheel.TranslateAnimation(float fromXDelta, float toXDelta,
                    float fromYDelta, float toYDelta)*/
//                        float centreX = colorWheel.getX() + colorView.getWidth() / 2;
//                        float centreY = colorWheel.getY() + colorView.getHeight() / 2;

                        float centreX = 540;
                        float centreY = 540;
                        Log.d("base_feedback", "CenterX: " + centreX + ", CenterY: " + centreY);
                        float newX = (int) event.getX();
                        float newY = (int) event.getY();
                        TranslateAnimation animation = new TranslateAnimation(currentX[0]-466, newX-466, currentY[0]-466, newY-466);
                        animation.setFillAfter(true);
                        animation.setDuration(10);
                        picker.startAnimation(animation);
                        currentX[0] = newX;
                        currentY[0] = newY;
                        Log.d("position_feedback", "X: " + newX + ", Y: " + newY);

                        colorView.setBackgroundColor(Color.rgb(r, g, b));

                        txtResult.setText("HEX: " + hex + "\nRGB: " + r + ", " + g + ", " + b);
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
}

/*centreX=imageView.getX() + imageView.getWidth()  / 2;
 centreY=imageView.getY() + imageView.getHeight() / 2;*/

//TODO
/**
 package com.example.jackyle.wifi_rasp;

 import android.os.AsyncTask;
 import android.support.v7.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;
 import android.util.Log;

 import java.io.DataOutputStream;
 import java.io.IOException;
 import java.net.InetAddress;
 import java.net.Socket;
 import java.net.UnknownHostException;

 public class MainActivity extends AppCompatActivity {
 //UI Element
 Button btnUp;
 Button btnDown;
 EditText txtAddress;
 Socket myAppSocket = null;
 public static String wifiModuleIp = "";
 public static int wifiModulePort = 0;
 public static String CMD = "0";
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);

 btnUp = (Button) findViewById(R.id.btnUp);
 btnDown = (Button) findViewById(R.id.btnDown);

 txtAddress = (EditText) findViewById(R.id.ipAddress);

 btnUp.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 getIPandPort();
 CMD = "Up";
 Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
 cmd_increase_servo.execute();
 }
 });
 btnDown.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 getIPandPort();
 CMD = "Down";
 Socket_AsyncTask cmd_increase_servo = new Socket_AsyncTask();
 cmd_increase_servo.execute();
 }
 });

 }
 public void getIPandPort()
 {
 String iPandPort = txtAddress.getText().toString();
 Log.d("MYTEST","IP String: "+ iPandPort);
 String temp[]= iPandPort.split(":");
 wifiModuleIp = temp[0];
 wifiModulePort = Integer.valueOf(temp[1]);
 Log.d("MY TEST","IP:" +wifiModuleIp);
 Log.d("MY TEST","PORT:"+wifiModulePort);
 }
 public class Socket_AsyncTask extends AsyncTask<Void,Void,Void>
 {
 Socket socket;

 @Override
 protected Void doInBackground(Void... params){
 try{
 InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIp);
 socket = new java.net.Socket(inetAddress,MainActivity.wifiModulePort);
 DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
 dataOutputStream.writeBytes(CMD);
 dataOutputStream.close();
 socket.close();
 }catch (UnknownHostException e){e.printStackTrace();}catch (IOException e){e.printStackTrace();}
 return null;
 }
 }
 }
*/