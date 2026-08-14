## 1. 문제 요약

여러 개의 신호등이 있고, 각 신호등은 모두 초록불 → 노란불 → 빨간불 순서로 반복된다.

각 신호등마다 초록불, 노란불, 빨간불이 유지되는 시간이 다르며, 모든 신호등은 처음에 초록불 상태에서 시작한다.

이때 모든 신호등이 동시에 노란불이 되는 가장 빠른 시간을 구해야 한다.

만약 그런 시간이 없다면 -1을 반환한다.

## 2. 핵심 포인트

이 문제의 핵심은 각 신호등의 상태가 일정한 주기로 반복된다는 점이다.

예를 들어 [G, Y, R]이라면 한 신호등의 전체 주기는 다음과 같다.

전체 주기 = G + Y + R

그리고 특정 시간 time에서 해당 신호등이 현재 주기 중 어디에 있는지는 다음과 같이 구할 수 있다.

time % 전체주기

각 신호등의 노란불 구간은 다음과 같다.

G초 이후부터 G + Y초까지

즉, 현재 시간이 노란불이려면 다음 조건을 만족해야 한다.

timing > G && timing <= G + Y

또한 모든 신호등의 상태는 각자의 주기를 기준으로 반복되므로, 모든 신호등의 상태 조합은 전체 주기들의 최소공배수(LCM) 이후에는 다시 반복된다.

따라서 1초부터 전체 주기의 최소공배수까지만 확인하면 된다.

## 3. 동작 원리

먼저 각 신호등의 전체 주기를 구한다.

totals[i] = G + Y + R;

그다음 모든 신호등 주기의 최소공배수를 구한다.

int totalLcm = totals[0];

for (int i = 1; i < totals.length; i++) {
totalLcm = lcm(totalLcm, totals[i]);
}

최소공배수까지만 확인하는 이유는, 그 이후의 신호 패턴은 이전과 동일하게 반복되기 때문이다.

그 후 time = 1부터 totalLcm까지 1초씩 증가시키며 모든 신호등이 노란불인지 검사한다.

int timing = time % totals[i];

여기서 timing은 현재 시간이 해당 신호등의 한 주기 안에서 몇 번째 위치인지 나타낸다.

노란불 구간이 아니라면 flag = false로 바꾸고 다음 시간으로 넘어간다.

if (timing <= signals[i][0] || timing > signals[i][0] + signals[i][1]) {
flag = false;
break;
}

모든 신호등이 노란불이면 flag가 끝까지 true로 유지되므로 해당 시간을 반환한다.

if (flag) {
return time;
}

끝까지 찾지 못하면 모든 신호등이 동시에 노란불이 되는 시간이 없다는 의미이므로 -1을 반환한다.

## 4. 전체 코드
   package programmers.lv1.p468371;

public class p468371 {
public static int gcd(int a, int b) {
while (b > 0) {
int temp = a % b;
a = b;
b = temp;
}

        return a;
    }

    public static int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    public static int solution(int[][] signals) {
        int time = 1;

        int[] totals = new int[signals.length];

        for (int i = 0; i < totals.length; i++) {
            for (int j = 0; j < 3; j++) {
                totals[i] += signals[i][j];
            }
        }

        int totalLcm = totals[0];

        for (int i = 1; i < totals.length; i++) {
            totalLcm = lcm(totalLcm, totals[i]);
        }

        while (time <= totalLcm) {
            boolean flag = true;

            for (int i = 0; i < totals.length; i++) {
                int timing = time % totals[i];

                if (timing <= signals[i][0] || timing > signals[i][0] + signals[i][1]) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                return time;
            }

            time++;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[][] signals = {
            {1, 1, 4},
            {2, 1, 3},
            {3, 1, 2},
            {4, 1, 1}
        };

        System.out.println(solution(signals));
    }
}
5. 시간 복잡도

신호등의 개수를 n, 모든 신호등 주기의 최소공배수를 L이라고 하면, 최대 L초까지 확인하고 매 시간마다 n개의 신호등을 검사한다.

따라서 시간 복잡도는 다음과 같다.

O(L × n)

제한사항에서 각 신호등의 전체 주기는 최대 20, 신호등 개수는 최대 5이므로 최소공배수의 크기도 충분히 작다.