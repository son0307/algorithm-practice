package codetree.p2024m1.The2DWindBlows;

import java.util.*;

public class Main {
    public static int[][] building = new int[100][100];
    public static int[][] queries = new int[100][4];
    public static int n;
    public static int m;
    public static int q;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        m = sc.nextInt();
        q = sc.nextInt();

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                building[i][j] = sc.nextInt();

        for (int i = 0; i < q; i++)
            for (int j = 0; j < 4; j++)
                queries[i][j] = sc.nextInt() - 1;

        for (int i = 0; i < q; i++) {
            rotate(i);
            getAverage(i);
        }

        for(int j = 0; j < n; j++) {
            for(int k = 0; k < m; k++) {
                System.out.print(building[j][k] + " ");
            }
            System.out.println();
        }
    }

    public static void rotate(int num) {
        int y1 = queries[num][0], x1 = queries[num][1];
        int y2 = queries[num][2], x2 = queries[num][3];

        int temp = building[y1][x1];

        for(int y = y1; y < y2; y++) {
            building[y][x1] = building[y+1][x1];
        }

        for(int x = x1; x < x2; x++) {
            building[y2][x] = building[y2][x+1];
        }

        for(int y = y2; y > y1; y--) {
            building[y][x2] = building[y-1][x2];
        }

        for(int x = x2; x > x1 + 1; x--) {
            building[y1][x] = building[y1][x-1];
        }

        building[y1][x1 + 1] = temp;
    }

    public static void getAverage(int num) {
        int[][] temp_arr = new int[n][m];
        int[] dy = new int[]{1, -1, 0, 0};
        int[] dx = new int[]{0, 0, 1, -1};
        int y1 = queries[num][0], x1 = queries[num][1];
        int y2 = queries[num][2], x2 = queries[num][3];

        for (int y = y1; y <= y2; y++) {
            for(int x = x1; x <= x2; x++) {
                int sum = building[y][x];
                int cnt = 1;
                for (int i = 0; i < 4; i++) {
                    int ny = y + dy[i];
                    int nx = x + dx[i];
                    if(ny < 0 || ny >= n || nx < 0 || nx >= m) continue;
                    sum += building[ny][nx];
                    cnt++;
                }
                temp_arr[y][x] = sum / cnt;
            }
        }

        for(int y = y1; y <= y2; y++)
            for(int x = x1; x <= x2; x++)
                building[y][x] = temp_arr[y][x];
    }
}
