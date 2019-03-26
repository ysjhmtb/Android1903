package com.example.intentservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Log.e("Debug : ", "handleMessage");
            Toast.makeText(getApplicationContext(), "handleMessage", Toast.LENGTH_LONG).show();

            Object path = msg.obj;

            if(obtainMessage().arg1 == RESULT_OK && path != null){
                Toast.makeText(getApplicationContext(), " " + path.toString() + " downloaded ", Toast.LENGTH_LONG ).show();
            }else{
                Toast.makeText(getApplicationContext(), "Downloading failed ", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){

        Log.e("Debug : ", "onClick");


        Intent intent = new Intent(this, DownloadService.class);
        Messenger messenger = new Messenger(handler);
        intent.putExtra("MESSENGER", messenger);
        intent.setData(Uri.parse("http://www.naver.com"));
        intent.putExtra("urlpath","http://www.naver.com");
        startService(intent);
    }
}
