package jameskealanthirdyearproject.communalcosts_client_app;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("jameskealanthirdyearproject.communalcosts_client_app", appContext.getPackageName());
    }

    /**
     * Created by kealan on 03/03/18.
     */
    public static class SettingsActivityTest {
        @Before
        public void setUp() throws Exception {
        }

        @After
        public void tearDown() throws Exception {
        }

        @Test
        public void onCreate() throws Exception {
        }

        @Test
        public void onOptionsItemSelected() throws Exception {
        }

        @Test
        public void sendFeedback() throws Exception {
        }

    }
}
