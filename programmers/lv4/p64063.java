package programmers.lv4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class p64063 {
    Map<Long, Long> map = new HashMap<>();

    private long find(long room) {
       if (!map.containsKey(room)) {
           map.put(room, room + 1);
           return room;
       }

       long nextRoom = map.get(room);
       long emptyRoom = find(nextRoom);

       map.put(room, emptyRoom + 1);

       return emptyRoom;
    }

    public long[] solution(long k, long[] room_number) {
        long[] answer = new long[room_number.length];

        for (int i = 0; i < room_number.length; i++) {
            answer[i] = find(room_number[i]);
        }

        return answer;
    }

    public static void main(String[] args) {
        long k = 10;
        long[] room_number = new long[]{1,3,4,1,3,1};
        System.out.println(Arrays.toString(new p64063().solution(k, room_number)));
    }
}
