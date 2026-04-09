package baekjoon.p2455;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int person = 0;
        int max = 0;

        for(int i = 0; i < 4; i++) {
            // 내린 사람, 탄 사람
            int out = sc.nextInt();
            int in = sc.nextInt();

            // 현재 기차에 타고 있는 사람
            person += (in - out);

            // 최댓값 갱신
            if (max < person) {
                max = person;
            }
        }

        System.out.println(max);
    }
}
