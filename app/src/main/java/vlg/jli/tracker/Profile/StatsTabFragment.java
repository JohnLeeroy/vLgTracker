package vlg.jli.tracker.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import vlg.jli.tracker.AsyncListener;
import vlg.jli.tracker.GameME.GameMEAPI;
import vlg.jli.tracker.GameME.GameMECache;
import vlg.jli.tracker.Model.User;
import vlg.jli.tracker.R;
import vlg.jli.tracker.View.PlayerSummaryView;

/**
 * Created by johnli on 12/1/14.
 */
public class StatsTabFragment extends Fragment {
    User currentUser;

    GameMECache cache;
    PlayerSummaryView playerSummaryView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_profile_user, container, false);
        cache = GameMECache.getInstance(getActivity());
        playerSummaryView = (PlayerSummaryView)rootView.findViewById(R.id.player_summary);

        init();
        return  rootView;
    }

    void init()
    {
        User me = cache.mainUser;
        if(me == null) {
            me = User.getRed();
        }
        setUser(me);
    }

    public void setUser(User user)
    {
        if(user == null)
            return;

        Ion.with(getActivity()).load(user.avatar).withBitmap().intoImageView(playerSummaryView.ivPicture);
        playerSummaryView.tvKills.setText(String.valueOf(user.kills));
        playerSummaryView.tvSkill.setText(String.valueOf(user.skill));
        playerSummaryView.tvRank.setText(String.valueOf(user.rank));
        playerSummaryView.tvName.setText(user.name);
    }
}
