public class CutRod {
    // data
    static int [] price_table = new int[]{0,1,5,8,9,10,17,17,20,24,30};

    // algorithms
    static int memoized_cut_rod(int[] p, int n) {
        int [] r = new int[n+1];
        for (int i = 0; i <= n; i++) {
            r[i] = Integer.MIN_VALUE;
        }
        return memoized_cut_rod_aux(p,n,r);
    }

    static int memoized_cut_rod_aux(int[] p, int n, int[] r) {
        if (r[n] >= 0) {
            return r[n];
        }
        int q;
        if (n==0) {
            q = 0;
        } else {
            q = Integer.MIN_VALUE;
        }
        for (int i = 1; i <= n; i++) {
            q = Math.max(q, p[i] + memoized_cut_rod_aux(p, n-i,r));
        }
        r[n] = q;
        return q;
    }

    static int bottom_up_cut_rod(int [] p, int n) {
        int [] r = new int[n+1];
        r[0] = 0;
        for (int j = 1; j <= n; j++) {
            int q = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) {
                q = Math.max(q, p[i] + r[j-i]);
                r[j] = q;
            }
        }
        return r[n];
    }

    public static void main(String[] args) {
//        System.out.println("Result: " + memoized_cut_rod(price_table, 4));
        System.out.println("Result: " + bottom_up_cut_rod(price_table, 4));
    }

}
