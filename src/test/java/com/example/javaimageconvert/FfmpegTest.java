package com.example.javaimageconvert;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FfmpegTest {

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Value("${ffprobe.path}")
    private String ffprovePath;

    private static final String GIF_FILENAME = "static/image/jjv1FK.gif";
    private static final String MP4_FILENAME = "static/image/conver_jjv1FK.mp4";
    private static final String PNG_FILENAME = "static/image/red_hat.png";
    private static final String JPG_FILENAME = "static/image/jjv1FK_to_jpg.jpg";

    @Test
    void test() {
        System.out.println(ffmpegPath);
        System.out.println(ffprovePath);

        URL resource = getClass().getClassLoader().getResource(GIF_FILENAME);
        File gifFile = new File(resource.getPath());

        File mp4File = new File(gifFile.getPath().replace(".gif", ".mp4"));

        try {
            final FFmpeg fFmpeg = new FFmpeg(ffmpegPath);
            final FFprobe fFprobe = new FFprobe(ffprovePath);
            System.out.println("initComplete!!!");

            assertThat(fFmpeg.isFFmpeg()).isTrue();
            assertThat(fFprobe.isFFprobe()).isTrue();

            FFmpegBuilder builder = new FFmpegBuilder()
                    .overrideOutputFiles(true)
                    .addExtraArgs("-r", "10")
                    .setInput(gifFile.getPath())
                    .addOutput(mp4File.getPath())
                    .addExtraArgs("-an")
                    .setVideoPixelFormat("yuv420p")
                    .setVideoMovFlags("faststart")
                    .setVideoWidth(240)
                    .setVideoHeight(182)
                    .setVideoFilter("scale=trunc(iw/2)*2:trunc(ih/2)*2")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
            FFmpegJob job = executor.createJob(builder);
            System.out.println("before run job state!!! is " + job.getState());
            job.run();
            System.out.println("after run job state!!! is " + job.getState());

        } catch (IOException e) {
            System.out.println("fail!!!");
            e.printStackTrace();
        }
    }
}
