public class Tuple<X,Y> implements Comparable<Tuple<X,Y>> {
    public final X x;
    public final Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Tuple{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }


    @Override
    public int compareTo(Tuple<X, Y> o) {
        return ((Integer)this.x).compareTo((Integer) o.x);
    }
}
