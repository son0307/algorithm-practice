package codetree.hashMap01.groupSameWord;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        HashMap<String, Integer> groupCount = new HashMap<>();
        int max = 0;

        for (int i = 0; i < n; i++) {
            String s = br.readLine();

            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sortedStr = new String(chars);

            int count = groupCount.getOrDefault(sortedStr, 0) + 1;
            groupCount.put(sortedStr, count);
            max = Math.max(max, count);
        }

        System.out.println(max);
    }
}