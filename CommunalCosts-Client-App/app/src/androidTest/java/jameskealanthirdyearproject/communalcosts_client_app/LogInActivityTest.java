package jameskealanthirdyearproject.communalcosts_client_app;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Created by kealan on 04/03/18.
 */
public class LogInActivityTest {

    String username = "testusername";
    String password = "testpassword";

    @Rule
    public ActivityTestRule<LogInActivity> mActivityRule =
            new ActivityTestRule<>(LogInActivity.class, true, true);
    @Test
    public void userLogin() throws Exception {
        mActivityRule.getActivity();
        onView(withId(R.id.LogIn_UserField)).perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.LogIn_PassField)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.LogIn_LogInBtn)).perform(click());

    }

}