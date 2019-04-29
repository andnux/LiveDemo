package top.andnux.liblive.pusher;

import android.Manifest;
import android.hardware.Camera;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresPermission;

import top.andnux.liblive.jni.PushNative;
import top.andnux.liblive.params.AudioParams;
import top.andnux.liblive.params.VideoParams;

public class LivePusher implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private VideoPusher mVideoPusher;
    private AudioPusher mAudioPusher;
    private PushNative mPushNative;

    @RequiresPermission(allOf = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
    })
    public LivePusher(SurfaceHolder holder) {
        mHolder = holder;
        mHolder.setKeepScreenOn(true);
        mHolder.addCallback(this);
        prepare();
    }

    private void prepare() {
        mPushNative = new PushNative();
        VideoParams videoParams = new VideoParams(480, 320,
                Camera.CameraInfo.CAMERA_FACING_BACK, 480000, 25);
        mVideoPusher = new VideoPusher(mHolder, videoParams, mPushNative);
        AudioParams audioParams = new AudioParams();
        mAudioPusher = new AudioPusher(audioParams, mPushNative);
    }

    public void startPush(String url) {
        mVideoPusher.startPush();
        mAudioPusher.startPush();
        mPushNative.startPush(url);
    }

    public void stopPush() {
        mVideoPusher.stopPush();
        mAudioPusher.stopPush();
        mPushNative.stopPush();
        mPushNative.release();
    }

    public void swithCamera() {
        mVideoPusher.swithCamera();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPush();
    }
}
