package name.lemerdy;

import java.util.Random;

public class RandomQuestions implements Questions {
    private final Random random;

    public RandomQuestions(Random random) {
        this.random = random;
    }

    @Override
    public Question next(int size) {
        int firstIndex = random.nextInt(size);
        int secondIndex;
        do {
            secondIndex = random.nextInt(size);
        } while (secondIndex == firstIndex);
        boolean answerIsFirst = random.nextBoolean();

        return new Question(firstIndex, secondIndex, answerIsFirst);
    }
}
