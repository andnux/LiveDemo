package top.andnux.liblive.params;

import android.media.AudioFormat;

public class AudioParams {

    private int sampleRateInHz = 44100;
    private int channel = 1;
    private int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    public AudioParams() {
    }

    public AudioParams(int sampleRateInHz, int channel,
                       int audioFormat) {
        this.sampleRateInHz = sampleRateInHz;
        this.channel = channel;
        this.audioFormat = audioFormat;
    }

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public void setSampleRateInHz(int sampleRateInHz) {
        this.sampleRateInHz = sampleRateInHz;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public int getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(int audioFormat) {
        this.audioFormat = audioFormat;
    }
}
