package vlg.jli.tracker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
        webView.loadUrl("http://vlgsite.com/forum/");
        return rootView;
    }

}
