import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.LinkedList;
import java.util.Queue;

public class TreeDrawer {
    private static final int MAX_DEPTH = 5;
    private static Graph graph = new SingleGraph("Tree");

    public static void draw(TreeNode treeNode) {
        graph.setAttribute("ui.stylesheet", "url('./src/main/java/stylesheet.css')");
        Node root = graph.addNode("" + treeNode.hashCode());
        root.setAttribute("ui.label", treeNode.toString());
        traverseBFS(treeNode);
        System.setProperty("org.graphstream.ui", "swing");
        graph.display().setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
        graph.setAttribute("ui.screenshot", "./screenshot.png");

    }

    private static void traverse(TreeNode parent, int depth) {
        if (depth > MAX_DEPTH)
            return;


        TreeNode leftChild = parent.leftChild;
        if (leftChild != null) {
            graph.addNode("" + leftChild.key);
            Node leftNode = graph.getNode("" + leftChild.key);
            leftNode.setAttribute("ui.label", leftChild.toString());
            graph.addEdge(parent.key + "-" + leftChild.key, parent.key + "", leftChild.key + "");
            traverse(leftChild, depth + 1);
        }

        TreeNode rightChild = parent.rightChild;
        if (rightChild != null) {
            graph.addNode("" + rightChild.key);
            Node rightNode = graph.getNode("" + rightChild.key);
            rightNode.setAttribute("ui.label", rightChild.toString());
            graph.addEdge(parent.key + "-" + rightChild.key, parent.key + "", rightChild.key + "");
            traverse(rightChild, depth + 1);
        }

    }

    private static void traverseBFS(TreeNode root) {

        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> dp = new LinkedList<>();
        int ranks[] = new int[MAX_DEPTH];
        dp.add(0);
        q.add(root);
        while (!q.isEmpty()) {
            TreeNode parent = q.poll();
            int depth = dp.poll();
            TreeNode leftChild = parent.leftChild;

            if (leftChild != null && depth < MAX_DEPTH) {
                ranks[depth]++;
                Node leftNode = graph.addNode("" + leftChild.hashCode());
                leftNode.setAttribute("ui.label", leftChild.toString());

                graph.addEdge(parent.hashCode() + "-" + leftChild.hashCode(), parent.hashCode() + "", leftChild.hashCode() + "");
                q.add(leftChild);
                dp.add(depth + 1);
            }

            TreeNode rightChild = parent.rightChild;

            if (rightChild != null && depth < MAX_DEPTH) {
                ranks[depth]++;
                Node rightNode = graph.addNode("" + rightChild.hashCode());
                rightNode.setAttribute("ui.label", rightChild.toString());
                graph.addEdge(parent.hashCode() + "-" + rightChild.hashCode(), parent.hashCode() + "", rightChild.hashCode() + "");
                q.add(rightChild);
                dp.add(depth + 1);
            }
        }
    }
}
