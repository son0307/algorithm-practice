package programmers.lv2.p42577;

import java.util.Arrays;

public class p42577 {
    public boolean solution(String[] phone_book) {
        // 사전 순으로 정렬 시 "12", "123", "1235", "567", "88"로 정렬됨.
        // 즉, 앞에 나온 숫자가 뒤에 나오는 숫자의 접두어가 된다.
        Arrays.sort(phone_book);

        for (int i = 0; i < phone_book.length - 1; i++) {
            if (phone_book[i + 1].startsWith(phone_book[i])) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        p42577 p = new p42577();
        System.out.println(p.solution(new String[]{"12","123","1235","567","88"}));
    }
}
