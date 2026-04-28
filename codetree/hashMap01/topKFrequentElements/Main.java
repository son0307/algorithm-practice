package codetree.hashMap01.topKFrequentElements;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        List<Integer> list = map.keySet().stream().collect(Collectors.toList());

        Collections.sort(list, (o1, o2) -> {
            if (map.get(o1) == map.get(o2)) {
                return o2 - o1;
            } else {
                return map.get(o2) - map.get(o1);
            }
        });

        for (int i = 0; i < k; i++) {
            System.out.print(list.get(i) + " ");
        }
    }
}
