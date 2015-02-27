package vlg.jli.tracker.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import vlg.jli.tracker.R;

/**
 * Created by johnli on 12/25/14.
 */
public class ServerInfoRowView extends LinearLayout {
    public TextView tvProperty;
    public TextView tvValue;

    public ServerInfoRowView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_row_server_info, this, true);

        bind();
    }

    public ServerInfoRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_row_server_info, this, true);

        bind();
    }

    public ServerInfoRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_row_server_info, this, true);

        bind();
    }

    void bind()
    {
        tvProperty = (TextView)findViewById(R.id.server_info_row_property);
        tvValue = (TextView)findViewById(R.id.server_info_row_value);

        tvProperty.setTextColor(getResources().getColor(R.color.semi_white));
        tvValue.setTextColor(getResources().getColor(R.color.semi_white));
    }
}
