
import edu.princeton.cs.algs4.Picture;

import java.util.Arrays;

public class SeamCarver {
    private Picture pic;
    private final int [][] rgb;
    private final double[][] e;
    private final double[][] acc;
    private final int[][] path;

    // private final int[] vseam;
    // private final int[] hseam;

    private boolean modified;

    private int vRemoved;
    private int hRemoved;


    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        pic = new Picture(picture);
        // 1. build rgb
        rgb = Seams.toRGB(pic);
        e = Seams.toEnergy(pic, 1000);
        acc = new double[e.length][e[0].length];
        path = new int[e.length][e[0].length];

    }

    private void updatePicture() {
        if (modified) {
            Picture oldPic = pic;
            int w = width();
            int h = height();
            pic = new Picture(w, h);
            for (int ir = 0; ir < h; ir++) {
                for (int ic = 0; ic < w; ic++) {
                    pic.setRGB(ic, ir, rgb[ir][ic]);
                }
            }
            modified = false;
            vRemoved = hRemoved = 0;
        }
    }
    // current picture
    public Picture picture() {
        updatePicture();
        return new Picture(pic);
    }

    // width of current picture
    public int width() {
        return pic.width() - vRemoved;
    }

    // height of current picture
    public int height() {
        return pic.height() - hRemoved;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        int w = width();
        int h = height();
        if(x < 0 || x >= w || y < 0 || y >= h) {
            throw new IllegalArgumentException();
        }
        return e[y][x];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        int w = width();
        int h = height();
        for(int ir = 0; ir < h; ir++) {
            acc[ir][0] = 1000;
        }
        Arrays.fill(acc[0], 1000);
        Arrays.fill(acc[h-1], 1000);

        int minRow = 0;
        for (int ic = 1; ic < w; ic++) {
            minRow = -1;
            double minAcc = Double.POSITIVE_INFINITY;
            for (int ir = 0; ir < h; ir++) {
                int prevrow = miny(acc,ic-1, ir-1, ir, ir+1 );
                path[ir][ic] = prevrow;
                acc[ir][ic] = e[ir][ic] + acc[prevrow][ic-1];
                if (acc[ir][ic] < minAcc) {
                    minAcc = acc[ir][ic];
                    minRow = ir;
                }
            }
        }

        int [] hseam = new int[w];
        for (int ic = hseam.length-1; ic >= 0; ic--) {
            hseam[ic] = minRow;
            minRow = path[minRow][ic];
        }

        return hseam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int w = width();
        int h = height();
        Arrays.fill(acc[0], 1000);
        for (int ir = 0; ir < h; ir++) {
            acc[ir][0] = 1000;
            acc[ir][w-1] = 1000;
        }

        int minCol = 0;
        for (int ir = 1; ir < h; ir++) {
            minCol = -1;
            double minAcc = Double.POSITIVE_INFINITY;
            for (int ic = 0; ic < w; ic++) {
                int prevcol = minx(acc,ir-1, ic-1, ic, ic+1 );
                path[ir][ic] = prevcol;
                acc[ir][ic] = e[ir][ic] + acc[ir-1][prevcol];
                if (acc[ir][ic] < minAcc) {
                    minAcc = acc[ir][ic];
                    minCol = ic;
                }
            }
        }

        int [] vseam = new int[h];
        for (int ir = vseam.length-1; ir >= 0; ir--) {
            vseam[ir] = minCol;
            minCol = path[ir][minCol];
        }
        return vseam;
    }

    private int minx(double[][] acc, int ir, int cl, int cm, int cr) {
        int w = width();

        int idx = cm;
        double minval = acc[ir][idx];
        if(cl >= 0 && acc[ir][cl] < minval) {
            idx = cl;
            minval = acc[ir][idx];
        }
        if(cr < w && acc[ir][cr] < minval) {
            idx = cr;
        }
        return idx;
    }

    private int miny(double[][] acc, int ic, int rl, int rm, int rr) {
        int h = height();

        int idx = rm;
        double minval = acc[idx][ic];
        if(rl >= 0 && acc[rl][ic] < minval) {
            idx = rl;
            minval = acc[rl][ic];
        }
        if(rr < h && acc[rr][ic] < minval) {
            idx = rr;
        }
        return idx;
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        int w = width();
        int h = height();
        checkSeam(seam, w, h);
        h -= 1;
        for (int ic = 0; ic < w; ic++) {
            for (int ir = seam[ic]; ir < h; ir++) {
                rgb[ir][ic] = rgb[ir+1][ic];
            }
        }
        hRemoved++;
        modified = true;
        Seams.updateEnergy(rgb, e, w, height(), 1000);
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        int w = width();
        int h = height();
        checkSeam(seam, h, w);
        w -= 1;

        for (int ir = 0; ir < h; ir++) {
            // System.arraycopy(rgb[ir], 0, tmp, 0, seam[ir]);
            System.arraycopy(rgb[ir], seam[ir]+1, rgb[ir], seam[ir], w-seam[ir]);
            // rgb[ir] = new int[w];
            // System.arraycopy(tmp, 0, rgb[ir], 0, w);
        }
        vRemoved++;
        modified = true;
        Seams.updateEnergy(rgb, e, width(), h, 1000);
    }


    private void checkSeam(int[] seam, int arrLength, int valueRange) {
        if (seam == null) {
            throw new IllegalArgumentException("null seam");
        }

        if (seam.length != arrLength) {
            throw new IllegalArgumentException("seam length different arrLenth : " + arrLength + ", but seam len : " + seam.length);
        }

        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= valueRange) {
                throw new IllegalArgumentException();
            }

            if(i > 0) {
                int diff = seam[i] - seam[i-1];
                if(diff < -1 || diff > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    //  unit testing (optional)
    public static void main(String[] args) {
    }
}
