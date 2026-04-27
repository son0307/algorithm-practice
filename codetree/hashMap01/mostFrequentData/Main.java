package codetree.hashMap01.mostFrequentData;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        HashMap<String, Integer> map = new HashMap<>();

        int n = Integer.parseInt(br.readLine());

        for (int i = 0; i < n; i++) {
            String k = br.readLine();
            map.put(k, map.getOrDefault(k, 0) + 1);
        }

        System.out.println(map.values().stream().max(Integer::compareTo).get());
    }
}