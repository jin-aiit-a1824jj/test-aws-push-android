package aws_push_notification.group4.amazonmobilepush;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONObject;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;




public class CustomFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = CustomFirebaseInstanceIdService.class.getSimpleName();

    private static final String END_POINT = "https://4lbwnz74eg.execute-api.ap-northeast-1.amazonaws.com/group4-stage/add-user-subscript";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token = " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "token = " + token);
        final String f_token = token;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(END_POINT);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    //conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.connect();

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("os", "android");
                    jsonParam.put("token", f_token);


                    Log.i(TAG+"JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i(TAG+"STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i(TAG+"MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, e.toString());
                }
            }
        });

        thread.start();



    }

}
