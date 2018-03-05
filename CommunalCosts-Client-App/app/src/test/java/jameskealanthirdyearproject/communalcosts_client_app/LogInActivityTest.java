package jameskealanthirdyearproject.communalcosts_client_app;

import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kealan on 04/03/18.
 */
public class LogInActivityTest {

    @Rule
    public ActivityTestRule<LogInActivity> rule = new ActivityTestRule<LogInActivity>(LogInActivity.class);
    public LogInActivity myAct = null;

    @Test
    public void onCreateInstantiatesVariablesCorrectly(){
        LogInActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.LogIn_LogInBtn);
        assertThat(viewById, viewById.isInstance(Button.class));
    }

    @Test
    public void onClick() throws Exception {
        userLogin()
    }

}