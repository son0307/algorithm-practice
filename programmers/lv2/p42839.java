package programmers.lv2;

import java.util.HashSet;
import java.util.Set;

public class p42839 {
    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }

        return true;
    }

    private void getAllNumbers(String[] number, boolean[] visited, Set<Integer> numberList, String num) {
        for (int i=0; i<number.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                int nextNum = Integer.parseInt(num + number[i]);
                if (isPrime(nextNum)) numberList.add(nextNum);
                getAllNumbers(number, visited, numberList, num + number[i]);
                visited[i] = false;
            }
        }
    }

    public int solution(String numbers) {
        String[] number = numbers.split("");
        boolean[] visited = new boolean[number.length];
        Set<Integer> numberList = new HashSet<>();
        getAllNumbers(number, visited, numberList, "");
        System.out.println("numberList = " + numberList);

        return numberList.size();
    }

    public static void main(String[] args) {
        p42839 p = new p42839();
        System.out.println("p = " + p.solution("17"));
    }
}
