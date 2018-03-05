package jameskealanthirdyearproject.communalcosts_client_app;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by C5228122 on 03/03/2018.
 */
public class HomeCollectiveViewTest {

    @Test
    public void testLaunch(){
        View view = mActv.findViewById(R.id.addCollectiveBtn);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        mActv = null;
    }



}