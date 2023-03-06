import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class IntervalSearchTree<K extends Comparable<K>,V extends Comparable<V>> extends HackMap<K,V> implements IntervalTree<K,V> {
    ///////////////////////////////////////////////
    // properties
    //////////////////////////////////////////////
    private IntervalTreeNode<K,V> root() {
        if (super.root != null) {
            return new IntervalSearchTreeNode<>(super.root.getKey(),super.root.getValue());
        } else {
            return null;
        }
    }

    ///////////////////////////////////////////////
    // constructors
    //////////////////////////////////////////////
    public IntervalSearchTree() {
    }
    ///////////////////////////////////////////////
    // inner node class
    //////////////////////////////////////////////
    protected static class IntervalSearchTreeNode<K,V> extends HackMap.Entry<K,V> implements IntervalTreeNode<K,V> {
        /////////////////////////////////////////////
        // Properties
        ////////////////////////////////////////////
//        K key;
//        V value;
        V max;
        K min;
        //        protected IntervalSearchTreeNode<K,V> parent;
//        protected IntervalSearchTreeNode<K,V> left;
//        protected IntervalSearchTreeNode<K,V> right;
        ////////////////////////////////////////////
        // constructors
        ////////////////////////////////////////////
        IntervalSearchTreeNode(K key, V value) {
            super(key,value, null);
//            this.key = key;
//            this.value = value;
//            this.left = null;
//            this.right = null;
//            this.parent = null;
            this.max = null;
            this.min = null;
        }

        @Override
        public IntervalTreeNode<K, V> parent() {
            return (IntervalTreeNode<K, V>) parent;
        }

        @Override
        public IntervalTreeNode<K, V> left() {
            return (IntervalTreeNode<K, V>) left;
        }

        @Override
        public IntervalTreeNode<K, V> right() {
            return (IntervalTreeNode<K, V>) right;
        }

        @Override
        public void setLeft(IntervalTreeNode<K, V> left) {
            this.left = (IntervalSearchTreeNode<K, V>) left;
        }

        @Override
        public void setRight(IntervalTreeNode<K, V> right) {
            this.right = (IntervalSearchTreeNode<K, V>) right;
        }

        @Override
        public void setParent(IntervalTreeNode<K, V> parent) {
            this.parent = (IntervalSearchTreeNode<K, V>) parent;
        }

        @Override
        public void setMax(V value) {
            this.max = value;
        }

        @Override
        public V getMax() {
            IntervalTreeNode<K,V> itr = this;
            while (itr.right() != null) {
                itr = itr.right();
            }
            max = itr.getValue();
            return max;
        }

        @Override
        public K getMin() {
            IntervalTreeNode<K,V> itr = this;
            while (itr.left() != null) {
                itr = itr.left();
            }
            min = itr.getKey();
            return min;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        public String toString() {
            return "IntervalSearchTreeNode{" +
                    "key=" + key +
                    ", value=" + value +
                    ", max=" + max +
                    '}';
        }
    }
    ///////////////////////////////////////////////
    // Private
    //////////////////////////////////////////////
//    private int amount(IntervalTreeNode<K,V> node) {
//        if (node == null) {
//            return 0;
//        }
//        return 1 + amount(node.left()) + amount(node.right());
//    }

    private int innerHeight(IntervalTreeNode<K,V> node) {
        if (node == null) {
            return -1;
        } else {
            return Math.max(innerHeight(node.left()), innerHeight(node.right())) + 1;
        }
    }

    private void breadth(IntervalTreeNode<K,V> node, Consumer<IntervalTreeNode<K,V>> consumer) {
        Queue<IntervalTreeNode<K,V>> queue = new LinkedBlockingQueue<>();
        if (node != null) {
            queue.add(node);
            while (!queue.isEmpty()) {
                node = queue.poll();
                // visit the node, i.e. perform the operation
                if (consumer != null) {
                    consumer.accept(node);
                }
                // enqueue the children from left to right
                if (node.left() != null) {
                    queue.add(node.left());
                }
                if (node.right() != null) {
                    queue.add(node.right());
                }
            }
        }
    }

    private void depth(IntervalTreeNode<K,V> node, Consumer<IntervalTreeNode<K,V>> consumer) {
        Stack<IntervalTreeNode<K,V>> stack = new Stack<>();
        if (node != null) {
            stack.push(node);
            while (!stack.empty()) {
                node = stack.pop();
                // visit the node, i.e. perform the operation
                if (consumer != null) {
                    consumer.accept(node);
                }
                // push the children from right to left
                if (node.right() != null) {
                    stack.push(node.right());
                }
                if (node.left() != null) {
                    stack.push(node.left());
                }
            }
        }
    }

    private void preorder(IntervalTreeNode<K,V> node, Consumer<IntervalTreeNode<K,V>> consumer) {
        // Root. Left. Right.
        if (node != null) {
            if (consumer != null) {
                consumer.accept(node);
            }
            preorder(node.left(), consumer);
            preorder(node.right(), consumer);
        }
    }

    private void inorder(IntervalTreeNode<K,V> node, Consumer<IntervalTreeNode<K,V>> consumer) {
        // Left. Root. Right.
        if (node != null) {
            inorder(node.left(),consumer);
            if (consumer != null) {
                consumer.accept(node);
            }
            inorder(node.right(),consumer);
        }
    }

    private void postorder(IntervalTreeNode<K,V> node, Consumer<IntervalTreeNode<K,V>> consumer) {
        // Left. Right. Root.
        if (node != null) {
            postorder(node.left(),consumer);
            postorder(node.right(),consumer);
            if (consumer != null) {
                consumer.accept(node);
            }
        }
    }

    /**
     * Inspired by Geeks for Geeks
     * @param low - starting point index
     * @param high - end point index
     * @param list - structure to be indexed
     */
//    private IntervalTreeNode<K,V> internalBalance(int low, int high, List<IntervalTreeNode<K,V>> list ) {
//        if (low > high) {
//            return null;
//        }
//        // get the middle element and make it root
//        int mid = (low + high) /2;
//        IntervalTreeNode<K,V> current = list.get(mid);
//        // recursively construct the left subtree and make it left child of root
//        current.setLeft(internalBalance(low, mid - 1, list ));
//        if (current.left() != null) {
//            current.left().setParent(current);
//        }
//        // recursively construct the right subtree and make it right child of the root
//        current.setRight(internalBalance(mid + 1, high, list));
//        if (current.right() != null) {
//            current.right().setParent(current);
//        }
//        return current;
//    }

    /**
     * This method exists to allow class implementers to specify the node for which to check the balance factor,
     * usually the node will be root, but for recursive tree rotations, such as LR-RL in the AVLTree, this
     * parameterization will be needed.
     * @return balance factor
     */
//    private int balanceFactor(IntervalTreeNode<K,V> node) {
//        int left_height = node.left() != null ? innerHeight(node.left()) : 0;
//        int right_height = node.right() != null ? innerHeight(node.right()) : 0;
//        return left_height - right_height;
//    }
//
    private boolean overlaps(IntervalTreeNode<K,V> a, IntervalTreeNode<K,V> b) {
        int left_comparison = a.getKey().compareTo((K) b.getValue());
        int right_comparison = a.getValue().compareTo((V) b.getKey());
        return left_comparison <= 0 && right_comparison >= 0;
    }

    ////////////////////////////////////////////
    // public api
    ////////////////////////////////////////////


    @Override
    public IntervalSearchTree<K, V> intervalSearcher(Tuple<K, V> interval) {
        IntervalSearchTree<K,V> nt = new IntervalSearchTree<>();
        K minKey = interval.x;
        V maxValue = interval.y;
        IntervalTreeNode<K,V> itr = root();
        IntervalTreeNode<K,V> ivl = new IntervalSearchTreeNode<>(interval.x, interval.y);
        // begin non-recursive traversal logic
        Stack<IntervalTreeNode<K,V>> stack = new Stack<>();
        while (itr != null || !stack.empty()) {
            // reach left most node if required
            while (itr != null) {
                stack.push(itr);
                int greaterThanMax = -1;
                if (itr.left() != null) {
                    greaterThanMax = itr.left().getMax().compareTo((V) ivl.getKey());
                }
                if(greaterThanMax >= 0) {
                    if (itr.right() != null) {
                        // grab non-overlapping nodes on the way.
                        nt.put(itr.right().getKey(), itr.right().getValue());
                    }
                    // only goes left if it needs to O(logn)
                    itr = itr.left();
                } else {
                    itr = null;
                }
            }
            // itr must be null at this point
            itr = stack.pop();
            // check root
            if (overlaps(itr, ivl)) {
                if (itr.getKey().compareTo(minKey) < 0) {
                    minKey = itr.getKey();
                }
                if (itr.getValue().compareTo(maxValue) > 0) {
                    maxValue = itr.getValue();
                }
            } else {
                // grab non-overlapping itrs on the way.
                nt.put(itr.getKey(),itr.getValue());
            }
            // we have visited the node and left subtree, now let's visit the right subtree
            // check right
            int lessThanMin = 1;
            if (itr.right() != null) {
                lessThanMin = itr.right().getMin().compareTo((K) ivl.getValue());
            }
            // check right
            if (lessThanMin <=0) {
                // grab non-overlapping itrs on the way.
                if (itr.left() != null) {
                    nt.put(itr.left().getKey(), itr.left().getValue());
                }
                // only goes right if it needs to O(logn)
                itr = itr.right();
            } else {
                itr = null;
            }
        }
        // end traversal logic
        // put keys in there stupid!
        nt.put(minKey, maxValue);
        return nt;
    }

    // Recursive Version

//    public Collection<Tuple<K, V>> intervalSearch(Tuple<K, V> interval) {
//        IntervalTreeNode<K,V> itr = root;
//        IntervalTreeNode<K,V> ttr = new IntervalSearchTreeNode<>(interval.x, interval.y);
//        IntervalSearchTree<K,V> nt = new IntervalSearchTree<>();
//        AtomicReference<K> minKey = new AtomicReference<>(ttr.getKey());
//        AtomicReference<V> maxValue = new AtomicReference<>(ttr.getValue());
//        intervalSearchHelper(itr, ttr, nt, minKey, maxValue);
//        nt.put(minKey.get(), maxValue.get());
//        nt.balance();
//        //////////////////////
//        return nt.entrySet().stream().map(e -> new Tuple<>(e.getKey(),e.getValue())).collect(Collectors.toList());
//    }
//
//    public void intervalSearchHelper(IntervalTreeNode<K,V> node, IntervalTreeNode<K,V> interval, IntervalSearchTree<K,V> nt, AtomicReference<K> minKey, AtomicReference<V> maxValue) {
//        if (node != null) {
//            int greaterThanMax = -1;
//            if (node.left() != null) {
//                greaterThanMax = node.left().getMax().compareTo((V) interval.getKey());
//            }
//            // check left
//            if(greaterThanMax >= 0) {
//                if (node.right() != null) {
//                    // grab non-overlapping nodes on the way.
//                    nt.put(node.right().getKey(), node.right().getValue());
//                }
//                intervalSearchHelper(node.left(), interval, nt, minKey, maxValue);
//            }
//            // check root
//            if (overlaps(node, interval)) {
//                if (node.getKey().compareTo(minKey.get()) < 0) {
//                    minKey.set(node.getKey());
//                }
//                if (node.getValue().compareTo(maxValue.get()) > 0) {
//                    maxValue.set(node.getValue());
//                }
//            } else {
//                // grab non-overlapping nodes on the way.
//                nt.put(node.getKey(),node.getValue());
//            }
//            int lessThanMin = 1;
//            if (node.right() != null) {
//                lessThanMin = node.right().getMin().compareTo((K) interval.getValue());
//            }
//            // check right
//            if (lessThanMin <=0) {
//                // grab non-overlapping nodes on the way.
//                if (node.left() != null) {
//                    nt.put(node.left().getKey(), node.left().getValue());
//                }
//                intervalSearchHelper(node.right(), interval, nt, minKey, maxValue);
//            }
//        }
//    }


//    @Override
//    public int size() {
//        return amount(root);
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return root == null;
//    }
//
//    @Override
//    public boolean containsKey(Object key) {
//        boolean found = false;
//        IntervalTreeNode<K,V> itr = root;
//        @SuppressWarnings("unchecked")
//        Comparable<? super K> k = (Comparable<? super K>) key;
//        while (!found && itr != null) {
//            int comparison = k.compareTo(itr.getKey());
//            if (comparison == 0) {
//                found = true;
//            }
//            if (comparison < 0) {
//                itr = itr.left();
//            } else if (comparison > 0) {
//                itr = itr.right();
//            }
//        }
//        return found;
//    }
//
//    @Override
//    public boolean containsValue(Object value) {
//        AtomicBoolean found = new AtomicBoolean(false);
//        traverser(root, TraversalType.PREORDER, (node) -> {
//            if (node.getValue() == value) found.set(true);
//        });
//        return found.get();
//    }

//    @Override
//    public V get(Object key) {
//        IntervalTreeNode<K,V> itr = root;
//        @SuppressWarnings("unchecked")
//        Comparable<? super K> k = (Comparable<? super K>) key;
//        while (itr != null) {
//            int comparison = k.compareTo(itr.getKey());
//            if (comparison < 0) {
//                itr = itr.left();
//            } else if (comparison > 0) {
//                itr = itr.right();
//            } else {
//                return itr.getValue();
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public V put(K key, V value) {
//        IntervalSearchTreeNode<K,V> node = new IntervalSearchTreeNode<>(key, value);
//        if (root == null) {
//            root = node;
//            return root.getValue();
//        }
//        boolean inserted = false;
//        IntervalTreeNode<K,V> itr = root;
//        V previous = null;
//        while (!inserted && itr != null) {
//            int comparison = key.compareTo(itr.getKey());
//            if (comparison < 0) {
//                if (itr.left() == null) {
//                    itr.setLeft(node);
//                    node.setParent(itr);
//                    inserted = true;
//                } else {
//                    itr = itr.left();
//                }
//            } else if( comparison > 0) {
//                if (itr.right() == null) {
//                    itr.setRight(node);
//                    node.setParent(itr);
//                    inserted = true;
//                } else {
//                    itr = itr.right();
//                }
//            } else {
//                // keep key, overwrite previous value
//                previous = itr.setValue(node.getValue());
//                inserted = true;
//            }
//        }
//        return previous;
//    }
//
//    @Override
//    public V remove(Object key) {
//        boolean found = false;
//        IntervalTreeNode<K,V> itr = root;
//        @SuppressWarnings("unchecked")
//        Comparable<? super K> k = (Comparable<? super K>) key;
//        while (!found && itr != null) {
//            int comparison = k.compareTo(itr.getKey());
//            if (comparison < 0) {
//                itr = itr.left();
//            } else if (comparison > 0) {
//                itr = itr.right();
//            }
//            // key object found
//            else {
//                found = true;
//                // special case for root
//                if (itr.isRoot()) {
//                    List<IntervalTreeNode<K,V>> list =  new ArrayList<>();
//                    traverser(root, TraversalType.INORDER, list::add);
//                    list.remove(root);
//                    this.root = null;
//                    internalBalance(0, list.size() - 1, list);
//                } else {
//                    IntervalTreeNode<K,V> parent = itr.parent();
//                    IntervalTreeNode<K,V> left = itr.left();
//                    IntervalTreeNode<K,V> right = itr.right();
//                    // re-attaching the child nodes to the node to be removed parent.
//                    if (right != null) {
//                        if (itr == parent.left()) {
//                            parent.setLeft(right);
//                        } else {
//                            parent.setRight(right);
//                        }
//                    } else {
//                        if (itr == parent.right()) {
//                            parent.setRight(left);
//                        } else {
//                            parent.setLeft(left);
//                        }
//                    }
//                }
//            }
//        }
//        return itr != null ? itr.getValue() : null;
//    }

//    @Override
//    public void putAll(Map<? extends K, ? extends V> m) {
//        for (Entry<? extends K, ? extends V> entry: m.entrySet()) {
//            this.put(entry.getKey(), entry.getValue());
//        }
//    }
//
//    @Override
//    public void clear() {
//        this.root = null;
//    }
//
//    @Override
//    public Set<K> keySet() {
//        Set<K> keys = new HashSet<>();
//        traverser(root, TraversalType.INORDER, (node) -> keys.add(node.getKey()));
//        return keys;
//    }
//
//    @Override
//    public Collection<V> values() {
//        Collection<V> values = new ArrayList<>();
//        traverser(root, TraversalType.INORDER, (node) -> values.add(node.getValue()) );
//        return values;
//    }

//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        Set<Entry<K,V>> entries = new HashSet<>();
//        traverser(root, TraversalType.INORDER, (node) -> entries.add(new Map.Entry<>() {
//            @Override
//            public K getKey() {
//                return node.getKey();
//            }
//
//            @Override
//            public V getValue() {
//                return node.getValue();
//            }
//
//            @Override
//            public V setValue(V value) {
//                return node.setValue(value);
//            }
//        }));
//        return entries;
//    }

//    @Override
//    public boolean isBalanced() {
//        int balance_factor = balanceFactor(root);
//        return -1 <= balance_factor && balance_factor <=1;
//    }
//
//    @Override
//    public void balance() {
//        List<IntervalTreeNode<K,V>> list =  new ArrayList<>();
//        traverser(root, TraversalType.INORDER, list::add);
//        this.root = internalBalance(0, list.size() - 1, list);
//    }

    @Override
    public int height() {
        return innerHeight(root());
    }

    @Override
    public V min() {
        IntervalTreeNode<K,V> itr = root();
        while (itr.left() != null) {
            itr = itr.left();
        }
        return itr.getValue();
    }

    @Override
    public V max() {
        IntervalTreeNode<K,V> itr = root();
        while (itr.right() != null) {
            itr = itr.right();
        }
        return itr.getValue();
    }

    @Override
    public V max(IntervalTreeNode<K, V> start) {
        IntervalTreeNode<K,V> itr = start;
        while (itr.right() != null) {
            itr = itr.right();
        }
        return itr.getValue();
    }

    /**
     * This solution basically makes use of the fact that since we are traversing the Tree anyway, we might as well
     * perform the operation at that time. The textbook's solution wastes time & space. When creating a reusable
     * iterator, it has to traverse the elements, then store the elements in a data structure. Effectively doubling the
     * memory footprint and time cost.
     * @param traversalType - The client gets to choose what type of traversal order they want.
     * @param consumer - The consumer function to be executed when a node is visited with respect to order.
     */
    @Override
    public void traverser(IntervalTreeNode<K,V> start, TraversalType traversalType, Consumer<IntervalTreeNode<K, V>> consumer ) {
        IntervalTreeNode<K,V> node = start != null ? start : root();
        switch (traversalType) {
            case BREADTH -> breadth(node, consumer);
            case DEPTH -> depth(node, consumer);
            case PREORDER -> preorder(node, consumer);
            case INORDER -> inorder(node, consumer);
            case POSTORDER -> postorder(node, consumer);
        }
    }

//
///////////////////////////////////////////////////////////////////////////////////
//    Candidate #1
//    ////////////////////////////////////////////////////////////////////////////////
//    public static List<Tuple<Integer,Integer>> appendInterval(Set<Tuple<Integer,Integer>> disjointed_set, Tuple<Integer,Integer> interval) {
//        // radix sort.
//        long start = System.currentTimeMillis();
//        List<Tuple<Integer,Integer>> sorted = new ArrayList<>(disjointed_set);
//        sorted.sort(Comparator.comparingInt(t -> t.x));
//        // Find min
//        Integer min = Integer.MAX_VALUE;
//        int min_index = -1;
//        for (int i = 0; i < sorted.size() - 1; i++) {
//            if (sorted.get(i).x < interval.x && sorted.get(i).y > interval.x) {
//                min = sorted.get(i).x;
//                min_index = i;
//                break;
//            }
//        }
//        // Find max
//        Integer max = Integer.MIN_VALUE;
//        int max_index = -1;
//        for (int i = sorted.size() - 1; i > 0 ; i--) {
//            if (sorted.get(i).y > interval.y && sorted.get(i).x < interval.y) {
//                max = sorted.get(i).y;
//                max_index = i;
//                break;
//            }
//        }
//        // Create new interval
//        Tuple<Integer,Integer> solution_interval = new Tuple<>(min, max);
//        int items = max_index - min_index + 1; // number of items to be removed
//        for (int i = 0, j = min_index; i < items; i++) {
//            sorted.remove(j);
//        }
//        sorted.add(min_index, solution_interval);
//        long end = System.currentTimeMillis();
//        System.out.printf("Time: %dms \n", end - start);
//        return sorted;
//    }
////////////////////////////////////////////////////////////////////////////////////////
//    Candidate #2
//    //////////////////////////////////////////////////////////////////////////////////////
//    public static Collection<Tuple<Integer,Integer>> appendInterval(Set<Tuple<Integer,Integer>> disjointed_set, Tuple<Integer,Integer> interval) {
//        TreeMap<Integer,Integer> tree = new TreeMap<>();
//        for (Tuple<Integer, Integer> t : disjointed_set) {
//            tree.put(t.x,t.y);
//        }
//        long start = System.currentTimeMillis();
//        // Candidate #1
//        // Find min
//        Integer min = tree.floorEntry(interval.x).getKey();
//        // Find max
//        Integer max = tree.floorEntry(interval.y).getValue();
//        // Create new interval
//        Tuple<Integer,Integer> solution_interval = new Tuple<>(min,max);
//        // take left and right trees
//        SortedMap<Integer,Integer> min_solution_map = tree.subMap(Integer.MIN_VALUE, min);
//        SortedMap<Integer,Integer> max_solution_map = tree.subMap(max, Integer.MAX_VALUE);
//        // Add to collection removing any overlaps
//        TreeMap<Integer,Integer> solution_map = new TreeMap<>();
//        solution_map.putAll(min_solution_map);
//        solution_map.put(solution_interval.x, solution_interval.y);
//        solution_map.putAll(max_solution_map);
//        long end = System.currentTimeMillis();
//        // Candidate #2
//        System.out.printf("Time: %dms \n", end - start);
//        return solution_map.entrySet().stream().map(e -> new Tuple<>(e.getKey(), e.getValue())).collect(Collectors.toList());
//    }
}
