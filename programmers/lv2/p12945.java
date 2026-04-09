package programmers.lv2;

public class p12945 {
    public long solution(int n) {
        long[] dp = new long [n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++)
            dp[i] = (dp[i-1] + dp[i-2]) % 1234567;

        return dp[n];
    }

    public static void main(String[] args) {
        p12945 p = new p12945();
        System.out.println(p.solution(10));
    }
}
