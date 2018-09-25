package util;

public class EuclidianDistanceCalculator {

    public static double calculate(int[] rgb, int[] sourceRGB){
        int difRed = rgb[0] - sourceRGB[0];
        int difGreen = rgb[1] - sourceRGB[1];
        int difBlue = rgb[2] - sourceRGB[2];
        return Math.sqrt(difRed * difRed + difGreen * difGreen + difBlue * difBlue);
    }
}
