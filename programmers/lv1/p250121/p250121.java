package programmers.lv1.p250121;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class p250121 {
    public int[][] solution(int[][] data, String ext, int val_ext, String sort_by) {
        ArrayList<int[]> answer = new ArrayList<>();

        int criterion = 0;
        switch (ext) {
            case "code" -> criterion = 0;
            case "date" -> criterion = 1;
            case "maximum" -> criterion = 2;
            case "remain" -> criterion = 3;
        }

        for (int i = 0; i < data.length; i++) {
            if (data[i][criterion] < val_ext)
                answer.add(data[i]);
        }

        switch (sort_by) {
            case "code" -> criterion = 0;
            case "date" -> criterion = 1;
            case "maximum" -> criterion = 2;
            case "remain" -> criterion = 3;
        }

        int finalCriterion = criterion;
        answer.sort(Comparator.comparingInt(a -> a[finalCriterion]));

        return answer.toArray(new int[answer.size()][]);
    }

    public static void main(String[] args) {
        p250121 p = new p250121();
        int[][] date = {
                {1, 20300104, 100, 80},
                {2, 20300804, 847, 37},
                {3, 20300401, 10, 8}
        };
        String ext = "date";
        int val_ext = 20300501;
        String sort_by = "remain";

        System.out.println(Arrays.deepToString(p.solution(date, ext, val_ext, sort_by)));
    }
}
