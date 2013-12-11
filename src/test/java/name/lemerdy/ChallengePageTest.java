package name.lemerdy;

import name.lemerdy.misc.PhantomJsTest;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ChallengePageTest extends PhantomJsTest {
    @ClassRule
    public static TomcatRule tomcat = new TomcatRule("0 1 true 2 3 false");

    @Override
    protected String defaultUrl() {
        return "http://localhost:8080";
    }

    @Before
    public void restartUI() throws Exception {
        goTo("/");

        await().until("#name").withText().contains(Pattern.compile("[Alcatel Ephone|Scritchy Scratchy]"));

        if ("Scritchy Scratchy".equals($("#name").getText())) {
            restartUI(); // servlet is statefull : we need to reset UI as it was when servlet was started for the first time
        }
    }

    @Test
    public void should_have_a_title() throws Exception {
        assertThat(title()).isEqualTo("Know Your Colleagues");
        assertThat($("h1").getText()).isEqualTo("Know Your Colleagues");
    }

    @Test
    public void should_display_images_name_and_initial_score() throws Exception {
        assertThat($("#firstImage").find("img").getAttribute("src")).endsWith("/images/Alcatel%20Ephone.jpg");
        assertThat($("#secondImage").find("img").getAttribute("src")).endsWith("/images/Angolina%20Jelly.jpg");
        assertThat($("#name").getText()).isEqualTo("Alcatel Ephone");
        assertThat($("#score").getText()).isEqualTo("0");
    }

    @Test
    public void should_click_on_first_image_and_display_next_challenge() throws Exception {
        click("#firstImage");
        await().until("#name").withText().equalTo("Anne Beauchart");

        assertThat($("#score").getText()).isEqualTo("1");
        assertThat($("#firstImage").find("img").getAttribute("src")).endsWith("/images/Jean-Paul%20Geeky.jpg");
        assertThat($("#secondImage").find("img").getAttribute("src")).endsWith("/images/Scritchy%20Scratchy.jpg");
        assertThat($("#name").getText()).isEqualTo("Scritchy Scratchy");
    }

    @Test
    public void should_click_on_good_image_and_win() throws Exception {
        click("#firstImage");
        await().until("#name").withText().equalTo("Scritchy Scratchy");

        assertThat($("#score").getText()).isEqualTo("1");
    }

    @Test
    public void should_click_on_bad_image_and_loose() throws Exception {
        click("#secondImage");
        await().until("#name").withText().equalTo("Scritchy Scratchy");

        assertThat($("#score").getText()).isEqualTo("-1");
    }
}
