package vlg.jli.tracker.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vlg.jli.tracker.R;

/**
 * Created by johnli on 2/21/15.
 */
public class UserCardStatBarView extends LinearLayout {
    public TextView tvKills;
    public TextView tvSkill;
    public TextView tvRank;

    public UserCardStatBarView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_user_card_stat_bar, this, true);

        bind();
    }

    public UserCardStatBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_user_card_stat_bar, this, true);

        bind();
    }

    public UserCardStatBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_user_card_stat_bar, this, true);

        bind();
    }

    void bind()
    {
        tvKills = (TextView)findViewById(R.id.card_kill_count);
        tvSkill = (TextView)findViewById(R.id.card_skill);
        tvRank = (TextView)findViewById(R.id.card_rank);
    }

}
