package programmers.lv2.p155651;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class p155651 {
    public static class RoomTime {
        int startTime;
        int endTime;

        public RoomTime(int startTime, int endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public int getStartTime() {
            return startTime;
        }
    }

    public int solution(String[][] book_time) {
        int maxRoom = 0;

        PriorityQueue<RoomTime> pq = new PriorityQueue<>(Comparator.comparing(RoomTime::getStartTime));

        for (String[] row : book_time) {
            String[] startTimeSplit = row[0].split(":");
            String[] endTimeSplit = row[1].split(":");

            int startTime = Integer.parseInt(startTimeSplit[0]) * 100 + Integer.parseInt(startTimeSplit[1]);
            int endTime = Integer.parseInt(endTimeSplit[0]) * 100 + Integer.parseInt(endTimeSplit[1]);

            endTime += 10;
            if (endTime % 100 >= 60)
                endTime += 40;

            pq.add(new RoomTime(startTime, endTime));
        }

        ArrayList<RoomTime> roomTimes = new ArrayList<>();

        while(!pq.isEmpty()) {
            RoomTime cur = pq.poll();

            if (!roomTimes.isEmpty()) {
                roomTimes.removeIf(rt -> cur.startTime >= rt.endTime);
            }
            roomTimes.add(cur);

            if (roomTimes.size() > maxRoom) maxRoom = roomTimes.size();
        }

        return maxRoom;
    }

    public static void main(String[] args) {
        p155651 p = new p155651();
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
