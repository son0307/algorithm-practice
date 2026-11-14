package swea.p1244;

import java.util.*;

public class Solution {
    static int max;
    static HashSet<Integer>[] visited;

    static int getNum(int[] num) {
        int result = 0;

        for(int n : num) {
            result = result * 10 + n;
        }

        return result;
    }

    static void dfs(int[] num, int cur, int des) {
        if(cur == des) {
            max = Math.max(max, getNum(num));
            return;
        }

        for (int i = 0; i < num.length - 1; i++) {
            for(int j = i + 1; j < num.length; j++) {
                int[] tempNum = Arrays.copyOf(num, num.length);

                int temp = tempNum[i];
                tempNum[i] = tempNum[j];
                tempNum[j] = temp;

                int nextNum = getNum(tempNum);

                if (visited[cur + 1].contains(nextNum)) continue;

                visited[cur + 1].add(nextNum);

                dfs(tempNum, cur + 1, des);
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        for(int tc = 1; tc <= n; tc++) {
            max = 0;

            String[] numString = sc.next().split("");
            int[] num = new int[numString.length];
            for(int j = 0; j < num.length; j++)
                num[j] = Integer.parseInt(numString[j]);
            int cnt = sc.nextInt();

            visited = new HashSet[cnt + 1];
            for(int i = 0; i <= cnt; i++) {
                visited[i] = new HashSet<>();
            }

            dfs(num, 0, cnt);

            System.out.println("#" + tc + " " + max);
        }
    }
}
