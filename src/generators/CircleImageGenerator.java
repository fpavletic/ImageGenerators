package generators;

import util.ImageGeneratorDisplay;
import util.ManhattanDistanceCalculator;
import util.RgbTransformer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;

public class CircleImageGenerator {

    private static final Random RNG = new Random();
    private BufferedImage source;
    private int width;
    private int height;
    private List<Integer> colours;
    private BufferedImage generatedImage;
    private ImageGeneratorDisplay display;
    private Graphics2D g;

    public CircleImageGenerator(File file) throws IOException{
        source = ImageIO.read(file);
        width = source.getWidth();
        height = source.getHeight();
        colours = readColours();
        generatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g = generatedImage.createGraphics();
        display = new ImageGeneratorDisplay(width, height, generatedImage);
    }

    private List<Integer> readColours(){

        Set<Integer> tmp = new HashSet<>();
        for ( int i = 0; i < width; i++ ) {
            for ( int j = 0; j < height; j++ ) {
                tmp.add(source.getRGB(i, j));
            }
        }
        return new ArrayList<>(tmp);
    }

    private void generate(long iterations) throws IOException{
        int max = Math.min(width, height) / 100;
        for ( long current = 1; current < iterations; current++ ) {
            double percentDone = current / (double) iterations;
            double radius = (1 - Math.sqrt(percentDone)) * max;
            iteration((int) Math.ceil(radius));
            display.repaint();
//            System.out.format("current: %d; percentDone: %.2f; radius: %.2f\n", current, percentDone, radius);
        }
        ImageIO.write(generatedImage, "jpg", new File("./generatedImg.jpg"));
        System.out.println("Done!");
    }

    private void iteration(int radius){
        int x = RNG.nextInt(width);
        int y = RNG.nextInt(height);
        int colour = colours.get(RNG.nextInt(colours.size()));
        double[] differences = new double[2];
        differences[0] = calculateDifference(x - radius, y - radius, radius * 2, radius * 2, (i, j) -> RgbTransformer.fromInt(colour));
        differences[1] = calculateDifference(x - radius, y - radius, radius * 2, radius * 2, (i, j) -> RgbTransformer.fromInt(generatedImage.getRGB(i, j)));

        if ( differences[0] < differences[1] ) {
            g.setColor(new Color(colour));
            g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        }
    }

    private double calculateDifference(int x, int y, int width, int height, BiFunction<Integer, Integer, int[]> rgbGetter){
        double distance = 0;
        int pixelCounter = 0;
        for ( int i = x; i < x + width && i < this.width && i >= 0; i++ ) {
            for ( int j = y; j < y + height && j < this.height && j >= 0; j++ ) {
                distance += ManhattanDistanceCalculator.calculate(rgbGetter.apply(i, j), RgbTransformer.fromInt(source.getRGB(i, j)));
                pixelCounter++;
            }
        }
        return distance / pixelCounter;
    }

    public static void main(String[] args) throws IOException{

        CircleImageGenerator imgGen = new CircleImageGenerator(new File("./testImg.jpg"));
        imgGen.generate(4000000);

    }
}
