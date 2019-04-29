package top.andnux.liblive.pusher;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import java.lang.ref.WeakReference;

import top.andnux.liblive.jni.PushNative;
import top.andnux.liblive.params.AudioParams;

class AudioPusher extends Pusher {

    private AudioRecord mAudioRecord;
    private AudioParams mAudioParams;
    private static final int BUFFER_SIZE = 2048;
    private PushNative mPushNative;

    AudioPusher(AudioParams audioParams, PushNative pushNative) {
        mAudioParams = audioParams;
        mPushNative = pushNative;
    }

    @Override
    void startPush() {
        isPushing = true;
        mPushNative.setAudioOptions(mAudioParams.getSampleRateInHz(),
                mAudioParams.getChannel(), mAudioParams.getAudioFormat());
        new AudioRecordThread().start();
    }

    @Override
    void stopPush() {
        isPushing = false;
        Log.d("TAG", "停止音频推流...");
        release();
    }

    @Override
    void release() {
        try {
            if (mAudioRecord != null) {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class AudioRecordThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                int audioSource = MediaRecorder.AudioSource.MIC;
                int channelConfig = mAudioParams.getChannel() == 1 ?
                        AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO;
                int minBufferSize = AudioRecord.getMinBufferSize(mAudioParams.getSampleRateInHz(),
                        channelConfig, mAudioParams.getAudioFormat());
                mAudioRecord = new AudioRecord(audioSource, mAudioParams.getSampleRateInHz(),
                        channelConfig, mAudioParams.getAudioFormat(),
                        Math.max(minBufferSize, BUFFER_SIZE));
                mAudioRecord.startRecording();
                while (isPushing) {
                    byte[] bytes = new byte[minBufferSize];
                    int len = mAudioRecord.read(bytes, 0, bytes.length);
                    if (len > 0) {
                        //进行录音编码
                        Log.d("TAG", "音频编码中...");
                        mPushNative.sendAudio(bytes, len);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
