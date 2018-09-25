package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageGeneratorDisplay extends JFrame {

    private BufferedImage generatedImage;

    public ImageGeneratorDisplay(int width, int height, BufferedImage generatedImage){
        this.generatedImage = generatedImage;
        this.setPreferredSize(new Dimension(width, height));
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g){
        generatedImage.flush();
        g.drawImage(generatedImage, 0, 0, null);
    }
}
