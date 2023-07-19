import java.util.LinkedList;
import java.util.Queue;

public class Search {
    TreeNode treeNode;
    public Search(TreeNode treeNode) {
        this.treeNode=treeNode;
    }
    public Search(){

    }
    public String search(String word){
        Queue<TreeNode> nodeQueue = new LinkedList<>();

        if (treeNode == null) return null;

        nodeQueue.add(treeNode);

        while (!nodeQueue.isEmpty()) {

            TreeNode node = nodeQueue.remove();

            if (word.equals(node.word)) return node.meaning;

            if (node.rightChild != null) {
                nodeQueue.add(node.rightChild);
            }
            if (node.leftChild != null) {
                nodeQueue.add(node.leftChild); }
        }
        return "?";
    }

}

