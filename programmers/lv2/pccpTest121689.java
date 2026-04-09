package programmers.lv2;

import java.util.ArrayList;
import java.util.List;

class Order {
    int time;
    int duration;

    public Order(int time, int duration) {
        this.time = time;
        this.duration = duration;
    }
}

public class pccpTest121689 {
    public int solution(int[] menu, int[] order, int k) {
        Order[] orders = new Order[order.length];
        for (int i = 0; i < order.length; i++) {
            orders[i] = new Order(k * i, menu[order[i]]);
        }

        int max = 0;
        int time = 0;
        int end = 0;
        for (int start = 0; start < order.length; start++) {
            Order o = orders[start];

            if (time < o.time) {
                time = o.time;
            }

            time += o.duration;
            while (end < order.length && orders[end].time < time)
                end++;

            max = Math.max(max, end - start);
        }

        return max;
    }

    public static void main(String[] args) {
        int[] menu = {2, 6};
        int[] order = {0,1,1,1};
        int k = 10;
        System.out.println(new pccpTest121689().solution(menu, order, k));
    }
}
