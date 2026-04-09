package baekjoon.p2166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Node {
    long x;
    long y;

    Node(long x, long y) {
        this.x = x;
        this.y = y;
    }
}

public class Main {
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
        Node n1 = nodes[0];
        for (int i = 1; i < n - 1; i++) {
            Node n2 = nodes[i];
            Node n3 = nodes[i + 1];
            total += calcSize(n1, n2, n3);
        }

        System.out.printf("%.1f\n", Math.abs(total));
    }

    public static double calcSize(Node n1, Node n2, Node n3) {
        long a = (n1.x * n2.y) + (n2.x * n3.y) + (n3.x * n1.y);
        long b = (n1.y * n2.x) + (n2.y * n3.x) + (n3.y * n1.x);

        return (a - b) / 2.0;
    }
}