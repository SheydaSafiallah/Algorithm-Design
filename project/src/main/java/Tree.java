import java.util.List;

public class Tree {
    private int [][] keys;
    private int i;
    private int j;
    List<Node> nodes;
    public Tree(int[][] keys , int i , int j , List<Node> nodes){
        this.keys = keys;
        this.i = i;
        this.j = j;
        this.nodes = nodes;
    }
    public TreeNode treeMaker(int keys[][] , int i , int j){
        int key = keys[j][i];
        TreeNode treeNode = new TreeNode();
        if (key != 0) {
            treeNode.setWord(nodes.get(key - 1).getWord());
            treeNode.setMeaning(nodes.get(key - 1).getMeaning());
            treeNode.key = key;
            treeNode.leftChild = null;
            treeNode.rightChild = null;
            /// set rightChild
            if (i < j) {
                if (key > i && key < j) {
                    if (key - 1 != 0) {
                        treeNode.rightChild = treeMaker(keys, i, key - 1);
                    }
                }
            }
            /// set leftChild
            if (key < j) {
                treeNode.leftChild = treeMaker(keys, key, j);

            }
        }
        return treeNode; //full tree
    }



}

