import org.junit.Test;

import static org.junit.Assert.*;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class BurrowsWheelerTest {

    @Test
    public void testAndOP() {
        /*
                                   V
        +---------------> DOWNTOWN(0) N
        | +-------------> OWNTOWND(1) D
        | | +-----------> WNTOWNDO(2) O
        | | | +---------> NTOWNDOW(3) W
        | | | | +-------> TOWNDOWN(4) N
        | | | | | +-----> OWNDOWNT(5) T
        | | | | | | +---> WNDOWNTO(6) O
        | | | | | | | +-> NDOWNTOW(7) W
        | | | | | | | |
        D O W N T O W N

        DOWNTOWN(0) N
        OWNTOWND(1) D
        WNTOWNDO(2) O
        NTOWNDOW(3) W
        TOWNDOWN(4) N
        OWNDOWNT(5) T
        WNDOWNTO(6) O
        NDOWNTOW(7) W
            v
           sort
            v
        DOWNTOWN(0) N < first
        NDOWNTOW(7) W
        NTOWNDOW(3) W
        OWNDOWNT(5) T
        OWNTOWND(1) D
        TOWNDOWN(4) N
        WNDOWNTO(6) O
        WNTOWNDO(2) O


        ts i       os(i)  k
        -- --      ----- --
        N  0*  D0   D(4)  0
        W  1   N7  *N(0)  1
        W  2   N3   N(5)  2
        T  3   O5   O(6)  3
        D  4   O1   O(7)  4
        N  5   T4   T(3)  5
        O  6   W6   W(1)  6
        O  7   W2   W(2)  7
        -- --      ----- --
        * ts - transformed symbol at last column
        * os - origin symbol at first column

        +---------> A ZEBRA(0)
        | +-------> Z EBRAZ(1)
        | | +-----> E BRAZE(2)
        | | | +---> B RAZEB(3)
        | | | | +-> R AZEBR(4)
        | | | | |
        Z E B R A

        AZEBR(4) r
        BRAZE(2) e
        EBRAZ(1) z
        RAZEB(3) b
        ZEBRA(0) a < first

        00 00 00 04 r e z b a

         A0       R4
         Z1       E2
         E2       Z1
         B3       B3
         R4       A0

         r0
         e1
         z2
         b3
        >a4

        z e b r a
        | e b r a z
        | | b r a z e
        | | | r a z e b
        | | | | a z e b r
        | | | | |
        v v v v v
        a z e b r

                    t  suffix arr
                   --  ----------
        +---------> O; KAKAO
        | +-------> K; AKAOK
        | | +-----> A; KAOKA
        | | | +---> K; AOKAK
        | | | | +-> A; OKAKA
        | | | | |
        K A K A O

         K; AKAOK
         K; AOKAK
        *O; KAKAO
         A; KAOKA
         A; OKAKA

        00 00 00 02 K K O A A

       >K0       A3
       >K1       A4
       >O2       K0
       >A3       K1
        A4       O2

       O K A K A
       K A K A O
         */
    }
}