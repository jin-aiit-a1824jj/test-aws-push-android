package aws_push_notification.group4.amazonmobilepush;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private WebView mWebView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseInstanceId.getInstance().getToken();

        if (FirebaseInstanceId.getInstance().getToken() != null) {
            Log.d(TAG, "token = " + FirebaseInstanceId.getInstance().getToken());
        }

        setContentView(R.layout.layout);

        //
        this.mWebView = (WebView)findViewById(R.id.webView);
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("check URL",url);
                view.loadUrl(url);
                return true;
            }
        });
        this.mWebView.setWebChromeClient(new WebChromeClient());
        //this.mWebView.loadUrl("https://aiit.ac.jp/");
        this.mWebView.loadUrl("http://ec.aiit-group4.work/");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
