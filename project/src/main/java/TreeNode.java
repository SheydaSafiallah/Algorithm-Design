
public class TreeNode{

    int key;
    TreeNode rightChild;
    TreeNode leftChild;
    String word;
    String meaning;

    public TreeNode(int key, TreeNode rightChild, TreeNode leftChild) {
        this.key = key;
        this.rightChild = rightChild;
        this.leftChild = leftChild;
    }

    public TreeNode() {
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    @Override
    public String toString() {
        return  "{"+key +
                ",\'" + word + '\'' +
                "='" + meaning + '\'' +
                '}';
    }

}

