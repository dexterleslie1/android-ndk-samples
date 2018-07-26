package com.xy.android.aec;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.audiofx.AcousticEchoCanceler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.LinkedList;

@RunWith(AndroidJUnit4.class)
public class AcousticEchoCancellationTest {
    @Rule
    public final PermissionsRule permissionsRule = new PermissionsRule(new String[]{Manifest.permission.RECORD_AUDIO});

    /**
     * 使用android emulator测试以下代码会出现严重回声
     */
    @Test
    public void echo_test() {
        // 开启扬声器
        AudioManager audioManager=(AudioManager) InstrumentationRegistry.getTargetContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(true);

        final AudioTrack audioPlay ;
        final AudioRecord audioRecord ;
        Thread thread;

        final int frequency = 8000;
        final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        final LinkedList<short[]> listAudio = new LinkedList<short[]>();

        int bufferSize = android.media.AudioTrack.getMinBufferSize(8000, AudioFormat.CHANNEL_OUT_MONO, audioEncoding);
        int bufferSizeRec = android.media.AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_IN_MONO, audioEncoding);
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION , frequency, AudioFormat.CHANNEL_IN_MONO,
                audioEncoding,	bufferSizeRec );

        audioRecord.startRecording();

        audioPlay = new AudioTrack(AudioManager.STREAM_VOICE_CALL, 8000, AudioFormat.CHANNEL_OUT_MONO, audioEncoding, bufferSize,
                AudioTrack.MODE_STREAM,audioRecord.getAudioSessionId());

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean playStart = false;
                short data [] = new short[160];
                int	bufferRead;
                while (true) {
                    bufferRead = audioRecord.read(data, 0, data.length);
                    short out [] = new short[bufferRead];
                    System.arraycopy(data, 0, out, 0, bufferRead);
                    listAudio.add(out);
                    if (playStart) {
                        data = listAudio.removeFirst();
                        audioPlay.write(data, 0, data.length);
                    }
                    else {
                        if (listAudio.size() > 5) {
                            playStart = true;
                            audioPlay.play();
                        }
                    }
                }
            }
        });
        thread.start();

        System.out.println("");

        audioRecord.stop();
        audioRecord.release();
        audioPlay.stop();
        audioPlay.release();
    }

    /**
     *
     */
    @Test
    public void aec_test() throws InterruptedException {
        // 开启扬声器
        AudioManager audioManager=(AudioManager) InstrumentationRegistry.getTargetContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(true);

        final AudioTrack audioPlay ;
        final AudioRecord audioRecord ;
        Thread thread;

        final int frequency = 8000;
        final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        final LinkedList<short[]> listAudio = new LinkedList<short[]>();

        int bufferSize = android.media.AudioTrack.getMinBufferSize(frequency, AudioFormat.CHANNEL_OUT_MONO, audioEncoding);
        int bufferSizeRec = android.media.AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_MONO, audioEncoding);
        audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION , frequency, AudioFormat.CHANNEL_IN_MONO,
                audioEncoding,	bufferSizeRec );

        audioRecord.startRecording();

        audioPlay = new AudioTrack(AudioManager.STREAM_VOICE_CALL, frequency, AudioFormat.CHANNEL_OUT_MONO, audioEncoding, bufferSize,
                AudioTrack.MODE_STREAM,audioRecord.getAudioSessionId());

        SpeexJNI.init();

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                boolean playStart = false;
                short data [] = new short[160];
                short echoData[]=null;
                int	bufferRead;
                while (true) {
                    if (playStart) {
                        echoData = listAudio.removeFirst();
                        audioPlay.write(echoData, 0, echoData.length);
                    }
                    else {
                        if (listAudio.size() > 5) {
                            playStart = true;
                            audioPlay.play();
                        }
                    }
                    bufferRead = audioRecord.read(data, 0, data.length);
                    short out [] = new short[bufferRead];
                    System.arraycopy(data, 0, out, 0, bufferRead);

//                    if(echoData!=null){
//                        short []out1=new short[160];
//                        SpeexJNI.cancellation(out,echoData,out1);
//                        listAudio.add(out1);
//                    }else {
                        listAudio.add(out);
//                    }
                }
            }
        });
        thread.start();

        System.out.println("");

        SpeexJNI.destroy();

        audioRecord.stop();
        audioRecord.release();
        audioPlay.stop();
        audioPlay.release();
    }
}
