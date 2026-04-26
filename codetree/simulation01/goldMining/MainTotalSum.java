package codetree.simulation01.goldMining;

import java.util.*;
import java.io.*;

public class MainTotalSum {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 중심점으로부터 거리별 금 개수 저장
                int maxDist = 2 * (n-1);
                int[] goldAtDist = new int[maxDist + 1];

                // 맵을 순회하며 금의 거리 계산
                for (int r = 0; r < n; r++) {
                    for (int c = 0; c < n; c++) {
                        if (map[r][c] == 1) {
                            int dist = Math.abs(r - i) + Math.abs(c - j);
                            goldAtDist[dist]++;
                        }
                    }
                }

                // 마름모 크기를 늘리면서 검사
                int currentGold = 0;
                for (int k = 0; k <= maxDist; k++) {
                    currentGold += goldAtDist[k];

                    int cost = k * k + (k+1) * (k+1);
                    int profit = currentGold * m;

                    if (profit >= cost)
                        max = Math.max(max, currentGold);
                }
            }
        }

        System.out.println(max);
    }
}
