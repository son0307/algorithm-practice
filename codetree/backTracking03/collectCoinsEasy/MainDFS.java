package codetree.backTracking03.collectCoinsEasy;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MainDFS {
    static int[] start = new int[2];
    static int[] end = new int[2];
    static int min = Integer.MAX_VALUE;

    static HashMap<Integer, int[]> coins = new HashMap<>();
    static List<Integer> sortedCoinKeys = new ArrayList<>();
    static int[] selectedCoins = new int[3];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            for (int j = 0; j < n; j++) {
                char c = line.charAt(j);

                if (c == 'S') start = new int[]{i, j};
                else if (c == 'E') end = new int[]{i, j};
                else if (c != '.') coins.put(c - '0', new int[]{i, j});
            }
        }

        // 코인 수가 3개 미만이면 해결 불가
        if (coins.size() < 3) {
            System.out.println(-1);
            return;
        }

        // 동전 번호 오름차순 정렬
        sortedCoinKeys = coins.keySet().stream().sorted().collect(Collectors.toList());

        dfs(0, 0);

        System.out.println(min);
    }

    static void dfs(int cnt, int idx) {
        if (cnt == 3) {
            int totalDistance = 0;
            totalDistance += getDistance(start, coins.get(selectedCoins[0]));
            totalDistance += getDistance(coins.get(selectedCoins[0]), coins.get(selectedCoins[1]));
            totalDistance += getDistance(coins.get(selectedCoins[1]), coins.get(selectedCoins[2]));
            totalDistance += getDistance(coins.get(selectedCoins[2]), end);

            min = Math.min(min, totalDistance);
            return;
        }

        for (int i = idx; i < sortedCoinKeys.size(); i++) {
            selectedCoins[cnt] = sortedCoinKeys.get(i);
            dfs(cnt + 1, i + 1);
        }
    }

    static int getDistance(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
}
