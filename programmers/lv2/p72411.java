package programmers.lv2;

import java.util.*;

public class p72411 {

    public void check_course(int idx, boolean[] checked, String order, int course_count, StringBuilder menu, HashMap<String, Integer> menus) {
        if (menu.length() == course_count) {
            char[] sorted = menu.toString().toCharArray();
            Arrays.sort(sorted);
            String sortedMenu = new String(sorted);
            menus.put(sortedMenu, menus.getOrDefault(sortedMenu, 0) + 1);
            return;
        }

        for (int i = idx; i < checked.length; i++) {
            if (!checked[i]) {
                checked[i] = true;
                menu.append(order.charAt(i));
                check_course(i + 1, checked, order, course_count, menu, menus);
                menu.deleteCharAt(menu.length() - 1);
                checked[i] = false;
            }
        }
    }

    public String[] solution(String[] orders, int[] course) {
        List<String> answer = new ArrayList<>();

        HashMap<String, Integer> menus = new HashMap<>();
        StringBuilder menu = new StringBuilder();

        for (int course_count : course) {
            for (String order : orders) {
                boolean[] checked = new boolean[order.length()];
                check_course(0, checked, order, course_count, menu, menus);
            }
        }

        for (int course_count : course) {
            int max = 0;

            for (String key : menus.keySet()) {
                if (key.length() == course_count && menus.get(key) >= 2) {
                    max = Math.max(max, menus.get(key));
                }
            }

            for (String key : menus.keySet()) {
                if (key.length() == course_count && menus.get(key) == max && menus.get(key) >= 2) {
                    answer.add(key);
                }
            }
        }

        Collections.sort(answer);

        return answer.toArray(new String[0]);
    }

    public static void main(String[] args) {
        p72411 p = new p72411();
        System.out.println(Arrays.toString(p.solution(new String[]{"XYZ", "XWY", "WXA"}, new int[]{2, 3, 4})));
    }
}
