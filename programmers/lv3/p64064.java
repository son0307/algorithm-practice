package programmers.lv3;

import java.util.Arrays;
import java.util.HashSet;

public class p64064 {

    public void find_ban_list(int idx, String[][] bans, HashSet<String> ban_ids, HashSet<HashSet<String>> ban_list) {
        if (idx == bans.length) {
            ban_list.add(new HashSet<>(ban_ids));
            return;
        }

        for (String id : bans[idx]) {
            if (ban_ids.contains(id)) continue;
            ban_ids.add(id);
            find_ban_list(idx+1, bans, ban_ids, ban_list);
            ban_ids.remove(id);
        }
    }

    public int solution(String[] user_id, String[] banned_id) {
        String[][] bans = Arrays.stream(banned_id)
                .map(banned -> banned.replaceAll("\\*", "."))
                .map(banned -> Arrays.stream(user_id)
                        .filter(id -> id.matches(banned))
                        .toArray(String[]::new))
                .toArray(String[][]::new);

        HashSet<HashSet<String>> ban_list =  new HashSet<>();
        boolean[] check = new boolean[bans.length];
        HashSet<String> ban_ids = new HashSet<>();

        find_ban_list(0, bans, ban_ids, ban_list);

        return ban_list.size();
    }

    public static void main(String[] args) {
        p64064 p = new p64064();
        System.out.println("p = " + p.solution(new String[] {"12345", "12453", "aaaaa"}, new String[] {"*****", "*****"}));
    }
}
