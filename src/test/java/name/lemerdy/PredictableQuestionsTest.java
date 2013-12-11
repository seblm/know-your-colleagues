package name.lemerdy;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PredictableQuestionsTest {
    @Test
    public void should_read_one_question() throws Exception {
        PredictableQuestions predictableQuestions;

        predictableQuestions = new PredictableQuestions("0 1 true");

        assertThat(predictableQuestions.next(0)).isEqualToComparingFieldByField(new Question(0, 1, true));
    }

    @Test
    public void should_read_two_questions() throws Exception {
        PredictableQuestions predictableQuestions;

        predictableQuestions = new PredictableQuestions("0 1 true 3 5 false");

        assertThat(predictableQuestions.next(0)).isEqualToComparingFieldByField(new Question(0, 1, true));
        assertThat(predictableQuestions.next(0)).isEqualToComparingFieldByField(new Question(3, 5, false));
    }

    @Test
    public void should_read_two_questions_and_rewind() throws Exception {
        PredictableQuestions predictableQuestions = new PredictableQuestions("0 1 true 3 5 false");
        predictableQuestions.next(0);
        predictableQuestions.next(0);

        Question rewindedQuestion = predictableQuestions.next(0);

        assertThat(rewindedQuestion).isEqualToComparingFieldByField(new Question(0, 1, true));
    }

    @Test(expected = NumberFormatException.class)
    public void should_fail_if_first_not_integer() throws Exception {
        new PredictableQuestions("a 1 true");
    }

    @Test(expected = NumberFormatException.class)
    public void should_fail_if_second_not_integer() throws Exception {
        new PredictableQuestions("0 a true");
    }

    @Test
    public void should_create_question_with_false_if_value_is_not_true() throws Exception {
        PredictableQuestions predictableQuestions;

        predictableQuestions = new PredictableQuestions("0 1 vrai");

        assertThat(predictableQuestions.next(0)).isEqualToComparingFieldByField(new Question(0, 1, false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_fail_if_number_of_arguments_are_not_multiple_of_3() throws Exception {
        new PredictableQuestions("0 1 true 3");
    }

    @Test
    public void should_read_question_with_other_separators_than_space() throws Exception {
        PredictableQuestions predictableQuestions;

        predictableQuestions = new PredictableQuestions("0\t1\ntrue");

        assertThat(predictableQuestions.next(0)).isEqualToComparingFieldByField(new Question(0, 1, true));
    }
}
