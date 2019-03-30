package shapeGenerators;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class TriangleGenerator implements ShapeGenerator {
    protected static final Random RNG = new Random(System.nanoTime());

    protected int[] xCoordinates = new int[3], yCoordinates = new int[3];
    protected final int VERTEX_COUNT = 3;

    protected int canvasWidth, canvasHeight;

    @Override
    public void drawShape(Graphics graphics){
        graphics.fillPolygon(xCoordinates, yCoordinates, VERTEX_COUNT);
    }

    @Override
    public void generateShape(int canvasWidth, int canvasHeight, int diameter){
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        int x0 = RNG.nextInt(canvasWidth - diameter);
        int y0 = RNG.nextInt(canvasHeight - diameter);
        for ( int i = 0; i < VERTEX_COUNT; i++ ){
            xCoordinates[i] = x0 + RNG.nextInt(diameter);
            yCoordinates[i] = y0 + RNG.nextInt(diameter);
        }
    }

    @Override
    public Iterator<Point> iterator(){
        return new TriangleIterator();
    }

    private class TriangleIterator implements Iterator<Point>{

        private int x, y;
        private int maxX, minY, maxY;

        private Point p = new Point();

        public TriangleIterator(){
            maxX = max(xCoordinates);
//            maxX = maxX < canvasWidth ? maxX > 0 ? maxX : 0 : canvasWidth - 1;
            maxY = max(yCoordinates);
//            maxY = maxY < canvasHeight ? maxY > 0 ? maxY : 0 : canvasHeight - 1;
            x = min(xCoordinates);
//            x = x < canvasWidth ? x > 0 ? x : 0 : canvasWidth - 1;
            minY =min(yCoordinates);
//            minY = minY < canvasHeight ? minY > 0 ? minY : 0 : canvasHeight - 1;
            y = minY;
        }

        @Override
        public boolean hasNext(){
            return x < maxX;
        }

        @Override
        public Point next(){
            p.x = x;
            p.y = y;
            if ( ++y >= maxY ){
                y = minY;
                x++;
            }
            return p;
        }
    }

    private static int max(int[] array){
        int max = Integer.MIN_VALUE;
        for(int current : array){
            max = Integer.max(max, current);
        }
        return max;
    }

    private static int min(int[] array){
        int min = Integer.MAX_VALUE;
        for(int current : array){
            min = Integer.min(min, current);
        }
        return min;
    }
}
