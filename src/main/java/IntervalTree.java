import java.util.Map;
import java.util.function.Consumer;

public interface IntervalTree<K extends Comparable<K>,V extends Comparable<V>> extends Map<K,V> {
    ///////////////////////////////////////////////
    // inner node class
    //////////////////////////////////////////////
    interface IntervalTreeNode<K,V> extends Entry<K,V> {
        // dutiful methods
        IntervalTreeNode<K,V> parent();
        IntervalTreeNode<K,V> left();
        IntervalTreeNode<K,V> right();
        // setters
        void setLeft(IntervalTreeNode<K,V> left);
        void setRight(IntervalTreeNode<K,V> right);
        void setParent(IntervalTreeNode<K,V> parent);
        void setMax(V value);
        // getters
        V getMax();
        K getMin();
        // quality of life methods
        default boolean isSingleParent() {
            return left() != null ^ right() != null;
        }
        default boolean isParent() {return left() != null || right() != null;}
        default boolean isChild() {
            return parent() != null;
        }
        default boolean isRoot() {
            return parent() == null;
        }
        default boolean isLeaf() {
            return left() == null && right() == null;
        }
    }

    ///////////////////////////////////////////////
    // traversal types enumeration
    //////////////////////////////////////////////
    enum TraversalType {
        BREADTH, DEPTH, PREORDER, INORDER, POSTORDER
    }
    ///////////////////////////////////////////////
    // definition methods
    ///////////////////////////////////////////////
//    boolean isBalanced();
//    void balance();
    int height();
    V min();
    V max();
    V max(IntervalTreeNode<K,V> start);
    void traverser(IntervalTreeNode<K,V> start, TraversalType traversalType, Consumer<IntervalTreeNode<K, V>> function);

    /////////////////////////////////////////////
    // COT 5407
    ////////////////////////////////////////////
    IntervalSearchTree<K,V> intervalSearcher(Tuple<K,V> interval);

}
