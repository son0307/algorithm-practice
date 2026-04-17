package programmers.lv3.p92343;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class p92343 {

    private static class Node {
        int num, sheep, wolf;
        HashSet<Integer> adjacent;

        public Node(int num, int sheep, int wolf, HashSet<Integer> adjacent) {
            this.num = num;
            this.sheep = sheep;
            this.wolf = wolf;
            this.adjacent = adjacent;
        }
    }

    public int solution(int[] info, int[][] edges) {
        int answer = 0;

        ArrayList<Integer>[] tree = new ArrayList[info.length];
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            tree[edge[0]].add(edge[1]);
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(0, 1, 0, new HashSet<>()));

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            answer = Math.max(answer, node.sheep);
            node.adjacent.addAll(tree[node.num]);

            for (Integer next : node.adjacent) {
                HashSet<Integer> adj = new HashSet<>(node.adjacent);
                adj.remove(next);

                if (info[next] == 0) {
                    queue.add(new Node(next, node.sheep + 1, node.wolf, adj));
                } else {
                    if (answer >= node.wolf + 1) {
                        queue.add(new Node(next, node.sheep, node.wolf + 1, adj));
                    }
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        p92343 p = new p92343();
        int[] info = new int[]{0,0,1,1,1,0,1,0,1,0,1,1};
        int[][] edges = new int[][] {
                {0,1}, {1,2}, {1,4}, {0,8}, {8,7}, {9,10}, {9,11}, {4,3}, {6,5}, {4,6}, {8,9}
        };
        System.out.println(p.solution(info, edges));
    }
}
