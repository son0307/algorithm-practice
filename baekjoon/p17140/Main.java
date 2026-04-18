package baekjoon.p17140;

import java.io.*;
import java.util.*;

class Count implements Comparable<Count>{
    int n;
    int cnt;

    public Count(int n, int cnt) {
        this.n = n;
        this.cnt = cnt;
    }

    @Override
    public int compareTo(Count c1) {
        if (this.cnt == c1.cnt) {
            return this.n - c1.n;
        }
        return this.cnt - c1.cnt;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[][] arr = new int[101][101];
        int rowLength = 3;
        int colLength = 3;
        for (int i = 1; i <= rowLength; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= colLength; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int time = 0;
        while (time >= 0) {
            if (arr[r][c] == k)
                break;

            // R 연산
            if (rowLength >= colLength) {
                int maxColLength = 0;

                for (int i = 1; i <= rowLength; i++) {
                    Map<Integer, Integer> map = new HashMap<>();

                    // 1. 카운트 (0은 무시)
                    for (int j = 1; j <= colLength; j++) {
                        if (arr[i][j] == 0) continue;
                        map.put(arr[i][j], map.getOrDefault(arr[i][j], 0) + 1);
                    }

                    // 2. ArrayList에 넣고 정렬
                    List<Count> list = new ArrayList<>();
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        list.add(new Count(entry.getKey(), entry.getValue()));
                    }
                    Collections.sort(list);

                    // 3. 배열에 다시 채우기
                    int idx = 1;
                    for (Count count : list) {
                        arr[i][idx++] = count.n;
                        arr[i][idx++] = count.cnt;
                        if (idx > 100) break;
                    }

                    maxColLength = Math.max(maxColLength, idx - 1);

                    // 4. 남은 자리는 0으로 채우기
                    while (idx <= 100)
                        arr[i][idx++] = 0;
                }
                colLength = maxColLength;
            }
            // C 연산
            else {
                int maxRowLength = 0;

                for (int i = 1; i <= colLength; i++) {
                    Map<Integer, Integer> map = new HashMap<>();

                    // 1. 카운트 (0은 무시)
                    for (int j = 1; j <= rowLength; j++) {
                        if (arr[j][i] == 0) continue;
                        map.put(arr[j][i], map.getOrDefault(arr[j][i], 0) + 1);
                    }

                    // 2. ArrayList에 넣고 정렬
                    List<Count> list = new ArrayList<>();
                    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                        list.add(new Count(entry.getKey(), entry.getValue()));
                    }
                    Collections.sort(list);

                    // 3. 배열에 다시 채우기
                    int idx = 1;
                    for (Count count : list) {
                        arr[idx++][i] = count.n;
                        arr[idx++][i] = count.cnt;
                        if (idx > 100) break;
                    }

                    maxRowLength = Math.max(maxRowLength, idx - 1);

                    // 4. 남은 자리는 0으로 채우기
                    while (idx <= 100)
                        arr[idx++][i] = 0;
                }
                rowLength = maxRowLength;
            }

            time++;

            if (time > 100)
                time = -1;
        }

        System.out.println(time);
    }
}
