package programmers.lv1.p42576;

import java.util.HashMap;

public class p42576 {
    public String solution(String[] participants, String[] completion) {
        HashMap<String, Integer> m = new HashMap<>();
        for (String p : participants) {
            m.put(p, m.getOrDefault(p, 0) + 1);
        }

        for (String c : completion) {
            if (m.get(c) == 1)
                m.remove(c);
            else
                m.put(c, m.get(c) - 1);
        }

        return m.keySet().toArray()[0].toString();
    }

    public static void main(String[] args) {
        p42576 p = new p42576();

        String[] participants = {"leo", "kiki", "kiki", "eden"};
        String[] completion = {"eden", "kiki", "leo"};

        System.out.println(p.solution(participants, completion));
    }
}
