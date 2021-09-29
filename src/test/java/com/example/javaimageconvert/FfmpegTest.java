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
class FfmpegTest {

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Value("${ffprobe.path}")
    private String ffprovePath;

    private static final String GIF_FILENAME = "static/image/jjv1FK.gif";

    @Test
    void test() throws IOException {
        System.out.println(ffmpegPath);
        System.out.println(ffprovePath);

        URL resource = getClass().getClassLoader().getResource(GIF_FILENAME);
        File gifFile = new File(resource.getPath());

        File mp4File = new File(gifFile.getPath().replace(".gif", ".mp4"));

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

        System.out.println(builder.build());
        builder.build().forEach(System.out::println);

        FFmpegExecutor executor = new FFmpegExecutor(fFmpeg, fFprobe);
        FFmpegJob job = executor.createJob(builder);
        System.out.println("before run job state!!! is " + job.getState());
        assertThat(job.getState()).isEqualTo(FFmpegJob.State.WAITING);
        job.run();
        System.out.println("after run job state!!! is " + job.getState());
        assertThat(job.getState()).isEqualTo(FFmpegJob.State.FINISHED);
    }
}
