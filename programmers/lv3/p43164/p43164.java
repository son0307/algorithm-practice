package programmers.lv3.p43164;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class p43164 {
    boolean flag;
    ArrayList<String> ans = new ArrayList<>();

    public String[] solution(String[][] tickets) {
        Arrays.sort(tickets, Comparator
                .comparing((String[] t) -> t[0])
                .thenComparing((t) -> t[1]));
        boolean[] visited = new boolean[tickets.length];

        dfs(tickets, visited, "ICN", new ArrayList<>(), 0);

        return ans.toArray(new String[0]);
    }

    void dfs(String[][] tickets, boolean[] visited, String curCity, ArrayList<String> history, int usedTicket) {
        history.add(curCity);
        if(tickets.length == usedTicket) {
            ans = new ArrayList<>(history);
            flag = true;
            return;
        }

        for (int i = 0; i < tickets.length; i++) {
            if (!visited[i] && tickets[i][0].equals(curCity)) {
                visited[i] = true;
                dfs(tickets, visited, tickets[i][1], history, usedTicket + 1);
                visited[i] = false;

                if (flag)
                    return;
            }
        }

        history.remove(history.size() - 1);
    }

    public static void main(String[] args) {
        p43164 p = new p43164();
//        String[][] tickets1 = {{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}};
//        String[][] tickets2 = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL","SFO"}};
        String[][] tickets3 = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"SFO", "ICN"}, {"ATL","SFO"}};

//        System.out.println(Arrays.toString(p.solution(tickets1)));
//        System.out.println(Arrays.toString(p.solution(tickets2)));
        System.out.println(Arrays.toString(p.solution(tickets3)));
    }
}
