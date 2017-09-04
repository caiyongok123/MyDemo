package com.example.cy.myapplication.util;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.cy.myapplication.R;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 蔡勇 on 2017-9-2.
 */

public class MyAudioRecorder {

    public MyAudioRecorder(SoundLevelListener soundLevelListener) {
        this.soundLevelListener = soundLevelListener;
    }

    SoundLevelListener soundLevelListener;


    public interface SoundLevelListener{
        //声音等级0到8
        void getSoundLevel(int l);
    }
    int soundLevel = 0;//当前分贝级别
    int maxLevel = 8;//最大分贝等级

    int SAMPLE_RATE_IN_HZ = 80;//分贝默认最大值

    int audioSource = MediaRecorder.AudioSource.MIC;//录制声音来源
    int sampleRateInHz = 44100;//44100;//录制频率，44.1k是最兼容的
    int channelConfig = AudioFormat.CHANNEL_CONFIGURATION_MONO;//录制通道
    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;//用ENCODING_PCM_16BIT的音质好，但是需要消耗更多的电量和存储空间

    int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);//录制缓冲大小：可以通过getMinBufferSize来获取

    AudioRecord audioRecord;
    File file;

    File fileTemp;

    boolean isRecording = false;
    Handler han = new Handler();


    public void start(String path) {
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
        file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        fileTemp = new File(path + "_temp.wav");
        if (fileTemp.exists()) {
            fileTemp.delete();
        }
        isRecording = true;
        han.post(new Runnable() {
            @Override
            public void run() {
                if (!isRecording) {
                    return;
                }
                if (soundLevelListener!=null){
                    soundLevelListener.getSoundLevel(soundLevel);
                }
                han.postDelayed(this, 100);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                audioRecord.startRecording();
                short[] buffer = new short[bufferSizeInBytes];
                try {
                    DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileTemp)));
                    while (isRecording) {
                        int bufferReadResult = audioRecord.read(buffer, 0, bufferSizeInBytes);
                        /*****************  计算音量等级大小  ********************/
                        long v = 0;
                        // 将 buffer 内容取出，进行平方和运算
                        for (int i = 0; i < buffer.length; i++) {
                            v += buffer[i] * buffer[i];
                        }
                        // 平方和除以数据总长度，得到音量大小。
                        double mean = v / (double) bufferReadResult;
                        double volume = 10 * Math.log10(mean);
                        soundLevel = (int) (volume / (SAMPLE_RATE_IN_HZ / maxLevel));
                        /**************************************/
                        for (int i = 0; i < bufferReadResult; i++)
                            dos.writeShort(Short.reverseBytes(buffer[i]));
                    }
                    audioRecord.stop();
                    dos.close();
                    copyWaveFile(fileTemp, file);
                    soundLevel = 0;
                } catch (Exception e) {
                }

            }
        }).start();
    }

    public void stop() {
        isRecording = false;
    }

    // 这里得到可播放的音频文件

    private void copyWaveFile(File inFile, File outFile) {

        long totalAudioLen = 0;

        long totalDataLen = 0;

        long longSampleRate = sampleRateInHz;

        int channels = 1;

        long byteRate = 16 * sampleRateInHz * channels / 8;

        byte[] data = new byte[bufferSizeInBytes];

        try {

            FileInputStream in = new FileInputStream(inFile);

            FileOutputStream out = new FileOutputStream(outFile);

            totalAudioLen = in.getChannel().size();

            totalDataLen = totalAudioLen + 36;

            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,

                    longSampleRate, channels, byteRate);
            int size = 0;
            while ((size = in.read(data)) != -1) {
                System.out.println("copyWaveFile...." + size);
                out.write(data, 0, size);

            }

            in.close();

            out.close();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    /**
     * 这里提供一个头信息。插入这些信息就可以得到可以播放的文件。
     * <p>
     * 为我为啥插入这44个字节，这个还真没深入研究，不过你随便打开一个wav
     * <p>
     * 音频的文件，可以发现前面的头文件可以说基本一样哦。每种格式的文件都有
     * <p>
     * 自己特有的头文件。
     */

    private void WriteWaveFileHeader(FileOutputStream out, long totalAudioLen,

                                     long totalDataLen, long longSampleRate, int channels, long byteRate)

            throws IOException {

        byte[] header = new byte[]{
                'R', 'I', 'F', 'F', (byte) (totalDataLen & 0xff), (byte) ((totalDataLen >> 8) & 0xff), (byte) ((totalDataLen >> 16) & 0xff), (byte) ((totalDataLen >> 24) & 0xff),
                'W', 'A', 'V', 'E', 'f', 'm', 't', ' ',
                16, 0, 0, 0, 1, 0, (byte) channels, 0,
                (byte) (longSampleRate & 0xff), (byte) ((longSampleRate >> 8) & 0xff), (byte) ((longSampleRate >> 16) & 0xff), (byte) ((longSampleRate >> 24) & 0xff), (byte) (byteRate & 0xff), (byte) ((byteRate >> 8) & 0xff), (byte) ((byteRate >> 16) & 0xff), (byte) ((byteRate >> 24) & 0xff),
                (byte) (2 * 16 / 8), 0, 16, 0, 'd', 'a', 't', 'a',
                (byte) (totalAudioLen & 0xff), (byte) ((totalAudioLen >> 8) & 0xff), (byte) ((totalAudioLen >> 16) & 0xff), (byte) ((totalAudioLen >> 24) & 0xff)
        };
        out.write(header, 0, 44);

    }
}
