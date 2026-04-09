package programmers.lv2;

import java.util.*;

public class p92341 {
    HashMap<String, Integer> parking = new HashMap<>();
    HashMap<String, Integer> totalTimes = new HashMap<>();

    public void in(String time, String num) {
        String[] times = time.split(":");
        int minute = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        parking.put(num, minute);
    }

    public void out(String time, String num) {
        String[] times = time.split(":");
        int minute = Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        int diff = minute - parking.get(num);
        parking.remove(num);
        totalTimes.put(num, totalTimes.getOrDefault(num, 0) + diff);
    }

    public int[] calculateFee(List<String> nums, int[] fees) {
        int[] answer = new int[nums.size()];

        for (int i = 0; i < nums.size(); i++) {
            answer[i] = fees[1];
            int time = totalTimes.get(nums.get(i));
            if (time > fees[0]) {
                answer[i] += (int) (Math.ceil((double) (time - fees[0]) / fees[2]) * fees[3]);
            }
        }

        return answer;
    }

    public int[] solution(int[] fees, String[] records) {
        for (String record : records) {
            String[] split = record.split(" ");
            if (split[2].equals("IN")) {
                in(split[0], split[1]);
            } else if (split[2].equals("OUT")) {
                out(split[0], split[1]);
            }
        }

        List<String> remaining = new ArrayList<>(parking.keySet());
        for (String num : remaining) {
            out("23:59", num);
        }

        List<String> sortedNum = new ArrayList<>(totalTimes.keySet());
        Collections.sort(sortedNum);

        return calculateFee(sortedNum, fees);
    }

    public static void main(String[] args) {
        int[] fees = {1, 100000, 10, 10000};
        String[] records = {"00:00 1234 IN", "00:02 1234 OUT"};
        System.out.println(Arrays.toString(new p92341().solution(fees, records)));
    }
}
