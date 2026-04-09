package programmers.lv2;

import java.util.Arrays;

public class p67257 {
    private long getAbstract(String[] numbers, String[] operators, String[] symbols) {
        String[] numbers1 = Arrays.copyOfRange(numbers, 0, numbers.length);
        String[] operators1 = Arrays.copyOfRange(operators, 0, operators.length);

        long result = 0;
        for (String symbol : symbols) {
            for (int j = 1; j < operators1.length; j++) {
                if (symbol.equals("*") && operators1[j].equals("*")) {
                    operators1[j] = "0";
                    int left = j - 1;
                    int right = j;
                    while (numbers1[left].equals("*"))
                        left--;
                    while (numbers1[right].equals("*"))
                        right++;
                    long cal = Long.parseLong(numbers1[left]) * Long.parseLong(numbers1[right]);
                    numbers1[left] = String.valueOf(cal);
                    numbers1[right] = "*";
                } else if (symbol.equals("-") && operators1[j].equals("-")) {
                    operators1[j] = "0";
                    int left = j - 1;
                    int right = j;
                    while (numbers1[left].equals("*"))
                        left--;
                    while (numbers1[right].equals("*"))
                        right++;
                    long cal = Long.parseLong(numbers1[left]) - Long.parseLong(numbers1[right]);
                    numbers1[left] = String.valueOf(cal);
                    numbers1[right] = "*";
                } else if (symbol.equals("+") && operators1[j].equals("+")) {
                    operators1[j] = "0";
                    int left = j - 1;
                    int right = j;
                    while (numbers1[left].equals("*"))
                        left--;
                    while (numbers1[right].equals("*"))
                        right++;
                    long cal = Long.parseLong(numbers1[left]) + Long.parseLong(numbers1[right]);
                    numbers1[left] = String.valueOf(cal);
                    numbers1[right] = "*";
                }
            }
            if(symbol.equals(symbols[2])) {
                for (String s : numbers1) {
                    if (!s.equals("*")) {
                        result = Long.parseLong(s);
                        break;
                    }
                }
            }
        }

        System.out.println("Math.abs(result) = " + Math.abs(result));
        return Math.abs(result);
    }

    public long solution(String expression) {
        String[] numbers = expression.split("[^0-9]+");
        String[] operators = expression.split("[0-9]+");

        long max = Long.MIN_VALUE;
        max = Math.max(max, getAbstract(numbers, operators, new String[]{"+", "-", "*"}));
        max = Math.max(max, getAbstract(numbers, operators, new String[]{"+", "*", "-"}));
        max = Math.max(max, getAbstract(numbers, operators, new String[]{"-", "+", "*"}));
        max = Math.max(max, getAbstract(numbers, operators, new String[]{"-", "*", "+"}));
        max = Math.max(max, getAbstract(numbers, operators, new String[]{"*", "-", "+"}));
        max = Math.max(max, getAbstract(numbers, operators, new String[]{"*", "+", "-"}));

        return max;
    }

    public static void main(String[] args) {
        p67257 p = new p67257();
        System.out.println("p = " + p.solution("0*2"));
    }
}
