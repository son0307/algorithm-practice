package baekjoon.p2166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        Node[] nodes = new Node[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            nodes[i] = new Node(x, y);
        }

        double total = 0.0;
        for (int i = 0; i < n; i++) {
            Node current = nodes[i];
            Node next = nodes[(i + 1) % n];

            total += (double)(current.y + next.y) * (next.x - current.x) / 2.0;
        }

        System.out.printf("%.1f\n", Math.abs(total));
    }
}