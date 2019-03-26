package com.example.intentservice;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadService extends IntentService {

    private int result = Activity.RESULT_CANCELED;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("Debug : ", "onHandleIntent");
        Toast.makeText(getApplicationContext(), "onHandleIntent", Toast.LENGTH_LONG).show();

        Uri data = intent.getData();
        String urlPath = intent.getStringExtra("urlpath");
        String buffer = " ";

        InputStream stream = null;

        try {
            URL url = new URL(urlPath);
            stream = url.openConnection().getInputStream();
            InputStreamReader reader = new InputStreamReader(stream);

            int i = 0, next = -1;
            while((next = reader.read()) != -1){
                buffer += " " + (char)next;
                if(++i > 100) break;
            }

            result = Activity.RESULT_OK;

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Bundle extras = intent.getExtras();
        if(extras != null){

            Log.e("Debug : ", "extras");
            Toast.makeText(getApplicationContext(), "extras", Toast.LENGTH_LONG).show();

            Messenger messenger = (Messenger) extras.get("MESSENGER");
            Message msg = Message.obtain();
            msg.arg1 = result;
            msg.obj = buffer;

            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                Log.w(getClass().getName(), "Exception sending message", e);
            }
        }
    }
}
