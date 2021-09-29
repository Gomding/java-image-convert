package com.example.javaimageconvert;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.job.FFmpegJob;
import org.bytedeco.javacpp.Loader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

class JavaCvFFmpegTest {

    private static final String GIF_FILENAME = "static/image/jjv1FK.gif";

    String ffmpegPath = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);

    @Test
    void test() throws IOException {
        URL resource = getClass().getClassLoader().getResource(GIF_FILENAME);
        File gifFile = new File(resource.getPath());
        File mp4File = new File(gifFile.getPath().replace(".gif", ".mp4"));

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

        FFmpegExecutor executor = new FFmpegExecutor(new FFmpeg(ffmpegPath));
        FFmpegJob job = executor.createJob(builder);
        job.run();
    }
}
