package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {

    public MyService() {
    }

    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder {
        public void startDownload() {
            Log.d("MyService", "startDownload: ");
        }

        public int getProgress() {
            Log.d("MyService", "getProgress: ");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }

    //创建服务的时候调用
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyService", "onCreate: ");
       /* Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("标题")
                .setContentText("内容")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
        */
    }

    //每次服务启动的时候调用，添加服务一旦启动就执行的逻辑
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    //服务销毁的时候调用
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyService", "onDestroy: ");
    }
}
