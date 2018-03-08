package jameskealanthirdyearproject.communalcosts_client_app;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

public class DeleteFCMTokenService extends IntentService {
    public static final String TAG = DeleteFCMTokenService.class.getSimpleName();
    // TODO: Rename parameters

    public DeleteFCMTokenService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String FCM_TOKEN = intent.getStringExtra("FCM_TOKEN");
        try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
            Log.d(TAG, "Deleted the token. Old Value: " + FCM_TOKEN);
        } catch (IOException e) {
            Log.d(TAG, "ERROR Token not deleted.");
            e.printStackTrace();
        }

    }
}
