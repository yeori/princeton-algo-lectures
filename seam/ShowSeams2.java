/******************************************************************************
 *  Compilation:  javac ShowSeams.java
 *  Execution:    java ShowSeams input.png
 *  Dependencies: SeamCarver.java SCUtility.java
 *
 *  Read image from file specified as command line argument. Show 3 images 
 *  original image as well as horizontal and vertical seams of that image.
 *  Each image hides the previous one - drag them to see all three.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class ShowSeams2 {

    static Picture cached;
    private static void showHorizontalSeam(SeamCarver sc) {
        // Picture picture = SCUtility.toEnergyPicture(sc);
        int[] horizontalSeam = sc.findHorizontalSeam();
        cached = SCUtility.seamOverlay(cached, true, horizontalSeam);
        cached.show();
    }


    private static void showVerticalSeam(SeamCarver sc) {
        // Picture picture = SCUtility.toEnergyPicture(sc);
        int[] verticalSeam = sc.findVerticalSeam();
        sc.removeVerticalSeam(verticalSeam);
        cached = SCUtility.seamOverlay(cached, false, verticalSeam);
        // cached.show();
    }

    public static void main(String[] args) {
        Picture picture = new Picture("balloon3.jpg");
        cached = picture;
        StdOut.printf("image is %d columns by %d rows\n", picture.width(), picture.height());
        picture.show();        
        SeamCarver sc = new SeamCarver(picture);

        boolean v = true;

        if (v) {
            for (int i = 0 ; i < 90 ; i++) {
                StdOut.printf("Displaying vertical seam calculated.\n");
                showVerticalSeam(sc);
            }
            cached.show();
        } else {
            StdOut.printf("Displaying horizontal seam calculated.\n");
            showHorizontalSeam(sc);
        }




    }

}
