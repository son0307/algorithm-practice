package baekjoon.p2902;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 이름 입력
        String fullName = sc.next();

        // 이름 분리
        String[] names = fullName.split("-");

        // 맨 첫번째 글자만 출력
        for(String name : names) {
            System.out.print(name.charAt(0));
        }
    }
}
