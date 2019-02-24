package imageGenerator;

import shapeGenerators.CircleGenerator;
import shapeGenerators.ShapeGenerator;
import util.ImageGeneratorDisplay;
import util.IntIntToIntArrayBiFunction; //Gotta go fast
import util.ManhattanDistanceCalculator;
import util.RgbTransformer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class ImageGenerator {

    private static final Random RNG = new Random(System.nanoTime());

    private BufferedImage sourceImage;
    private int sourceWidth;
    private int sourceHeight;
    private List<Integer> sourceColours;

    private BufferedImage generatedImage;
    private Graphics2D generatedImageGraphics;
    private ImageGeneratorDisplay generatedImageDisplay;

    private ShapeGenerator shapeGenerator;
    private int lengthFactor;

    public ImageGenerator(File sourceFile, ShapeGenerator shapeGenerator, int lengthFactor ) throws IOException{
        sourceImage = ImageIO.read(sourceFile);
        sourceWidth = sourceImage.getWidth();
        sourceHeight = sourceImage.getHeight();
        sourceColours = readColours();

        generatedImage = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_INT_RGB);
        generatedImageGraphics = generatedImage.createGraphics();

        generatedImageDisplay = new ImageGeneratorDisplay(sourceWidth, sourceHeight, generatedImage);

        this.shapeGenerator = shapeGenerator;
        this.lengthFactor = lengthFactor;
    }

    private List<Integer> readColours(){

        Set<Integer> tmp = new HashSet<>();
        for ( int i = 0; i < sourceWidth; i++ ) {
            for ( int j = 0; j < sourceHeight; j++ ) {
                tmp.add(sourceImage.getRGB(i, j));
            }
        }
        return new ArrayList<>(tmp);
    }

    public void generate(long iterations) throws IOException{
        int max = Math.min(sourceWidth, sourceHeight) / lengthFactor;
        new Scanner(System.in).nextLine();
        for ( long current = 0; current < iterations; current++ ) {
            double percentDone = current / (double) iterations;

            iteration((int)((1 - Math.sqrt(percentDone)) * max));
            generatedImageDisplay.repaint();
        }
        ImageIO.write(generatedImage, "png", new File("./paleCrtica.png"));
        System.out.println("Done!");

    }

    private void iteration(int length){
        shapeGenerator.generateShape(sourceWidth, sourceHeight, length);
        int[] colour = RgbTransformer.fromInt(sourceColours.get(RNG.nextInt(sourceColours.size())));
        if ( isShouldDraw((i, j) -> RgbTransformer.fromInt(generatedImage.getRGB(i, j)), (i, j) -> colour) ) {
            generatedImageGraphics.setColor(new Color(RgbTransformer.toInt(colour)));
            shapeGenerator.drawShape(generatedImageGraphics);
        }
    }

    private boolean isShouldDraw(IntIntToIntArrayBiFunction generatedRgbGetter, IntIntToIntArrayBiFunction colourRgbGetter){
        int distanceGenerated = 0, distanceColour = 0;
        for(Point point : shapeGenerator){
            distanceColour += ManhattanDistanceCalculator.calculate(colourRgbGetter.apply(point.x, point.y), RgbTransformer.fromInt(sourceImage.getRGB(point.x, point.y)));
            distanceGenerated += ManhattanDistanceCalculator.calculate(generatedRgbGetter.apply(point.x, point.y), RgbTransformer.fromInt(sourceImage.getRGB(point.x, point.y)));
        }
        return distanceColour < distanceGenerated;
    }

    public static void main(String[] args) throws IOException{

//        ImageGenerator imgGen = new ImageGenerator(new File("./testImg.jpg"), new BresenhamLineGenerator(), 25);
//        ImageGenerator imgGen = new ImageGenerator(new File("./testImg.jpg"), new LineGenerator(), 25);
        ImageGenerator imgGen = new ImageGenerator(new File("./testImg.jpg"), new CircleGenerator(), 50);
        imgGen.generate(4000000);

    }
}
