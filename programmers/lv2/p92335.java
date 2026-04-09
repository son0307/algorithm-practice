package programmers.lv2;

public class p92335 {
    public boolean isPrime(long n) {
        if (n < 2)
            return false;

        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    public int solution(int n, int k) {
        int answer = 0;
        String[] nums = Integer.toString(n,k).split("0");

        for (String num : nums) {
            if (num.isEmpty())
                continue;

            if (isPrime(Long.parseLong(num))) {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int n = 88573;
        int k = 3;
        System.out.println(new p92335().solution(n, k));
    }
}
