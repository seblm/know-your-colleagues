package name.lemerdy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ChallengeTest {

    @Mock
    private Random random;

    @Test
    public void should_create_a_challenge() throws ServletException, IOException {
        when(random.nextInt(eq(2))).thenReturn(0, 1);
        when(random.nextBoolean()).thenReturn(true);

        Challenge challenge = new Challenge(new HashSet<>(asList("/images/a guy.jpg", "/images/some other guy.jpg")), new RandomQuestions(random));

        assertThat(challenge.getFirstImage()).as("firstImage").isEqualTo("/images/a guy.jpg");
        assertThat(challenge.getSecondImage()).as("secondImage").isEqualTo("/images/some other guy.jpg");
        assertThat(challenge.getName()).as("name").isEqualTo("a guy");
        assertThat(challenge.getAnswer()).as("answer").isEqualTo("firstImage");
    }

    @Test
    public void should_create_a_challenge_with_second_face_as_winner() throws ServletException, IOException {
        when(random.nextInt(eq(2))).thenReturn(0, 1);
        when(random.nextBoolean()).thenReturn(false);

        Challenge challenge = new Challenge(new HashSet<>(asList("/images/a guy.jpg", "/images/some other guy.jpg")), new RandomQuestions(random));

        assertThat(challenge.getFirstImage()).as("firstImage").isEqualTo("/images/a guy.jpg");
        assertThat(challenge.getSecondImage()).as("secondImage").isEqualTo("/images/some other guy.jpg");
        assertThat(challenge.getName()).as("name").isEqualTo("some other guy");
        assertThat(challenge.getAnswer()).as("answer").isEqualTo("secondImage");
    }
}
