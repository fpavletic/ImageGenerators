package shapeGenerators;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class CircleGenerator implements ShapeGenerator {
    private static final Random RNG = new Random(System.nanoTime());

    private int x0, y0, width, height, diameter;

    @Override
    public void drawShape(Graphics graphics){
        graphics.fillOval(x0, y0, diameter, diameter);
    }

    @Override
    public void generateShape(int width, int height, int diameter){
        x0 = RNG.nextInt(width);
        y0 = RNG.nextInt(height);
        this.width = width;
        this.height = height;
        this.diameter = diameter;
    }

    @Override
    public Iterator<Point> iterator(){
        return new CircleIterator();
    }

    private class CircleIterator implements Iterator<Point>{

        private int x, y;
        private int maxX, maxY;

        private Point p = new Point();

        public CircleIterator(){
            maxX = x0 + diameter;
            maxY = y0 + diameter;
            x = x0;
            y = y0;
        }

        @Override
        public boolean hasNext(){
            return x != width && x < maxX;
        }

        @Override
        public Point next(){
            p.y = y;
            p.x = x;
            if ( ++y == maxY || y == height ){
                y = y0;
                x++;
            }
            return p;
        }
    }
}
