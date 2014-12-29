package vlg.jli.tracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

/**
 * Created by johnli on 11/28/14.
 */
public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_about, container, false);
        WebView webView = (WebView)rootView.findViewById(R.id.about_webview);
        webView.setWebViewClient(new WebViewClient());                      //prevents transferring control to default browser
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl("http://vlgsite.com/forum/");
        return rootView;
    }
}
