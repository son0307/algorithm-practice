package codetree.simulation01.bestPlaceOf33;

import java.io.*;
import java.util.*;

public class Main {
    static int[][] map;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int max = 0;

        int n = Integer.parseInt(st.nextToken());
        map = new int[n][n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for (int i = 0; i < n-2; i++) {
            for (int j = 0; j < n-2; j++) {
                max = Math.max(max, search(i, j));
            }
        }

        System.out.println(max);
    }

    static int search(int y, int x) {
        int count = 0;

        for (int i = y; i <= y + 2; i++) {
            for (int j = x; j <= x + 2; j++) {
                if (map[i][j] == 1) count++;
            }
        }

        return count;
    }
}
