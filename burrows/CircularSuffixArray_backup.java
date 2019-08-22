/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class CircularSuffixArray_backup {

    private final Integer[] suffixes;

    // circular suffix array of s
    private String s ;
    public CircularSuffixArray_backup(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        this.s = s;
        final int len = s.length();
        Integer[] suffixes = new Integer[len];
        for (int i = 0; i < s.length(); i++) {
            suffixes[i] = i;  // index in origin suffix array
        }
        Arrays.sort(suffixes, (offsetA, offsetB) -> {
            // offset: start index of each suffix
            int diff = 0;
            for(int cnt = 0 ; cnt < len ; cnt ++) {
                int ia = (offsetA + cnt) % len;
                int ib = (offsetB + cnt) % len;

                diff = s.charAt(ia) - s.charAt(ib);
                if (diff != 0) {
                    return diff;
                }
            }
            return 0;
        });

        /*
        int[][] suffixes = new int[len][2];
        for (int i = 0; i < s.length(); i++) {
            int ch = s.charAt((i-1 + len)%len);
            suffixes[i][0] = ch; // transformed-char
            suffixes[i][1] = i;  // index in origin suffix array
        }
        Arrays.sort(suffixes, (a, b) -> {
            // offset: start index of each suffix
            int offsetA = a[1];
            int offsetB = b[1];
            int diff = 0;
            for(int cnt = 0 ; cnt < len ; cnt ++) {
                int ia = (offsetA + cnt) % len;
                int ib = (offsetB + cnt) % len;

                diff = s.charAt(ia) - s.charAt(ib);
                if (diff != 0) {
                    return diff;
                }
            }
            return 0;
        });

         */

        this.suffixes = suffixes;
    }

    // length of s
    public int length() {
        return s.length();
    }

    /**
     * returns index at origin suffix of i-th sorted suffix
      * @param i index at sorted suffix
     * @return index at origin suffix of the given sorted suffix
     */
    public int index(int i) {
        if (i < 0 || i >= s.length()) {
            throw new IllegalArgumentException();
        }

        return suffixes[i];
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
