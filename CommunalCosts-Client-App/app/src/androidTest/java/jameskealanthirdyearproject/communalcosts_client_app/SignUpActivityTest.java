package jameskealanthirdyearproject.communalcosts_client_app;

import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by kealan on 06/03/18.
 */
public class SignUpActivityTest {

    String email = "example@mail.com";
    String passwd = "secure";

    @Rule
    public ActivityTestRule<SignUpActivity> mActivityRule =
            new ActivityTestRule<>(SignUpActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void registerUser() throws Exception {
        onView(withId(R.id.SignUp_EmailField)).perform(typeText(email));
        onView(withId(R.id.SignUp_PasswordField)).perform(typeText(passwd));
        onView(withId(R.id.SignUp_SignUpBtn)).perform(click());
    }

    @Test
    public void onClick() throws Exception {
    }

}