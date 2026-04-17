package programmers.lv2.p42583;

import java.util.LinkedList;
import java.util.Queue;

public class p42583 {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Queue<Integer> bridge = new LinkedList<>();

        for (int i = 0; i < bridge_length; i++) {
            bridge.offer(0);
        }

        int bridge_weight = 0;
        int time = 0;
        int truck_index = 0;

        while(truck_index < truck_weights.length) {
            bridge_weight -= bridge.poll();
            if (bridge_weight + truck_weights[truck_index] <= weight) {
                bridge_weight += truck_weights[truck_index];
                bridge.offer(truck_weights[truck_index]);

                truck_index++;
            } else {
                bridge.offer(0);
            }

            time++;
        }

        return time + bridge_length;
    }

    public static void main(String[] args) {
        p42583 p = new p42583();
        int bridge_length = 5;
        int weight = 7;
        int[] truck_weights = {2, 4, 3, 3};

        System.out.println(p.solution(bridge_length, weight, truck_weights));
    }
}
