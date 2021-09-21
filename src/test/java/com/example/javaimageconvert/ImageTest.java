package com.example.javaimageconvert;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

class ImageTest {

    private static final String GIF_FILENAME = "static/image/jjv1FK.gif";
    private static final String MP4_FILENAME = "static/image/conver_jjv1FK.mp4";
    private static final String PNG_FILENAME = "static/image/mickey.png";
    private static final String JPG_FILENAME = "static/image/mickey2222.png";

    @Test
    void test() throws IOException {
        final String fileName = PNG_FILENAME;
        final String newFileName = JPG_FILENAME;

        URL resource = getClass().getClassLoader().getResource(fileName);
        File file = new File(resource.getPath());

        URL resource2 = getClass().getClassLoader().getResource(newFileName);
        File newFile = new File(resource2.getPath());

        BufferedImage imageIO = ImageIO.read(file);
        Image resizedImage = imageIO.getScaledInstance(imageIO.getWidth(), imageIO.getHeight(), Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(imageIO.getWidth(), imageIO.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = bufferedImage.getGraphics();

//        Color invisible = new Color(0,0,0,);
        graphics.drawImage(resizedImage, 0, 0,null);
        ImageIO.write(bufferedImage, "png", newFile);

        graphics.dispose();
    }
}
