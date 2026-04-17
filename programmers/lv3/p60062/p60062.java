package programmers.lv3.p60062;

import java.util.*;

public class p60062 {
    private static List<List<Integer>> permutation = new ArrayList<List<Integer>>();
    private int answer = Integer.MAX_VALUE;

    void makePermutation(int cnt, int[] dist, boolean[] visited, List<Integer> current) {
        if (current.size() == dist.length) {
            permutation.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                current.add(dist[i]);
                makePermutation(cnt + 1, dist, visited, current);
                visited[i] = false;
                current.remove(current.size() - 1);
            }
        }
    }

    public int solution(int n, int[] weak, int[] dist) {
        int[] weakAppend = new int[weak.length * 2];
        for (int i = 0; i < weakAppend.length; i++) {
            if (i < weak.length) weakAppend[i] = weak[i];
            else weakAppend[i] = weak[i - weak.length] + n;
        }

        boolean[] visited = new boolean[dist.length];
        List<Integer> tempArray = new ArrayList<>();
        makePermutation(0, dist, visited, tempArray);

        for(List<Integer> friends:permutation) {
            for (int start = 0; start < weak.length; start++) {
                int cursor = start;
                int friendCount = 0;

                for(int friend:friends) {
                    friendCount++;
                    int maxCover = weakAppend[cursor] + friend;
                    while (cursor < start + weak.length && weakAppend[cursor] <= maxCover) {
                        cursor++;
                    }
                    if (cursor >= start + weak.length) {
                        answer = Math.min(answer, friendCount);
                        break;
                    }
                }
            }
        }

        if (answer == Integer.MAX_VALUE) return -1;
        return answer;
    }

    public static void main(String[] args) {
        int n = 12;
        int[] weak = {1, 5, 6, 10};
        int[] dist = {1, 2, 3, 4};
        System.out.println(new p60062().solution(n, weak, dist));
    }
}
