package util;

public class RgbTransformer {

    public static int[] fromInt (int rgbInt){
        return new int[]{ rgbInt & 255, rgbInt >> 8 & 255, rgbInt >> 16 & 255};
    }

    public static int toInt (int[] rgb ){
        return rgb[0] + ( rgb[1] << 8 ) + ( rgb[2] << 16 );
    }

}
