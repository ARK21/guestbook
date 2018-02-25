package com.ark.view;

import com.vaadin.server.StreamResource;
import nl.captcha.Captcha;
import nl.captcha.text.producer.DefaultTextProducer;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class create captcha component for adding to layout from bufferedImage within nl.captcha.simplecaptcha library
 */
public class CaptchaComponent implements StreamResource.StreamSource {
    // stream to write buffered image
    private ByteArrayOutputStream imageBuffer = null;
    // text of captcha
    private String captchaText;
    @Override
    public InputStream getStream() {
        // create Captcha
        Captcha captcha = new Captcha.Builder(120,50)
                .addText(new DefaultTextProducer(4))
                .addNoise()
                .build();

        try {
            imageBuffer = new ByteArrayOutputStream();
            // write from captcha. get image to stream
            ImageIO.write(captcha.getImage(), "png", imageBuffer);
            // initialize answer
            this.captchaText = captcha.getAnswer();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // return image like input stream
        return  new ByteArrayInputStream(imageBuffer.toByteArray());
    }

    public String getCaptchaText() {
        return captchaText;
    }
}
