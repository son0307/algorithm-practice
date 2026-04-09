package p1717;

import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int[] parents;
    static int n, m;

    private static void make() {
        for (int i = 0; i <= n; i++) {
            parents[i] = i;
        }
    }

    public static void union(int i, int j) {
        parents[find(i)] = find(j);
    }

    public static int find(int i) {
        if(i != parents[i])
            parents[i] = find(parents[i]);

        return parents[i];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        parents = new int[n+1];
        make();

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (num == 0) {
                union(a, b);
            }
            else {
                if(find(a) == find(b))
                    sb.append("YES\n");
                else
                    sb.append("NO\n");
            }
        }

        System.out.println(sb);
    }
}
