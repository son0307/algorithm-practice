package swea.p1215;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        for(int tc = 1; tc <= 10; tc++) {
            int len = sc.nextInt();
            char[][] map = new char[8][8];
            for(int i = 0; i < 8; i++) {
                String s = sc.next();
                for(int j = 0; j < 8; j++) {
                    map[i][j] = s.charAt(j);
                }
            }

            int count = 0;

            // 가로
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j <= 8 - len; j++) {
                    boolean isValid = true;

                    for(int k = 0; k < len / 2; k++) {
                        if(map[i][j+k] != map[i][j+(len-k-1)]) {
                            isValid = false;
                            break;
                        }
                    }

                    if(isValid) count++;
                }
            }

            // 세로
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j <= 8 - len; j++) {
                    boolean isValid = true;

                    for(int k = 0; k < len / 2; k++) {
                        if(map[j+k][i] != map[j+(len-k-1)][i]) {
                            isValid = false;
                            break;
                        }
                    }

                    if(isValid) count++;
                }
            }

            System.out.println("#" + tc + " " + count);
        }
    }
}
