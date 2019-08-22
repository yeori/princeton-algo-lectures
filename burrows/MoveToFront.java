/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

/**
 *     0 1 2 3 4 5 6 7 8
 *     a b c d e f g h i   lead('f')
 *   0
 *   f 1 1 1 1 1 x 0 0 0
 *     a b c d e . g h i   lead('c');
 *
 *
 */
public class MoveToFront {

    private static final int EXTENDED_ASCII_SIZE = 256;

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Integer> table = new LinkedList<>();
        for (int i = 0; i < EXTENDED_ASCII_SIZE; i++) {
            table.add(i);
        }

        while (!BinaryStdIn.isEmpty()) {
            int ch = BinaryStdIn.readChar();

            int pos = findPos(table, ch); // position of each char
            BinaryStdOut.write((byte)pos);
        }
        BinaryStdOut.flush();
    }

    private static int findPos(LinkedList<Integer> table, int ch) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i) == ch) {
                table.remove(i);
                table.addFirst(ch);
                return i;
            }
        }
        throw new RuntimeException("can't be here");
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Integer> table = new LinkedList<>();
        for (int i = 0; i < EXTENDED_ASCII_SIZE; i++) {
            table.add(i);
        }

        while (!BinaryStdIn.isEmpty()) {
            int pos = BinaryStdIn.readChar();
            int  ch = table.remove(pos); // char at table[pos]
            table.addFirst(ch);
            BinaryStdOut.write((char)ch);
        }
        BinaryStdOut.flush();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if ("-".equals(args[0])) {
            encode();
        } else if ("+".equals(args[0])) {
            decode();
        } else {
            System.err.printf("unknown option \"%s\", + or - is allowed\n", args[0]);
        }
    }

}
