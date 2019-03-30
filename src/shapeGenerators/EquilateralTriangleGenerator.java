package shapeGenerators;

public class EquilateralTriangleGenerator extends TriangleGenerator {
    @Override
    public void generateShape(int canvasWidth, int canvasHeight, int radius){
        int x0 = RNG.nextInt(canvasWidth - radius - radius) + radius;
        int y0 = RNG.nextInt(canvasHeight - radius - radius) + radius;
        double angle = 2 * Math.PI * RNG.nextDouble();
        for ( int i = 0; i < VERTEX_COUNT; i++ ){
            xCoordinates[i] = x0 + (int)(Math.cos(angle) * radius);
            yCoordinates[i] = y0 + (int)(Math.sin(angle) * radius);
            angle += 2 * Math.PI / 3;
        }
    }
}
