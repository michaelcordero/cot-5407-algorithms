import java.util.Arrays;

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

    static int minCost(int n, int[] cuts) {
        int[] A = new int[cuts.length + 2];
        System.arraycopy(cuts, 0, A, 1, cuts.length);
        A[0] = 0;
        A[A.length - 1] = n;

        Arrays.sort(A);

        // dp[i][j] := minCost(cuts[i..j])
        int[][] dp = new int[A.length][A.length];

        for (int d = 2; d < A.length; ++d)
            for (int i = 0; i + d < A.length; ++i) {
                final int j = i + d;
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; ++k)
                    dp[i][j] = Math.min(dp[i][j], A[j] - A[i] + dp[i][k] + dp[k][j]);
            }

        return dp[0][A.length - 1];
    }

    public static void main(String[] args) {
//        System.out.println("Result: " + memoized_cut_rod(price_table, 4));
//        System.out.println("Result: " + bottom_up_cut_rod(price_table, 4));
        System.out.println("Result: " + minCost(7, new int[]{1,3,4,5}));
    }

}
