package programmers.lv1.p178871;

import java.util.Arrays;
import java.util.HashMap;

public class p178871 {
    public String[] solution(String[] players, String[] callings) {

        HashMap<String, Integer> ranks = new HashMap<>();
        for (int i = 0; i < players.length; i++) {
            ranks.put(players[i], i);
        }

        for (String calling : callings) {
            String target = players[ranks.get(calling) - 1];
            ranks.put(calling, ranks.get(calling) - 1);
            ranks.put(target, ranks.get(target) + 1);

            players[ranks.get(calling)] = calling;
            players[ranks.get(target)] = target;
        }

        return players;
    }

    public static void main(String[] args) {
        String[] players = {"mumu", "soe", "poe", "kai", "mine"};
        String[] callings = {"kai", "kai", "mine", "mine"};
        System.out.println(Arrays.toString(new p178871().solution(players, callings)));
    }
}
