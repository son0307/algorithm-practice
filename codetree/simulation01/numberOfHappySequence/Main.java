package codetree.simulation01.numberOfHappySequence;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][n];
        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;

        // 행 탐색
        for (int i=0; i<n; i++) {
            int cnt = 0, lastNum = 0;
            for (int j=0; j<n; j++) {
                if (map[i][j] != lastNum) {
                    cnt = 0;
                    lastNum = map[i][j];
                }

                cnt++;

                if (cnt >= m) {
                    ans++;
                    break;
                }
            }
        }

        // 열 탐색
        for (int i=0; i<n; i++) {
            int cnt = 0, lastNum = 0;
            for (int j=0; j<n; j++) {
                if (map[j][i] != lastNum) {
                    cnt = 0;
                    lastNum = map[j][i];
                }

                cnt++;

                if (cnt >= m) {
                    ans++;break;
                }
            }
        }

        System.out.println(ans);
    }
}
