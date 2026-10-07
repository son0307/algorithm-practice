package codetree.p2025_2m1.TotalWidthOfARectangle2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[][] map = new int[201][201];
        int n = Integer.parseInt(st.nextToken());

        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken()) + 100, y1 = Integer.parseInt(st.nextToken()) + 100;
            int x2 = Integer.parseInt(st.nextToken()) + 100, y2 = Integer.parseInt(st.nextToken()) + 100;

            for(int x = x1; x < x2; x++) {
                for(int y = y1; y < y2; y++) {
                    map[x][y] = 1;
                }
            }
        }

        int count = 0;
        for(int i = 0 ; i < 200; i++)
            for(int j = 0; j < 200; j++)
                if(map[i][j] == 1) count++;

        System.out.println(count);
    }
}
