package programmers.lv2;

import java.util.LinkedList;
import java.util.Queue;

public class p154538 {
    public class Value{
        int val;
        int count;

        public Value(int val, int count) {
            this.val = val;
            this.count = count;
        }
    }

    public int solution(int x, int y, int n) {
        if (x == y) return 0;
        Value init = new Value(y, 1);

        Queue<Value> q = new LinkedList<>();
        q.offer(init);
        while (!q.isEmpty()) {
            Value v = q.poll();
            if (v.val < x) continue;

            int sub = v.val - n;
            if (sub == x) return v.count;
            else q.offer(new Value(sub, v.count + 1));

            if (v.val % 2 == 0)
                if (v.val / 2 == x)
                    return v.count;
                else
                    q.offer(new Value(v.val / 2, v.count + 1));

            if (v.val % 3 == 0)
                if (v.val / 3 == x)
                    return v.count;
                else
                    q.offer(new Value(v.val / 3, v.count + 1));
        }

        return -1;
    }

    public static void main(String[] args) {
        p154538 p = new p154538();
        int x = 1;
        int y = 4;
        int n = 10;
        System.out.println(p.solution(x, y, n));
    }
}
