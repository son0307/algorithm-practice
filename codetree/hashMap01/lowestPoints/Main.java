package codetree.hashMap01.lowestPoints;

import java.io.*;
import java.util.*;

public class Main {
    static HashMap<Integer, Integer> points = new HashMap<>();

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            if (points.containsKey(x)) {
                points.put(x, Math.min(points.get(x), y));
            } else {
                points.put(x, y);
            }
        }

        long answer = 0;
        for (int x : points.keySet()) {
            answer += points.get(x);
        }
        System.out.println(answer);
    }
}
