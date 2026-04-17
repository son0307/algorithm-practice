package programmers.lv2.p155651;

import java.util.Arrays;
import java.util.PriorityQueue;

public class p155651_2 {
    public int solution(String[][] book_time) {
        int[][] bookings = new int[book_time.length][2];

        for (int i = 0; i < book_time.length; i++) {
           bookings[i][0] = parseTime(book_time[i][0]);
           bookings[i][1] = parseTime(book_time[i][1]) + 10;
        }

        Arrays.sort(bookings, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();

        for (int[] booking : bookings) {
            int startTime = booking[0];
            int endTime = booking[1];

            if (!roomEndTimes.isEmpty() && roomEndTimes.peek() <= startTime) {
                roomEndTimes.poll();
            }

            roomEndTimes.add(endTime);
        }

        return roomEndTimes.size();
    }

    private int parseTime(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }

    public static void main(String[] args) {
        p155651_2 p = new p155651_2();
        String[][] book_time1 = {
                {"15:00", "17:00"},
                {"16:40", "18:20"},
                {"14:20", "16:35"},
                {"14:10", "19:20"},
                {"18:20", "21:20"}
        };

        String[][] book_time2 = {
                {"10:20", "12:55"},
                {"09:10", "10:12"}
        };

        String[][] book_time3 = {
                {"10:20", "12:30"},
                {"10:20", "12:30"},
                {"10:20", "12:30"}
        };

        System.out.println(p.solution(book_time1));
    }
}
