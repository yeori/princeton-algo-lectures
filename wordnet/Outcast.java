/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Outcast {

    private final WordNet wnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wnet = wordnet;
    }
    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int [] answer = {0, Integer.MIN_VALUE}; // [index of nouns, distsum of nouns[index] ]
        for (int i = 0; i < nouns.length; i++) {
            int dist = distSum(wnet, nouns, i);
            if (dist > answer[1]) {
                answer[0] = i;
                answer[1] = dist;
            }

        }
        return nouns[answer[0]];
    }

    private int distSum(WordNet wnet, String [] nouns, int index) {
        int sum = 0;
        for (int i = 0; i < nouns.length; i++) {
            if(i != index) {
                sum += wnet.distance(nouns[i], nouns[index]);
            }
        }
        return sum;
    }

    public static void main(String[] args) {
    }
}
