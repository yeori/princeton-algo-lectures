import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.util.Arrays;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SeamCarverTest {


    @Test
    public void energyFunction() {
        Picture pic = new Picture("10x10.png");

        int [][] px = Seams.toRGB(pic);
        printRGB(px);

        double [][] energy = Seams.toEnergy(px, 1000);
        print(energy, "%7.2f");
        SCUtility c;

    }

    @Test
    public void verticalSeam() {
        SeamCarver sc = new SeamCarver(new Picture("8x1.png"));
        int [] vseam = sc.findVerticalSeam();
        System.out.println(Arrays.toString(vseam));
    }

    @Test
    public void remove_h_and_v() {
        SeamCarver sc = new SeamCarver(new Picture("HJocean.png"));
        int [] seam = null;
        seam = sc.findHorizontalSeam();
        sc.removeHorizontalSeam(seam);

        seam = sc.findVerticalSeam();
        sc.removeVerticalSeam(seam);

    }

    @Test
    public void correctness_test12b() {

        // In in = new In("res/6x6-v2.hex");
        // int row = in.readInt();
        // int col = in.readInt();
        // Picture pic = new Picture(col, row);
        // for (int ir = 0; ir < row; ir++) {
        //     for (int ic = 0; ic < col; ic++) {
        //         int color = rgb(in.readString());
        //         pic.setRGB(ic, ir, color);
        //     }
        //
        // }
        // pic.save("rand6x6-v2.png");

        // PrintSeams.main(new String[]{"rand6x6-v2.png"});

        SeamCarver sc = new SeamCarver(new Picture("rand6x6-v2.png"));
        // int[] seam = sc.findHorizontalSeam();
        // System.out.println("H-SEAM");
        // PrintSeams.printSeam(sc, seam, true);


        System.out.println("#2 remove h-seam");
        int [] hseam = sc.findHorizontalSeam();
        PrintSeams.printSeam(sc, hseam, true);
        System.out.println("H-seam: " + Arrays.toString(hseam));
        sc.removeHorizontalSeam(hseam);
        PrintSeams.printSeam(sc, hseam, true);
        hseam = sc.findHorizontalSeam();
        System.out.println("H-seam(after): " + Arrays.toString(hseam));



        // sc.removeVerticalSeam(seam);
        // sc.removeHorizontalSeam(seam);
        // int[] seam2 = sc.findHorizontalSeam();
        /*
1000.00  1000.00* 1000.00  1000.00  1000.00  1000.00
1000.00     6.56*    7.87    13.11     8.83  1000.00
1000.00     7.28*    8.72     6.32    14.32  1000.00
1000.00     8.89*   10.15    12.88    16.12  1000.00
1000.00* 1000.00  1000.00  1000.00  1000.00  1000.00
         */

    }

    private int rgb(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);

        return (r<<16) + (g<<8) + b;

    }

    @Test
    public void dp() {

        double [][] e1 = {
                {0000.00,0000.00,0000.00,0000.00,0000.00,0000.00},
                {1000.00, 237.35, 151.02, 234.09, 107.89,1000.00},
                {1000.00, 138.69, 228.10, 133.07, 211.51,1000.00},
                {1000.00, 153.88, 174.01, 284.01, 194.50,1000.00},
                {1000.00,1000.00,1000.00,1000.00,1000.00,1000.00},
        };

        double [][] e = {
                {1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00},
                {1000.00, 136.73, 272.84, 254.64, 280.20, 178.56, 236.84, 128.93, 172.62,1000.00},
                {1000.00, 222.06, 156.22, 186.79, 208.01, 171.06, 295.56, 125.46, 259.00,1000.00},
                {1000.00, 228.47, 252.25, 168.24, 164.97, 127.18, 209.46, 202.49, 229.03,1000.00},
                {1000.00, 293.85, 274.51, 217.81, 165.63, 175.67, 223.37, 194.78, 243.35,1000.00},
                {1000.00, 208.68, 294.31, 243.53, 161.69, 253.29, 236.73, 217.57, 221.35,1000.00},
                {1000.00, 318.51, 222.38, 240.88, 239.79, 220.45, 259.36, 269.76, 264.14,1000.00},
                {1000.00, 225.20, 270.73, 187.06, 197.58, 165.59, 255.09, 276.89, 124.04,1000.00},
                {1000.00, 222.50, 204.43, 252.65, 270.87, 199.05, 324.13,  90.64, 245.94,1000.00},
                {1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00,1000.00},
        };
        double [][] acc = new double[e.length][e[0].length];
        int [][] path = new int[e.length][e[0].length];
        System.arraycopy(e[0], 0, acc[0], 0, e[0].length); // copy row0


        int minCol = -1;
        for (int ir = 1; ir < e.length; ir++) {
            minCol = -1;
            double minAcc = Double.POSITIVE_INFINITY;
            for (int ic = 0; ic < e[0].length; ic++) {
                int prevcol = minx(acc,ir-1, ic-1, ic, ic+1 );
                path[ir][ic] = prevcol;
                acc[ir][ic] = e[ir][ic] + acc[ir-1][prevcol];
                if (acc[ir][ic] < minAcc) {
                    minAcc = acc[ir][ic];
                    minCol = ic;
                }
            }
        }

        print(e, "%8.2f");
        print(acc, "%8.2f");


        int icol = minCol;
        StringBuilder sb = new StringBuilder();
        for(int ir = e.length-1; ir >= 0 ; ir--) {
            sb.insert(0, icol + " ");
            icol = path[ir][icol];
        }
        System.out.println(sb.toString());

        /*
        { 1000.00, 1000.00, 1000.00, 1000.00, 1000.00, 1000.00,}
        { 1000.00,  237.35,  151.02,  234.09,  107.89, 1000.00,}
        { 1000.00,  138.69,  228.10,  133.07,  211.51, 1000.00,}
        { 1000.00,  153.88,  174.01,  284.01,  194.50, 1000.00,}
        { 1000.00, 1000.00, 1000.00, 1000.00, 1000.00, 1000.00,}

        { 1000.00, 1000.00, 1000.00, 1000.00, 1000.00, 1000.00,}
        { 2000.00, 1237.35, 1151.02, 1234.09, 1107.89, 2000.00,}
        { 2237.35, 1289.71, 1379.12, 1240.96, 1319.40, 2107.89,}
        { 2289.71, 1443.59, 1414.97, 1524.97, 1435.46, 2319.40,}
        { 2443.59, 2414.97, 2414.97, 2414.97, 2435.46, 2435.46,}
         */
    }

    private int minx(double[][] acc, int ir, int cl, int cm, int cr) {
        int idx = cm;
        double minval = acc[ir][idx];
        if(cl >= 0 && acc[ir][cl] < minval) {
            idx = cl;
            minval = acc[ir][idx];
        }
        if(cr < acc.length && acc[ir][cr] < minval) {
            idx = cr;
        }
        return idx;
    }


    private void print(double[][] e, String format) {
        //[     3.142]
        StringBuilder sb = new StringBuilder();
        for (int ir = 0; ir < e.length; ir++) {
            sb.append('{');
            for (int ic = 0; ic < e[0].length; ic++) {
                sb.append(String.format(format + ",", e[ir][ic]));
            }
            sb.append("}\n");
        }
        System.out.println(sb.toString());
    }

    private void printRGB(int[][] rgb) {
        StringBuilder sb = new StringBuilder();
        for (int ir = 0; ir < rgb.length; ir++) {
            for (int ic = 0; ic < rgb[ir].length; ic++) {
                int v = rgb[ir][ic];
                int r = 0xff & (v>>>16);
                int g = 0xff & (v>>>8);
                int b = 0xff & v;
                sb.append(String.format("(%3d,%3d,%3d) ", r, g, b));
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }


    @Test
    public void removeVertical() {
        Picture pic = new Picture("6x5.png");
        SeamCarver sc = new SeamCarver(pic);

    }
}