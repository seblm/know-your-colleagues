package name.lemerdy;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.stream.IntStream.range;

public class PredictableQuestions implements Questions {
    private final List<Question> questions;

    private int currentQuestionIndex;

    public PredictableQuestions(String questions) {
        String[] questionsAsStrings = questions.split("\\s+");
        if (questionsAsStrings.length % 3 != 0) {
            throw new IllegalArgumentException(format("number of arguments has to be a multiple of 3 but are actually %d", questionsAsStrings.length));
        }
        this.questions = range(0, questionsAsStrings.length / 3)
                .mapToObj(i -> new Question(
                        parseInt(questionsAsStrings[i * 3]),
                        parseInt(questionsAsStrings[i * 3 + 1]),
                        parseBoolean(questionsAsStrings[i * 3 + 2])))
                .collect(Collectors.<Question>toList());
        this.currentQuestionIndex = 0;
    }

    @Override
    public Question next(int size) {
        return questions.get(currentQuestionIndex++ % questions.size());
    }
}
