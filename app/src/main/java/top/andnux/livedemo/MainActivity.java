package top.andnux.livedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import top.andnux.liblive.pusher.LivePusher;

public class MainActivity extends AppCompatActivity {

    private LivePusher mLivePusher;
    private static final String URL = "rtmp://106.13.8.37/live/test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 0x00);
                return;
            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0x11);
                return;
            }
        }
        mLivePusher = new LivePusher(surfaceView.getHolder());
    }

    public void swithCamera(View view) {
        mLivePusher.swithCamera();
    }

    public void stopLive(View view) {
        mLivePusher.stopPush();
    }

    public void startLive(View view) {
        mLivePusher.startPush(URL);
    }
}
