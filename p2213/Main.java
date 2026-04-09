package p2213;

import java.io.*;
import java.util.*;

public class Main {

    static HashMap<Integer, List<Integer>> child_nodes;
    static int[] weight;
    static int[][] temp;
    static boolean[] visited;
    static List<Integer> result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        weight = new int[n+1];
        child_nodes = new HashMap<>();
        temp = new int[n+1][2];
        visited = new boolean[n+1];
        result = new ArrayList<>();
        for (int i = 0; i < n+1; i++) {
            child_nodes.put(i, new ArrayList<>());
        }

        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i < n+1; i++) {
            weight[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 0; i < n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            child_nodes.get(a).add(b);
            child_nodes.get(b).add(a);
        }

        travel(1);

        if (temp[1][0] < temp [1][1]) {
            System.out.println(temp[1][1]);
            trace(1, true);
        } else {
            System.out.println(temp[1][0]);
            trace(1, false);
        }

        Collections.sort(result);
        for (int ans : result) {
            System.out.print(ans + " ");
        }
    }

    private static void travel(int pos) {
        temp[pos][0] = 0;
        temp[pos][1] = weight[pos];

        if (child_nodes.get(pos).isEmpty()) return;

        visited[pos] = true;

        for (Integer child : child_nodes.get(pos)) {
            if (!visited[child]) {
                travel(child);

                if (temp[child][0] > temp[child][1]) {
                    temp[pos][0] += temp[child][0];
                } else {
                    temp[pos][0] += temp[child][1];
                }
                temp[pos][1] += temp[child][0];
            }
        }
        visited[pos] = false;
    }

    private static void trace(int pos, boolean contain) {
        visited[pos] = true;

        if (contain) {
            result.add(pos);
            for (Integer child : child_nodes.get(pos)) {
                if (!visited[child]) {
                    trace(child, false);
                }
            }
        }
        else {
            for (Integer child : child_nodes.get(pos)) {
                if (!visited[child]) {
                    if (temp[child][0] > temp[child][1]) {
                        trace(child, false);
                    } else {
                        trace(child, true);
                    }
                }
            }
        }

        visited[pos] = false;
    }
}
