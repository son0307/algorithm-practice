package codetree.simulation01.tromino;

import java.io.*;
import java.util.*;

public class Main {
    static final int[][][] BLOCKS = {
            {{0, 0}, {0, 1}, {-1, 0}},  // L자 1
            {{0, 0}, {0, 1}, {1, 0}},   // L자 2
            {{0, 0}, {0, -1}, {1, 0}},  // L자 3
            {{0, 0}, {0, -1}, {-1, 0}}, // L자 4
            {{0, 0}, {0, -1}, {0, 1}},  // 일자 (가로)
            {{0, 0}, {-1, 0}, {1, 0}}   // 일자 (세로)
    };

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] map = new int[n][m];

        for (int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = 0;

        // 완전 탐색
        for (int i=0; i<n; i++) {
            for (int j=0; j<m; j++) {

                // 6가지 블록 형태 덮어보기
                for (int k=0; k<6; k++) {
                    int sum = 0;
                    boolean isPossible = true;

                    for (int l=0; l<3; l++) {
                        int ny = i + BLOCKS[k][l][0];
                        int nx = j + BLOCKS[k][l][1];

                        // 범위 벗어나면 무효
                        if (ny < 0 || ny >= n || nx < 0 || nx >= m) {
                            isPossible = false;
                            break;
                        }
                        sum += map[ny][nx];
                    }

                    if (isPossible)
                        max = Math.max(max, sum);
                }
            }
        }

        System.out.println(max);
    }
}
