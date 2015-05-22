package vlg.jli.tracker.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vlg.jli.tracker.R;

/**
 * Created by johnli on 5/21/15.
 */
public class PlayerSummaryView extends LinearLayout {
    public TextView tvKills;
    public TextView tvSkill;
    public TextView tvRank;
    public TextView tvName;
    public ImageView ivPicture;

    public PlayerSummaryView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_player_summary, this, true);

        bind();
    }

    public PlayerSummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_player_summary, this, true);

        bind();
    }

    public PlayerSummaryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_player_summary, this, true);

        bind();
    }

    void bind()
    {
        tvKills = (TextView)findViewById(R.id.summary_kill_count);
        tvSkill = (TextView)findViewById(R.id.summary_skill);
        tvRank = (TextView)findViewById(R.id.summary_rank);
        tvName = (TextView)findViewById(R.id.summary_name);
        ivPicture = (ImageView)findViewById(R.id.user_picture);
    }
}
