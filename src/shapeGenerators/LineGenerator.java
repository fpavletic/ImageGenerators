package shapeGenerators;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class LineGenerator implements ShapeGenerator{
    private static final Random RNG = new Random(System.nanoTime());

    private int x0, y0, width, height, length;

    @Override
    public void generateShape(int width, int height, int length){
        x0 = RNG.nextInt(width);
        y0 = RNG.nextInt(height);
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public void drawShape(Graphics graphics){
        graphics.drawLine(x0, y0, x0 + (int)Math.ceil(length / 3.1623), y0 + 3 * (int)Math.ceil(length / 3.1623));
    }

    @Override
    public Iterator<Point> iterator(){
        return new LineIterator();
    }

    private class LineIterator implements Iterator<Point>{

        private int x, y, dx, dy = 0;

        private Point p = new Point();

        public LineIterator(){
            x = x0;
            y = y0;
            dx = (int)Math.ceil(length / 3.1623);
            if ( x + dx > width ) dx = width - 1 - x;
        }

        @Override
        public boolean hasNext(){
            return x != x0 + dx && y != height;
        }

        @Override
        public Point next(){
            p.y = y++;
            p.x = ++dy % 3 == 0 ? x++ : x;
            return p;
        }
    }
}
