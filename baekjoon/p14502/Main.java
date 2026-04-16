package baekjoon.p14502;

import java.io.*;
import java.util.*;

class Virus {
    int x;
    int y;

    public Virus(int y, int x) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
    int max_safe = Integer.MIN_VALUE;

    public void dfs(int[][] field, Queue<Virus> queue, int lastPos, int cnt) {
        // 벽을 3개 다 세운 경우
        if (cnt == 3) {
            Queue<Virus> q = new LinkedList<>(queue);
            int[][] f = new int[field.length][field[0].length];

            // 연구소 맵 깊은 복사
            for (int i = 0; i < field.length; i++)
                f[i] = Arrays.copyOf(field[i], field[i].length);

            int[] dy = {-1, 1, 0, 0};
            int[] dx = {0, 0, -1, 1};

            // BFS를 통한 바이러스 확산 시뮬레이션
            while(!q.isEmpty()) {
                Virus virus = q.poll();
                for (int i = 0; i < 4; i++) {
                    int newY = virus.y + dy[i];
                    int newX = virus.x + dx[i];

                    if (newY < 0 || newY >= f.length || newX < 0 || newX >= f[0].length) continue;
                    if (f[newY][newX] == 0) {
                        f[newY][newX] = 2;
                        q.add(new Virus(newY, newX));
                    }
                }
            }

            // 안전 영역(0) 개수 카운트
            int cur_safe = 0;
            for (int i = 0; i < f.length; i++) {
                for (int j = 0; j < f[i].length; j++) {
                    if (f[i][j] == 0) cur_safe++;
                }
            }

            // 최댓값 갱신
            max_safe = Math.max(cur_safe, max_safe);
            return;
        }

        // 1차원 인덱싱을 통한 조합 탐색
        for (int i = lastPos; i < field.length * field[0].length; i++) {
            int y = i / field[0].length;
            int x = i % field[0].length;

            if (field[y][x] == 0) {
                field[y][x] = 1;
                dfs(field, queue, i + 1, cnt + 1);
                field[y][x] = 0;
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] field = new int[n][m];
        Queue<Virus> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                field[i][j] = Integer.parseInt(st.nextToken());

                // 초기 바이러스들의 위치 저장
                if (field[i][j] == 2)
                    q.add(new Virus(i, j));
            }
        }

        Main main = new Main();
        main.dfs(field, q, 0, 0);

        System.out.println(main.max_safe);
    }
}
