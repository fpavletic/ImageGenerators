package shapeGenerators;

import java.awt.*;

public interface ShapeGenerator extends Iterable<Point> {

    void generateShape(int canvasWidth, int canvasHeight, int size);

    void drawShape(Graphics graphics);

}
