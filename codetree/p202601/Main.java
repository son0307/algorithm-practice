package codetree.p202601;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

class Turtle {
    int c;
    int r;
    boolean alive;

    public Turtle(int r, int c) {
        this.r = r;
        this.c = c;
        this.alive = true;
    }
}

class Volcano {
    int c;
    int r;
    int capacity;
    int pressure;

    public Volcano(int r, int c, int capacity) {
        this.c = c;
        this.r = r;
        this.capacity = capacity;
        this.pressure = 0;
    }
}

public class Main {
    static int[] dR = {0, 1, 0, -1};
    static int[] dC = {1, 0, -1, 0};

    // 현재 거북이 위치에서 (N-1, N-1)까지 이동하는 거리 탐색
    public int[][] makeDistanceMap(int[][] map, int curR, int curC) {
        int N = map.length;
        int[][] distanceMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(distanceMap[i], -1);
        }

        distanceMap[N-1][N-1] = 0;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{N-1, N-1});

        while (!q.isEmpty()) {
            int[] current = q.poll();
            int c = current[0];
            int r = current[1];

            for (int i = 0; i < 4; i++) {
                int nC = c + dC[i];
                int nR = r + dR[i];

                if (nC < 0 || nR < 0 || nC >= N || nR >= N) continue;
                if (distanceMap[nR][nC] != -1) continue;
                if (map[nR][nC] != 0 && !(nR == curR && nC == curC)) continue;

                distanceMap[nR][nC] = distanceMap[r][c] + 1;
                q.add(new int[]{nC, nR});
            }
        }

        return distanceMap;
    }

    // 경로들 중 우측부터 살펴보며 경로 선택 후 이동
    public int[] moveTurtle(int[][] distanceMap, int curR, int curC) {
        if (distanceMap[curR][curC] != -1) {
            int N = distanceMap.length;
            for (int i = 0; i < 4; i++) {
                int nR = curR + dR[i];
                int nC = curC + dC[i];

                if (nC < 0 || nR < 0 || nC >= N || nR >= N) continue;

                if (distanceMap[curR][curC] - distanceMap[nR][nC] == 1)
                    return new int[]{nR, nC};
            }
        }
        return new int[]{curR, curC};
    }

    // 열기 맵 만들기
    public int[][] makeHeatMap(int N, int volcanoNum, Volcano[] volcanoes) {
        int[][] heatMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(heatMap[i], 0);
        }
        Queue<Volcano> qv = new LinkedList<>();
        boolean[] exploded = new boolean[volcanoNum];

        for (int i = 0; i < volcanoNum; i++) {
            if (volcanoes[i].pressure >= volcanoes[i].capacity) {
                qv.add(volcanoes[i]);
                exploded[i] = true;
            }
        }

        while (!qv.isEmpty()) {
            Volcano v = qv.poll();
            heatMap[v.r][v.c] += v.capacity;
            for (int i = 1; i < N; i++) {
                for (int j = 0; j < 4; j++) {
                    int nR = v.r + dR[j] * i;
                    int nC = v.c + dC[j] * i;
                    if (nR < 0 || nR >= N || nC < 0 || nC >= N) continue;

                    
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        Main m = new Main();
        int count = 0;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int turtleNum = Integer.parseInt(st.nextToken());
        int volcanoNum = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] result = new int[turtleNum];
        Arrays.fill(result, -1);
        Turtle[] turtles = new Turtle[turtleNum];
        for (int i = 0; i < turtleNum; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            turtles[i] = new Turtle(r, c);
        }

        Volcano[] volcanos = new Volcano[volcanoNum];
        for (int i = 0; i < volcanoNum; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int capacity = Integer.parseInt(st.nextToken());
            volcanos[i] = new Volcano(r, c, capacity);
        }

        while (count < 100) {
            count++;

            // 1단계. 거북이 이동
            for (int i = 0; i < turtleNum; i++) {
                Turtle t = turtles[i];
                if (!t.alive) continue;
                int[][] distanceMap = m.makeDistanceMap(map, t.r, t.c);
                int[] moved = m.moveTurtle(distanceMap, t.r, t.c);

                map[t.r][t.c] = 0;
                if(moved[0] == N-1 && moved[1] == N-1) {
                    result[i] = count;
                    map[moved[0]][moved[1]] = 0;
                    t.alive = false;
                } else {
                    map[moved[0]][moved[1]] = 2;
                    t.r = moved[0];
                    t.c = moved[1];
                }
            }

            // 2단계. 압력 증가
            for (int i = 0; i < volcanoNum; i++) {
                volcanos[i].pressure += 10;
            }

            // 3단계. 분출 및 연쇄 반응



        }
    }
}
