import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntervalAdder {
    public static void main(String[] args) {
        Set<Tuple<Integer,Integer>> disjointed_set
//                 = new HashSet<>(2_000_000);
        = new HashSet<>(List.of(
                new Tuple<>(-5,0),
                new Tuple<>(2,4),
                new Tuple<>(5,8),
                new Tuple<>(10, 15),
                new Tuple<>(17,20),
                new Tuple<>(25,30)
                ));
//        for (int i = -1_000_000; i < 1_000_000; i++) {
//            disjointed_set.add(new Tuple<>(i, i+1));
//        }
        Tuple<Integer,Integer> interval_to_add = new Tuple<>(3,12);
        Collection<Tuple<Integer,Integer>> solution_set = appendInterval(disjointed_set, interval_to_add);
        System.out.println(solution_set);
    }

    public static Collection<Tuple<Integer,Integer>> appendInterval(Set<Tuple<Integer,Integer>> disjointed_set, Tuple<Integer,Integer> interval) {
        long start = System.currentTimeMillis();
        IntervalSearchTree<Integer,Integer> it = new IntervalSearchTree<>();
        for (Tuple<Integer, Integer> t : disjointed_set) {
            it.put(t.x, t.y);
        }
        IntervalSearchTree<Integer,Integer> solution_map = it.intervalSearcher(interval);
        long end = System.currentTimeMillis();
        System.out.printf("Time: %dms \n", end - start);
        return solution_map.entrySet().stream().map(e -> new Tuple<>(e.getKey(), e.getValue())).collect(Collectors.toList());
    }
}
