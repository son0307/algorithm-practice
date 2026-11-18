package swea.p4861;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int tc = 1; tc <= T; tc++) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            char[][] map = new char[N][N];
            for(int i = 0; i < N; i++) {
                String input = sc.next();
                for(int j = 0; j < N; j++) {
                    map[i][j] = input.charAt(j);
                }
            }

            boolean flag = false;
            String ans;

            // 가로
            for(int i = 0; i < N; i++) {
                for(int j = 0; j <= N - M; j++) {
                    boolean found = true;
                    StringBuilder sb = new StringBuilder();

                    for(int k = 0; k < M; k++) {
                        sb.append(map[i][j + k]);
                    }

                    for(int l = 0; l < M/2; l++) {
                        char c1 = sb.charAt(l);
                        char c2 = sb.charAt(M-1-l);

                        if(c1 != c2) {
                            found = false;
                            break;
                        }
                    }

                    if(found) {
                        flag = true;
                        System.out.println("#" + tc + " " + sb);
                    }
                }
            }

            // 세로
            if(!flag) {
                for(int i = 0; i < N; i++) {
                    for(int j = 0; j <= N - M; j++) {
                        boolean found = true;
                        StringBuilder sb = new StringBuilder();

                        for(int k = 0; k < M; k++) {
                            sb.append(map[j+k][i]);
                        }

                        for(int l = 0; l < M/2; l++) {
                            char c1 = sb.charAt(l);
                            char c2 = sb.charAt(M-1-l);

                            if(c1 != c2) {
                                found = false;
                                break;
                            }
                        }

                        if(found) {
                            System.out.println("#" + tc + " " + sb);
                        }
                    }
                }
            }
        }
    }
}
