package baekjoon.p21608;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int x = Integer.parseInt(br.readLine());
        int n = x * x;
        int[][] seats = new int[x+1][x+1];
        boolean[][] likes = new boolean[n+1][n+1];

        int[] dy = {1, -1, 0, 0};
        int[] dx = {0, 0, -1, 1};

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());
            for (int j = 0; j < 4; j++) {
                int friend = Integer.parseInt(st.nextToken());
                likes[student][friend] = true;
            }

            // 순환하며 최선의 자리 찾기
            int bestR = 0;
            int bestC = 0;
            int bestLikes = 0;
            int bestEmpty = 0;

            for (int j = 1; j <= x; j++) {
                for (int k = 1; k <= x; k++) {
                    if (seats[j][k] != 0) continue;
                    if (bestR == 0 && bestC == 0) {
                        bestR = j;
                        bestC = k;
                    }

                    int curLikes = 0;
                    int curEmpty = 0;

                    for (int l = 0; l < 4; l++) {
                        int newR = j + dy[l];
                        int newC = k + dx[l];

                        if (newR < 1 || newR > x || newC < 1 || newC > x) continue;
                        if (seats[newR][newC] == 0) curEmpty++;
                        else if (likes[student][seats[newR][newC]]) curLikes++;
                    }

                    if (curLikes > bestLikes) {
                        bestLikes = curLikes;
                        bestEmpty = curEmpty;
                        bestR = j;
                        bestC = k;
                    } else if (curLikes == bestLikes) {
                        if (curEmpty > bestEmpty) {
                            bestEmpty = curEmpty;
                            bestR = j;
                            bestC = k;
                        }
                    }
                }
            }

            seats[bestR][bestC] = student;
        }

        // 완성된 자리 기반으로 만족도 계산
        int score = 0;
        for (int i = 1; i <= x; i++) {
            for (int j = 1; j <= x; j++) {
                int stud = seats[i][j];
                int count = 0;

                for (int k = 0; k < 4; k++) {
                    int newR = i + dy[k];
                    int newC = j + dx[k];

                    if (newR < 1 || newR > x || newC < 1 || newC > x) continue;
                    if (likes[stud][seats[newR][newC]]) count++;
                }

                switch(count) {
                    case 0: score+=0; break;
                    case 1: score+=1; break;
                    case 2: score+=10; break;
                    case 3: score+=100; break;
                    case 4: score+=1000; break;
                }
            }
        }
        System.out.println(score);
    }
}
