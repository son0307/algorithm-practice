package programmers.lv3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class p77486 {
    HashMap<String, String> referralMap = new HashMap<>();
    Map<String, Integer> profitMap = new HashMap<>();

    public Integer[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {

        for (int i = 0; i < enroll.length; i++) {
            referralMap.put(enroll[i], referral[i]);
        }

        for (int i = 0; i < seller.length; i++) {
            distribute(seller[i], amount[i] * 100);
        }

        List<Integer> answer = Arrays.stream(enroll)
                .map(e -> profitMap.getOrDefault(e, 0))
                .collect(Collectors.toList());

        return answer.toArray(new Integer[0]);
    }

    public void distribute(String seller, int price) {
        if (price == 0)
            return;

        int tax = (int) (price * 0.1);
        int profit = profitMap.getOrDefault(seller, 0) + (price - tax);

        profitMap.put(seller, profit);

        if (!referralMap.get(seller).equals("-"))
            distribute(referralMap.get(seller), tax);
    }

    public static void main(String[] args) {
        p77486 p = new p77486();

        String[] enroll = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
        String[] referral = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
        String[] seller = {"young", "john", "tod", "emily", "mary"};
        int[] amount = {12, 4, 2, 5, 10};

        String[] enroll2 = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
        String[] referral2 = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
        String[] seller2 = {"sam", "emily", "jaimie", "edward"};
        int[] amount2 = {2, 3, 5, 4};

//        System.out.println("answer = " + Arrays.toString(p.solution(enroll, referral, seller, amount)));
        System.out.println("answer = " + Arrays.toString(p.solution(enroll2, referral2, seller2, amount2)));
    }
}
