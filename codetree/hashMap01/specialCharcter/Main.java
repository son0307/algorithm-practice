package codetree.hashMap01.specialCharcter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        HashMap<Character, Integer> charCount = new HashMap<>();
        for(char c : s.toCharArray())
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);

        for(char c : s.toCharArray()) {
            if (charCount.get(c) == 1) {
                System.out.println(c);
                return;
            }
        }

        System.out.println("None");
    }
}
