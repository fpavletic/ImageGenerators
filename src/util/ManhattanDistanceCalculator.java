package util;

public class ManhattanDistanceCalculator {

    public static int calculate(int[] rgb, int[] sourceRgb){
        int difRed = Math.abs(rgb[0] - sourceRgb[0]);
        int difGreen = Math.abs(rgb[1] - sourceRgb[1]);
        int difBlue = Math.abs(rgb[2] - sourceRgb[2]);
        return difRed + difGreen + difBlue;
    }

}
