package programmers.lv2.p12985;

public class p12985 {
    public int solution(int n, int a, int b)
    {
        // a, b sort
        if(b < a) {
            int temp = a;
            a = b;
            b = temp;
        }

        while (!(a<=n/2 && b>n/2)) {
            if (a > n/2) {
                a -= n/2;
                b -= n/2;
            }

            n/=2;
        }

        return (int)(Math.log(n)/Math.log(2));
    }

    public static void main(String[] args) {
        p12985 p = new p12985();
        int answer = p.solution(8, 2, 4);
        System.out.println(answer);
    }
}
