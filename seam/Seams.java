/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Picture;

public class Seams {

    public static int[][] toRGB(Picture pic) {
        int [][] rgb = new int[pic.height()][pic.width()];
        for (int x = 0; x < rgb[0].length; x++) {
            for (int y = 0; y < rgb.length; y++) {
                rgb[y][x] = pic.getRGB(x, y);
            }
        }
        return rgb;
    }

    private static double conv(double v) {
        return Math.sqrt(v);
    }

    private static int dualGradient(int colorA, int colorB) {
        int r = (0xff & (colorA >>> 16)) - (0xff & (colorB >>> 16));
        int g = (0xff & (colorA >>>  8)) - (0xff & (colorB >>>  8));
        int b = (0xff & colorA) - (0xff & colorB);
        return r*r + g*g + b*b;
    }

    public static double[][] toEnergy(int[][] px, int inf) {
        double [][] e = new double[px.length][px[0].length];
        for (int ir = 0; ir < e.length; ir++) {
            for (int ic = 0; ic < e[ir].length; ic++) {
                if (ir == 0 || ir == e.length-1 || ic == 0 || ic == e[ir].length-1) {
                    e[ir][ic] = inf;
                } else {
                    e[ir][ic] = conv(dualGradient(px[ir][ic-1], px[ir][ic+1]) + dualGradient(px[ir-1][ic], px[ir+1][ic]));
                }
            }
        }
        return e;
    }

    public static double[][] toEnergy(Picture pic, int inf) {
        double [][] e = new double[pic.height()][pic.width()];
        for (int ir = 0; ir < e.length; ir++) {
            for (int ic = 0; ic < e[ir].length; ic++) {
                if (ir == 0 || ir == e.length-1 || ic == 0 || ic == e[ir].length-1) {
                    e[ir][ic] = inf;
                } else {
                    e[ir][ic] = Math.sqrt(
                            dualGradient(pic.getRGB(ic-1, ir), pic.getRGB(ic+1, ir)) +
                                    dualGradient(pic.getRGB(ic, ir-1), pic.getRGB(ic, ir+1)));
                }
            }
        }
        return e;
    }

    public static double[][] updateEnergy(int[][]rgb, double [][] e, int width, int height, int inf) {
        for (int ir = 0; ir < height; ir++) {
            for (int ic = 0; ic < width; ic++) {
                if (ir == 0 || ir == height-1 || ic == 0 || ic == width-1) {
                    e[ir][ic] = inf;
                } else {
                    e[ir][ic] = Math.sqrt(
                            dualGradient(rgb[ir][ic-1], rgb[ir][ic-1]) +
                                    dualGradient(rgb[ir-1][ic], rgb[ir+1][ic]));
                }
            }
        }
        return e;
    }
}
