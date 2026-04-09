package programmers.lv3;

import java.util.*;

public class p43163 {
    static class Node {
        String word;
        int rank;

        public Node(String word, int rank) {
            this.word = word;
            this.rank = rank;
        }
    }

    public boolean isConvertable(String src, String dest) {
        char[] src_char = src.toCharArray();
        char[] dest_char = dest.toCharArray();
        int cnt = 0;

        for (int i = 0; i < src_char.length; i++) {
            if (src_char[i] != dest_char[i]) {
                cnt++;
                if (cnt >= 2)
                    return false;
            }
        }

        return true;
    }

    public int solution(String begin, String target, String[] words) {
        boolean[] visited = new boolean[words.length];

        Queue<Node> q = new LinkedList<>();
        q.add(new Node(begin, 0));

        while(!q.isEmpty()) {
            Node cur = q.poll();
            if (cur.word.equals(target))
                return cur.rank;

            for (int i = 0; i < words.length; i++) {
                if (!visited[i] && isConvertable(cur.word, words[i])) {
                    visited[i] = true;
                    q.add(new Node(words[i], cur.rank + 1));
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        String begin = "hit";
        String target = "cog";
        String[] words = {"hot", "cog"};
        System.out.println(new p43163().solution(begin, target, words));
    }
}
