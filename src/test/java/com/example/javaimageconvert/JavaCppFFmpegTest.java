package com.example.javaimageconvert;

import org.bytedeco.javacpp.Loader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class JavaCppFFmpegTest {

    @Test
    void test() {
        String ffmpeg = Loader.load(org.bytedeco.ffmpeg.ffmpeg.class);
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    ffmpeg,
                    "-y",
                    "-v",
                    "error",
                    "-r",
                    "10",
                    "-i",
                    "/Users/parksmac/IdeaProjects/java-image-convert/build/resources/main/static/image/jjv1FK.gif",
                    "-pix_fmt",
                    "yuv420p",
                    "-movflags",
                    "faststart",
                    "-s",
                    "240x182",
                    "-vf",
                    "scale=trunc(iw/2)*2:trunc(ih/2)*2",
                    "-an",
                    "/Users/parksmac/IdeaProjects/java-image-convert/build/resources/main/static/image/jjv1FKCPP.mp4"
            );

            pb.command().forEach(System.out::println);

            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();
            System.out.println(process.isAlive());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
