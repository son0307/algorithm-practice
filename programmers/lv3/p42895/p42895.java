package programmers.lv3.p42895;

import java.util.*;

public class p42895 {
    public int solution(int N, int number) {
        HashSet<Integer>[] dp = new HashSet[9];

        // N 1개로 해결되는 경우
        if(number == N) return 1;
        dp[1] = new HashSet<>();
        dp[1].add(N);

        // N을 2개 이상 사용해야 하는 경우
        for(int i = 2; i < 9; i++) {
            dp[i] = new HashSet<>();

            // 'NN'과 같은 경우 검사
            int temp = 0;
            for(int j = 0; j < i; j++) {
                temp = temp * 10 + N;
            }
            if (temp == number) return i;
            dp[i].add(temp);

            // j개를 사용하는 경우, i - j개를 사용하는 경우를 가져와서 사칙연산
            for(int j = 1; j < i; j++) {
                HashSet<Integer> s1 = dp[j];
                HashSet<Integer> s2 = dp[i - j];

                for(int i1 : s1) {
                    for (int i2 : s2) {
                        dp[i].add(i1 + i2);
                        dp[i].add(i1 - i2);
                        dp[i].add(i1 * i2);

                        if(i2 != 0) {
                            dp[i].add(i1 / i2);
                        }
                    }
                }

                if(dp[i].contains(number)) return i;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        p42895 p = new p42895();
        int N1 = 5;
        int number1 = 5;
        int N2 = 2;
        int number2 = 11;
        System.out.println(p.solution(N1, number1));
        System.out.println(p.solution(N2, number2));
    }
}
