package top.andnux.liblive.jni;

import android.util.Log;

public class PushNative {

    public static final int CONNECT_FAILED = 101;
    public static final int INIT_FAILED = 102;

    private LiveStateChangeListener liveStateChangeListener;

    public LiveStateChangeListener getLiveStateChangeListener() {
        return liveStateChangeListener;
    }

    public void setLiveStateChangeListener(LiveStateChangeListener liveStateChangeListener) {
        this.liveStateChangeListener = liveStateChangeListener;
    }

    /**
     * 接收Native层抛出的错误
     *
     * @param code
     */
    public void throwNativeError(int code) {
        Log.e("TAG", String.valueOf(code));
        if (liveStateChangeListener != null) {
            liveStateChangeListener.onError(code);
        }
    }

    static {
        System.loadLibrary("live");
    }

    public native void startPush(String url);

    public native void stopPush();

    public native void release();

    public native void setVideoOptions(int width, int height,
                                       int bitrate, int fps);

    public native void setAudioOptions(int sampleRateInHz,
                                       int channel, int audioFormat);

    public native void sendVideo(byte[] data);

    public native void sendAudio(byte[] data, int length);
}
