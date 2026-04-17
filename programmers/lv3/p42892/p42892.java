package programmers.lv3.p42892;

import java.util.ArrayList;
import java.util.Arrays;

public class p42892 {
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

    public static void main(String[] args) {
        int[][] nodeinfo = {{5,3},{11,5},{13,3},{3,5},{6,1},{1,3},{8,6},{7,2},{2,2}};
        System.out.println(Arrays.deepToString(new p42892().solution(nodeinfo)));
    }
}
