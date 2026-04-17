## 문제 요약

- **목표**: 괄호와 사칙연산(`+`, `-`, `*`, `/`)으로 이루어진 중위 표기식(Infix Notation)을 후위 표기식(Postfix Notation)으로 변환한다.
- **조건**:
    1. 피연산자는 알파벳 대문자로만 주어지며, 식의 길이는 100을 넘지 않는다.
    2. 괄호 안의 수식이 가장 먼저 계산되며, 연산자 우선순위는 곱셈/나눗셈(`*`, `/`)이 덧셈/뺄셈(`+`, `-`)보다 높다.
- **출력 항목**: 변환이 완료된 후위 표기식 문자열

## 핵심 포인트

- **스택 (Stack) 자료구조**: 연산자들을 잠시 보관해두었다가, 자신보다 우선순위가 낮거나 같은 연산자가 나타났을 때 적절한 위치에 꺼내어 배치하기 위해 LIFO(후입선출) 구조의 스택이 필수적으로 사용된다.
- **우선순위 판별 (Precedence)**:
    - 스택 안에서 `(`는 가장 우선순위가 낮게 취급되고, `)` 이 나올때까지 스택에서 빠져나오지 못 한다. `)` 이 등장하면 `(` 을 찾을 때까지 모든 연산자를 빼낸다.
    - 우선순위는 `+`,`-` < `*`, `/` 이며,
      스택 가장 위에 있는 연산자의 우선순위 ≥ 새로 들어오는 연산자의 우선순위 → 하나씩 빼냄
      스택 가장 위에 있는 연산자의 우선순위 < 새로 들어오는 연산자의 우선순위 → 그대로 쌓음
    - `+` , `-` 는 괄호를 제외한 대부분의 연산자를 모두 밖으로 밀어내고 들어간다.
    - `*`, `/`는 우선순위가 높으므로, 자신과 같은 `*`, `/`만 밀어내고 들어간다.

### 동작 원리

![image.png](attachment:c26c82e0-e33e-438f-a8d3-d1d2199be5ba:image.png)

![image.png](attachment:701ba075-ee89-47fc-b6f5-ba9570bcbf58:image.png)

1. **문자열 순회**: 입력받은 중위 표기식을 한 글자씩 순회한다.
2. **피연산자 (알파벳)**: 스택에 넣지 않고 곧바로 출력 문자열(`StringBuilder`)에 이어 붙인다.
3. **여는 괄호 `(`**: 무조건 스택에 `push`하여 새로운 우선순위 구간의 시작(벽)을 알린다.
4. **닫는 괄호 `)`**: 스택에서 여는 괄호 `(`가 나올 때까지 모든 연산자를 `pop`하여 출력에 붙이고, 마지막에 여는 괄호는 출력 없이 스택에서 버린다.
5. **사칙연산자 (`+`, `-`, `*`, `/`)**:
   자신보다 우선순위가 높거나 같은 연산자가 스택 최상단(peek)에 있다면, 그것들을 모두 `pop`하여 출력에 붙인다. 이후 더 이상 뺄 것이 없으면 자신을 스택에 `push`한다.
6. **잔여 연산자 처리**: 순회가 모두 끝난 후, 스택에 남아있는 모든 연산자를 순서대로 `pop`하여 출력 문자열 끝에 이어 붙인다.

### 전체 코드

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] mid = sc.nextLine().split("");
        StringBuilder sb = new StringBuilder();
        Stack<String> st = new Stack<>();

        for (String m : mid) {
            switch (m) {
                case "(": st.push(m); break;
                case ")": {
                    while (!st.isEmpty() && !st.peek().equals("("))
                        sb.append(st.pop());
                    st.pop();
                } break;
                case "+": 
                case "-": {
                    while (!st.isEmpty() && !st.peek().equals("("))
                        sb.append(st.pop());
                    st.push(m);
                } break;
                case "*":
                case "/" : {
                    while (!st.isEmpty() && (st.peek().equals("*") || st.peek().equals("/")))
                        sb.append(st.pop());
                    st.push(m);
                } break;
                default: sb.append(m);
            }
        }

        while (!st.isEmpty())
            sb.append(st.pop());

        System.out.println(sb);
    }
}
```

위 알고리즘의 시간 복잡도는 다음과 같다.

- 입력 문자열의 길이를 N이라고 할 때, 각 문자는 스택에 최대 한 번 들어가고, 최대 한 번 나온다. 스택에 들어갔다 나오는 총합 횟수는 2N을 넘지 않으므로 전체 시간 복잡도는 $O(N)$이다.