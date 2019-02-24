package shapeGenerators;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;

public class BresenhamLineGenerator implements ShapeGenerator {

    private static final Random RNG = new Random(System.nanoTime());

    private int x1, y1, x2, y2;

    public void generateShape(int width, int height, int length){

        length = length == 0 ? 1 : length;
        int x1 = RNG.nextInt(width),
                y1 = RNG.nextInt(height);
        int x2 = RNG.nextInt(length + length) + x1 - length,
                y2 = RNG.nextInt(length + length) + y1 - length;

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2 < width ? x2 : width -1;
        this.y2 = y2 < height ? y2 : height -1;
    }

    @Override
    public void drawShape(Graphics graphics){
        graphics.drawLine(x1, y1, x2, y2);
    }

    @Override
    public Iterator<Point> iterator(){
        return new BresenhamLineIterator();
    }

    private class BresenhamLineIterator implements Iterator<Point>{

        private int x, y, dx, dy, dx2, dy2, ix, iy, d;

        private Point p = new Point();

        public BresenhamLineIterator(){

            d = 0;

            dx = Math.abs(x2 - x1);
            dy = Math.abs(y2 - y1);

            dx2 = 2 * dx;
            dy2 = 2 * dy;

            ix = x1 < x2 ? 1 : -1;
            iy = y1 < y2 ? 1 : -1;

            x = x1;
            y = y1;
        }

        @Override
        public Point next(){
            if (dx >= dy) {
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            } else {
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
            p.x = x >= 0 ? x : 0;
            p.y = y >= 0 ? y : 0;
            return p;
        }

        @Override
        public boolean hasNext(){
            return x != x2 && y != y2;
        }

    }
}
