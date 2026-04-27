package codetree.hashMap01.basic;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<Integer, Integer> map = new HashMap<>();

        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String command = st.nextToken();
            if (command.equals("add")) {
                int k = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                map.put(k, v);
            }
            else if (command.equals("remove")) {
                int k = Integer.parseInt(st.nextToken());
                map.remove(k);
            }
            else if (command.equals("find")) {
                int k = Integer.parseInt(st.nextToken());
                if (map.containsKey(k))
                    System.out.println(map.get(k));
                else
                    System.out.println("None");
            }
        }
    }
}