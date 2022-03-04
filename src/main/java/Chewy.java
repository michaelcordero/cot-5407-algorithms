import java.util.*;

public class Chewy {

    /**
     * Football Scores The number of goals achieved by two football teams in matches in a league is given in the form
     * of two lists. Consider:
     *
     * Football team A, has played three matches, and has scored teamA = [1, 2, 3] goals in each match respectively.
     * Football team B, has played two matches, and has scored teamB = [2, 4] goals in each match respectively.
     * For each match of team B, compute the total number of matches of team A where team A has scored less than or
     * equal to the number of goals scored by team B in that match.
     *
     * In the above case:
     *
     * For 2 goals scored by team B in its first match, team A has 2 matches with scores 1 and 2. For 4 goals scored
     * by team B in its second match, team A has 3 matches with scores 1, 2 and 3. Hence, the answer: [2, 3].
     *
     */
    public static List<Integer> matching_scores(List<Integer> teamA, List<Integer> teamB) {
        List<Integer> result = new ArrayList<>();
        TreeSet<Integer> tree = new TreeSet<>(teamA);
        for (Integer i : teamB) {
            int occurrences = tree.subSet(Integer.MIN_VALUE, true, i, true).size();
            result.add(occurrences);
        }
        return result;
    }

    public static List<Integer> index_scores(List<Integer> teamA, List<Integer> teamB) {
        // initialize solution
        Stack<Integer> result = new Stack<>();
        // algorithm only works assuming that everything is in sorted order
        // O(n)
        for (int i = teamB.size(); i > 0; i--) {
            int currentB = teamB.get(i-1);
            int currentA = teamA.get(i);
            if (currentB >= currentA) {
                result.push(i+1);
            }
        }
        // rebuild list in O(n)
        List<Integer> solution = new ArrayList<>();
        while (!result.empty()) {
            solution.add(result.pop());
        }
        return solution;
    }

    public static void main(String[] args) {
        List<Integer> teamA = Arrays.asList(1,2,3);
        List<Integer> teamB = Arrays.asList(2,4);
        List<Integer> result = index_scores(teamA,teamB);
        System.out.println("Results: " + result);
    }
}

