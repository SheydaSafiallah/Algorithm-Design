public class TimeMeasure {
    private final String context;
    private long startTotal;

    public TimeMeasure(String context) {
        this.context = context;
    }

    public void start() {
        startTotal = System.currentTimeMillis();
    }

    public void end() {
        long endTotal = System.currentTimeMillis();
        System.out.println(context + ": " + (endTotal - startTotal) + "ms");
    }
}

