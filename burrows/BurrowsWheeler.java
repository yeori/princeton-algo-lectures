import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform() {

        String in = BinaryStdIn.readString();
        int len = in.length();
        CircularSuffixArray arr = new CircularSuffixArray(in);

        byte [] buf = new byte[in.length()];
        for (int i = 0; i < in.length(); i++) {
            int pos = arr.index(i);
            buf[i] = (byte) in.charAt((pos-1+len)%len); // prevent underflow at pos-1
            if (pos == 0) {
                // this is first row
                BinaryStdOut.write(i);
            }
        }

        // flush remaining tokens
        for (int i = 0 ; i < buf.length ; i++) {
            BinaryStdOut.write((char)(buf[i] & 0xFF));
        }

        BinaryStdOut.flush();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int row = BinaryStdIn.readInt(); // first row index to probe

        StringBuilder sb = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            sb.append(BinaryStdIn.readChar());
        }

        int [][] ts = new int[sb.length()][2]; // list of [char, row-index]
        for (int k = 0; k < ts.length; k++) {
            ts[k][0] = sb.charAt(k);
            ts[k][1] = k;
        }
        // sort by character and then index
        Arrays.sort(ts, (a,b) -> {
            int diff = a[0] - b[0];
            return diff == 0 ? a[1] - b[1] : diff;
        });

        int [] ch;
        for (int i = 0 ; i < sb.length(); i++) {
            ch = ts[row];
            BinaryStdOut.write((char)ch[0]);
            row = ch[1]; //next row
        }
        BinaryStdOut.flush();

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if ("-".equals(args[0])) {
            transform();
        } else {
            inverseTransform();
        }
    }
}
