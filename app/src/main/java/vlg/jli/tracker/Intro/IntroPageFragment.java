package vlg.jli.tracker.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vlg.jli.tracker.R;

/**
 * Created by johnli on 3/25/15.
 */
public class IntroPageFragment extends Fragment{

    int layoutId;

    public void setLayoutId(int layoutId)
    {
        this.layoutId = layoutId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(layoutId, container, false);
        return v;
    }

}
