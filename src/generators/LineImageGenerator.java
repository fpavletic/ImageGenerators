package generators;

import util.ImageGeneratorDisplay;
import util.ManhattanDistanceCalculator;
import util.RgbTransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;

public class LineImageGenerator {

    private static final Random RNG = new Random();
    private BufferedImage source;
    private int width;
    private int height;
    private List<Integer> colours;
    private BufferedImage generatedImage;
    private ImageGeneratorDisplay display;
    private Graphics2D g;

    public LineImageGenerator(File file) throws IOException{
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
        int max = Math.min(width, height) / 25;
        for ( long current = 0; current < iterations; current++ ){
            double percentDone = current / (double) iterations;
            double length = (1 - Math.sqrt(percentDone)) * max;
            iteration((int)Math.ceil(length));
            display.repaint();
//            System.out.format("current: %d; percentDone: %.2f; radius: %.2f\n", current, percentDone, length);
        }
        ImageIO.write(generatedImage, "jpg", new File("./generatedImg.jpg"));
        System.out.println("Done!");

    }

    private void iteration(int length) {
        int x = RNG.nextInt(width);
        int y = RNG.nextInt(height);
        int colour = colours.get(RNG.nextInt(colours.size()));
        int[] rgb = RgbTransformer.fromInt(colour);
        double[] differences = new double[2];
        differences[0] = calculateDifference(x, y, length, (i, j) -> RgbTransformer.fromInt(colour));
        differences[1] = calculateDifference(x, y, length, (i, j) -> RgbTransformer.fromInt(generatedImage.getRGB(i, j)));

        if ( differences[0] < differences[1] ) {
            g.setColor(new Color(colour));
            g.drawLine(x, y, x + (int)Math.ceil(length / 3.1623), y + 3 * (int)Math.ceil(length / 3.1623));
        }
    }

    private double calculateDifference(int x, int y, int length, BiFunction<Integer, Integer, int[]> rgbGetter){
        double distance = 0;
        int xDif = (int)Math.ceil(length / 3.1623);
        double pixelCount = xDif % 2 == 0 ? xDif * 1.5 + 1 : ( xDif + 1 ) * 1.5;
        for ( int i = x; i < width && i <= x + xDif; i++ ){
            for ( int j = y; j < height && j <= y + (i%2)*2; j++){
                distance += ManhattanDistanceCalculator.calculate(rgbGetter.apply(i, j), RgbTransformer.fromInt(source.getRGB(i, j)));
            }
        }
        return distance / pixelCount;
    }

    public static void main(String[] args) throws IOException{

        LineImageGenerator imgGen = new LineImageGenerator(new File("./testImg7.jpg"));
        imgGen.generate(20000000);

    }

}
