## 문제 요약

- **목표**: 영대문자로 이루어진 후위 표기식과 각 대문자에 대응하는 숫자가 주어졌을 때, 식을 계산하여 최종 결과를 도출한다.
- **조건**:
    1. 최종 결과는 소수점 둘째 자리까지 출력해야 한다.
- **출력 항목**: 계산이 완료된 실수형 결괏값

## 핵심 포인트

- **스택 (Stack) 연산**: 후위 표기식을 계산할 때는 피연산자(숫자)를 만나면 스택에 넣고(`push`), 연산자를 만나면 스택에서 피연산자 두 개를 꺼내(`pop`) 연산한 뒤 그 결과를 다시 스택에 넣는 과정의 반복이다.
- **비교환 법칙 연산의 순서 주의 (`-`, `/`)**: 덧셈과 곱셈은 순서가 바뀌어도 상관없지만, 뺄셈과 나눗셈은 먼저 꺼낸 값(`num1`)이 뒤로 가고, 나중에 꺼낸 값(`num2`)이 앞으로 와야 한다. (예: `num2 - num1`, `num2 / num1`)
- **타입 변환 및 포맷팅**: 나눗셈 시 소수점 아래까지 정확히 계산하기 위해 값을 `Double`이나 `double`형으로 다루어야 하며, `printf("%.2f")`를 사용해 출력 형식을 맞춘다.

### 동작 원리

1. **값 매핑**: 피연산자의 개수 N을 입력받고, 이어서 주어지는 후위 표기식을 문자 배열(`char[]`)로 변환한다. 그 후 N개의 숫자를 입력받아 각각 A, B, C... 에 매핑하여 배열에 저장해 둔다.
2. **수식 순회**: 후위 표기식 배열을 처음부터 끝까지 한 글자씩 순회한다.
3. **피연산자 처리**: 현재 문자가 알파벳 대문자라면, 매핑해둔 배열에서 해당 값을 찾아 `double`형으로 변환한 뒤 스택에 넣는다(`push`).
4. **연산자 처리**: 현재 문자가 사칙연산자라면, 스택에서 값 두 개를 꺼낸다(`pop`). 나중에 꺼낸 값에서 먼저 꺼낸 값을 연산한 뒤, 그 결괏값을 다시 스택에 넣는다.
5. **결과 출력**: 수식 순회가 모두 끝나면 스택에는 최종 결괏값 단 1개만 남아있게 된다. 이를 꺼내어 소수점 둘째 자리까지 반올림하여 출력(`printf`)한다.

### 전체 코드

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        char[] chars = br.readLine().toCharArray();

        double[] values = new double[n];
        for (int i = 0; i < n; i++) {
            values[i] = Double.parseDouble(br.readLine());
        }

        Stack<Double> st = new Stack<>();
        for (char c : chars) {
            switch (c) {
                case '+': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 + num1);
                } break;
                case '-': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 - num1);
                } break;
                case '*': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 * num1);
                } break;
                case '/': {
                    double num1 = st.pop();
                    double num2 = st.pop();
                    st.push(num2 / num1);
                } break;
                default:
                    // 'A'의 아스키코드를 빼면 0부터 시작하는 인덱스로 사용 가능
                    st.push(values[c - 'A']);
            }
        }

        // 소수 두번째 자리까지 표기
        System.out.printf("%.2f\n", st.pop());
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 입력 데이터의 피연산자 개수를 N, 후위 표기식의 길이를 L이라고 할 때, 다음과 같이 계산할 수 있다.
    - 피연산자들을 순회하며 배열에 저장 : $O(N)$
    - 후위 표기식을 하나씩 살피며 처리 : $O(L)$

따라서, 최종적으로 시간복잡도는 $O(N+L)$이 된다.