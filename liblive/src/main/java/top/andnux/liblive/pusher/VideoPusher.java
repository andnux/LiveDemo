package top.andnux.liblive.pusher;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.util.Iterator;
import java.util.List;

import top.andnux.liblive.jni.PushNative;
import top.andnux.liblive.params.VideoParams;

import static android.content.ContentValues.TAG;

class VideoPusher extends Pusher implements SurfaceHolder.Callback,
        Camera.PreviewCallback {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private VideoParams mVideoParams;
    private PushNative mPushNative;

    VideoPusher(SurfaceHolder holder, VideoParams videoParams, PushNative pushNative) {
        mHolder = holder;
        mPushNative = pushNative;
        mVideoParams = videoParams;
        mHolder.addCallback(this);
    }

    @Override
    void startPush() {
        mPushNative.setVideoOptions(mVideoParams.getWidth(),
                mVideoParams.getHeight(), mVideoParams.getBitrate(),
                mVideoParams.getFps());
        isPushing = true;
    }

    @Override
    void stopPush() {
        isPushing = false;
        Log.d("TAG", "停止视频推流...");
    }

    @Override
    void release() {
        stopPreview();
    }

    void swithCamera() {
        if (mVideoParams.getCameraId() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mVideoParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            mVideoParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        stopPreview();
        startPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    /**
     * 开始预览
     */
    private void startPreview() {
        try {
            mCamera = Camera.open(mVideoParams.getCameraId());
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPreviewSize(mVideoParams.getWidth(),mVideoParams.getHeight());
            mCamera.setParameters(parameters);
            mCamera.setPreviewCallback(this);
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 停止预览
     */
    private void stopPreview() {
        try {
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (isPushing) {
            Log.d("TAG", "视频编码中...");
            mPushNative.sendVideo(data);
        }
    }
}
