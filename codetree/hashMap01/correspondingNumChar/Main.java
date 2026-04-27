package codetree.hashMap01.correspondingNumChar;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        HashMap<String, String> map = new HashMap<>();


        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= n; i++) {
            String s = br.readLine();
            map.put(s, Integer.toString(i));
            map.put(Integer.toString(i), s);
        }

        for (int i = 1; i <= m; i++) {
            System.out.println(map.get(br.readLine()));
        }
    }
}
