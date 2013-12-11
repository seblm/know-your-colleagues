package name.lemerdy;

public class Question {
    private final int first;
    private final int second;
    private final boolean firstIsAnswer;

    public Question(int first, int second, boolean firstIsAnswer) {
        this.first = first;
        this.second = second;
        this.firstIsAnswer = firstIsAnswer;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public boolean isFirstIsAnswer() {
        return firstIsAnswer;
    }
}
