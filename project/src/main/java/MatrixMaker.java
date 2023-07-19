import java.util.List;

public class MatrixMaker {
    private List<Node> nodes;

    private double weights(int i, int j) {
        double sum = 0.0;
        for (int l = i; l > j; l--) {
            sum += nodes.get(l - 1).getProbability();
        }
        return sum;
    }

    public MatrixMaker(List<Node> nodes) {
        this.nodes = nodes;
    }

    public int[][] keyMaker() {

        double[][] cost = new double[nodes.size() + 1][]; ///nosed.size and null
        int[][] keys = new int[nodes.size() + 1][]; ///nosed.size and null

        int m = (nodes.size()) - 2; //for keys
        int key;

        for (int i = 0; i <= nodes.size(); i++) { //main cost diagonal = 0
            cost[i] = new double[i + 1];
            cost[i][i] = 0.0;
        }

        for (int i = 1; i <= nodes.size(); i++) { // next diagonal -> main cost probability
            cost[i][i - 1] = nodes.get(i - 1).getProbability();
        }


        for (int i = 0; i <= nodes.size(); i++) { //main key diagonal = 0
            keys[i] = new int[i + 1];
            keys[i][i] = 0;
        }

        for (int i = 0; i < nodes.size(); i++) {//next diagonal -> key instead of probability
            keys[i + 1][i] = i + 1;
        }


        for (int j = 2; j <= nodes.size(); j++) { /// make cost & keys array
            for (int i = 0; i <= m; i++) {
                double currentMinimumCost = Double.MAX_VALUE;
                double weight = weights(i + j, i);

                for (int k = i + 1; k <= j + i; k++) {
                    cost[i + j][i] = cost[k - 1][i] + cost[j + i][k] + weight;

                    if (cost[i + j][i] < currentMinimumCost) {
                        currentMinimumCost = cost[i + j][i];
                        key = k;
                        keys[i + j][i] = key;
                    }
                }
            }
            m--;

        }

        return keys;
    }


}
