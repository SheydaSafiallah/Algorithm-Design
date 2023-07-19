public class Node {
    private int index;
    private String word;
    private String meaning;
    private double probability;


    public Node( String[] row) {

        this.index = Integer.parseInt(row[0]);
        this.word = row[1];
        this.meaning = row[2];
        this.probability = Double.parseDouble(row[3]);
    }

    public int getIndex() {
        return index;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public double getProbability() {
        return probability;
    }



}
