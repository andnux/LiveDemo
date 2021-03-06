package top.andnux.liblive.params;

import android.hardware.Camera;

public class VideoParams {

    private int width = 320;
    private int height = 480;
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int bitrate = 480000;
    private int fps = 25;

    public VideoParams() {

    }

    public VideoParams(int width, int height, int cameraId,
                       int bitrate, int fps) {
        this.width = width;
        this.height = height;
        this.cameraId = cameraId;
        this.bitrate = bitrate;
        this.fps = fps;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }
}
