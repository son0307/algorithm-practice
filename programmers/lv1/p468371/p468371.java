package programmers.lv1.p468371;

public class p468371 {
    public static int gcd(int a, int b) {
        while (b > 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    public static int solution(int[][] signals) {
        int time = 1;

        int[] totals = new int[signals.length];
        for (int i = 0; i < totals.length; i++) {
            for (int j = 0; j < 3; j++)
                totals[i] += signals[i][j];
        }

        int totalLcm = totals[0];
        for (int i = 1; i < totals.length; i++) {
            totalLcm = lcm(totalLcm, totals[i]);
        }

        while (time <= totalLcm) {
            boolean flag = true;

            for (int i = 0; i < totals.length; i++) {
                int timing = time % totals[i];
                if (timing <= signals[i][0] || timing > signals[i][0] + signals[i][1]) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return time;
            }

            time++;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] signals = {{1,1,4}, {2,1,3}, {3,1,2}, {4,1,1}};
        System.out.println(solution(signals));
    }
}
