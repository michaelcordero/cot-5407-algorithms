class CutRodChatGPT {

    public static int maxProfit(int[] prices, int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            int maxPrice = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                maxPrice = Math.max(maxPrice, prices[j - 1] + dp[i - j]);
            }
            dp[i] = maxPrice;
        }

        return dp[n];
    }


    public static void main(String[] args) {
        int length = 4;
        int [] prices = new int[]{ 2, 5, 7, 8};
        System.out.println("Max profit for rod of length: " + length + " = " + maxProfit(prices, length));
    }
}
