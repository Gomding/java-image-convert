package com.example.javaimageconvert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageConverter {

    private static final String GIF_FILENAME = "static/image/jjv1FK.gif";
    private static final String MP4_FILENAME = "static/image/conver_jjv1FK.mp4";
    private static final String PNG_FILENAME = "static/image/red_hat.png";
    private static final String JPG_FILENAME = "static/image/jjv1FK_to_jpg.jpg";

    public static void main(String[] args) throws IOException {
        ImageConverter imageConverter = new ImageConverter();
        imageConverter.test();
    }

    private void test() throws IOException {
        final String fileName = PNG_FILENAME;
        final String newFileName = JPG_FILENAME;

        URL resource = getClass().getClassLoader().getResource(fileName);
        File file = new File(resource.getPath());

        URL resource2 = getClass().getClassLoader().getResource(newFileName);
        File newFile = new File(resource2.getPath());

        BufferedImage imageIO = ImageIO.read(file);
        BufferedImage bufferedImage = new BufferedImage(imageIO.getWidth(), imageIO.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();

        bufferedImage.createGraphics().drawImage(imageIO, 0, 0, Color.WHITE, null);

        ImageIO.write(bufferedImage, "jpg", newFile);

        graphics.dispose();
    }
}
