package swea.p1216;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for(int t = 1; t <= 10; t++) {
            int tc = sc.nextInt();

            char[][] map = new char[100][100];
            for(int i = 0; i < 100; i++) {
                String s = sc.next();
                for(int j = 0; j < 100; j++) {
                    map[i][j] = s.charAt(j);
                }
            }

            int maxLength = 1;

            for(int len = 2; len <= 100; len++) {
                boolean found = false;

                // 가로
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j <= 100 - len; j++) {
                        boolean isValid = true;

                        for (int k = 0; k < len / 2; k++) {
                            if (map[i][j + k] != map[i][j + (len - k - 1)]) {
                                isValid = false;
                                break;
                            }
                        }

                        if (isValid) found = true;
                    }
                }

                // 세로
                for (int i = 0; i < 100; i++) {
                    for (int j = 0; j <= 100 - len; j++) {
                        boolean isValid = true;

                        for (int k = 0; k < len / 2; k++) {
                            if (map[j + k][i] != map[j + (len - k - 1)][i]) {
                                isValid = false;
                                break;
                            }
                        }

                        if (isValid) found = true;
                    }
                }

                if(found) maxLength = len;
            }

            System.out.println("#" + tc + " " + maxLength);
        }
    }
}
