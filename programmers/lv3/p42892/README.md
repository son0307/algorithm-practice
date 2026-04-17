## 문제 요약

- 각 점의 좌표들이 1번부터 주어지고, 이 점들로 구성된 이진 트리를 전위 순회, 후위 순회한 결과를 반환하는 문제
    - 각 점들의 왼쪽 서브 트리에 속한 점들은 자기보다 작은 x좌표 값을 가지고, 오른쪽 서브 트리에 속한 점들은 자기보다 큰 x좌표 값을 가진다.
    - 이진 트리에서 같은 레벨에 있는 점들은 같은 y좌표 값을 가진다.

## 핵심 포인트

이 문제에서는 전위 순회와 후위 순회는 재귀 함수를 이용하면 손쉽게 해결할 수 있다. 따라서, 이진 트리를 주어진 조건에 맞춰 제대로 구성하는 것이 중요하다.

주어진 각 점의 좌표를 이용하면 트리 내에서 점들 간의 계층 관계와 어느쪽 서브트리에 위치해야 하는지를 알아낼 수가 있다. 우선, 각 점에 대한 정보를 저장할 수 있는 노드를 구성한다.

```java
private static class Node {
    private int x;
    private int y;
    private int value;

    private Node left;
    private Node right;

    public Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}
```

각 노드는 점에 대한 좌표와 노드 번호, 그리고 왼쪽 자식 노드와 오른쪽 자식 노드를 저장하도록 한다.

트리를 손쉽게 만들기 위해선 높은 곳에 있는 점들부터 차례로 트리에 넣어야 한다. 따라서, 점들의 y좌표를 기준으로 정렬하고 차례대로 하나씩 빼와 트리에 삽입하면 된다.

```java
Node[] nodes = new Node[nodeinfo.length];
for (int i = 0; i < nodeinfo.length; i++) {
    nodes[i] = new Node(nodeinfo[i][0] , nodeinfo[i][1], i + 1);
}
Arrays.sort(nodes, (a, b) -> b.y - a.y);
```

트리를 만드는 함수는 간단하다. y좌표를 기준으로 정렬했을 때, 가장 앞에 있는 함수가 트리에서 가장 높은 곳에 있는 루트 노드로 설정한다. 그 다음, 해당 트리에 노드를 하나씩 삽입한다. x좌표를 기준으로 왼쪽 서브트리에 속한 점들의 x좌표값은 작고, 오른쪽 서브트리에 속한 점들의 x좌표값은 크므로 이를 기준으로 왼쪽으로 갈지 오른쪽으로 갈지를 선택한다.

```java
private Node construct(Node[] nodes) {
    Node root = nodes[0];

    for (int i = 1; i < nodes.length; i++) {
        insert(root, nodes[i]);
    }

    return root;
}

private void insert(Node root, Node node) {
    if (root.x > node.x) {
        if (root.left == null)
            root.left = node;
        else
            insert(root.left, node);
    }
    else {
        if (root.right == null)
            root.right = node;
        else
            insert(root.right, node);
    }
}
```

위 과정들을 통해 이진 트리가 완전히 구성되었으면 다음은 전위 순회, 후위 순회를 진행할 타이밍이다. 재귀 함수를 통해 탐색을 수행하고, 탐색한 점들의 번호를 차례대로 배열에 저장하도록 한다.

```java
private void preOrder(Node root, ArrayList<Integer> values) {
    if (root == null) return;

    values.add(root.value);
    preOrder(root.left, values);
    preOrder(root.right, values);
}

private void postOrder(Node root, ArrayList<Integer> values) {
    if (root == null) return;

    postOrder(root.left, values);
    postOrder(root.right, values);
    values.add(root.value);
}
```

### 전체 코드

```java
import java.util.*;

class Solution {
    private static class Node {
        private int x;
        private int y;
        private int value;

        private Node left;
        private Node right;

        public Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    private Node construct(Node[] nodes) {
        Node root = nodes[0];

        for (int i = 1; i < nodes.length; i++) {
            insert(root, nodes[i]);
        }

        return root;
    }

    private void insert(Node root, Node node) {
        if (root.x > node.x) {
            if (root.left == null)
                root.left = node;
            else
                insert(root.left, node);
        }
        else {
            if (root.right == null)
                root.right = node;
            else
                insert(root.right, node);
        }
    }

    private void preOrder(Node root, ArrayList<Integer> values) {
        if (root == null) return;

        values.add(root.value);
        preOrder(root.left, values);
        preOrder(root.right, values);
    }

    private void postOrder(Node root, ArrayList<Integer> values) {
        if (root == null) return;

        postOrder(root.left, values);
        postOrder(root.right, values);
        values.add(root.value);
    }

    public int[][] solution(int[][] nodeinfo) {
        int[][] answer = new int[2][];

        Node[] nodes = new Node[nodeinfo.length];
        for (int i = 0; i < nodeinfo.length; i++) {
            nodes[i] = new Node(nodeinfo[i][0] , nodeinfo[i][1], i + 1);
        }
        Arrays.sort(nodes, (a, b) -> b.y - a.y);

        Node root = construct(nodes);

        ArrayList<Integer> preOrderValues = new ArrayList<>();
        ArrayList<Integer> postOrderValues = new ArrayList<>();
        preOrder(root, preOrderValues);
        postOrder(root, postOrderValues);

        answer[0] = preOrderValues.stream().mapToInt(i -> i).toArray();
        answer[1] = postOrderValues.stream().mapToInt(i -> i).toArray();

        return answer;
    }
}
```