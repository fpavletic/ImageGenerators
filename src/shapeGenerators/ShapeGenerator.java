package shapeGenerators;

import java.awt.*;

public interface ShapeGenerator extends Iterable<Point> {

    void generateShape(int width, int height, int length);

    void drawShape(Graphics graphics);

}
