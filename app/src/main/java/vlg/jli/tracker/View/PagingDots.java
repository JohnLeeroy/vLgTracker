package vlg.jli.tracker.View;

/**
 * Created by johnli on 8/18/14.
 * PagingDots
 *  Controls and animates the five dots below the feed preview fragment
 *  Takes in an adapter and handles the rest
 *  Only handles 4 dots.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import vlg.jli.tracker.R;

public class PagingDots extends RelativeLayout {

    LinearLayout dotsContainer;
    ImageView[] dots;
    int dotCount;

    PagerAdapter pageAdapter;

    public PagingDots(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_paging_dots, this, true);

        Bind();
    }

    public PagingDots(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_paging_dots, this, true);

        Bind();
    }

    public PagingDots(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_paging_dots, this, true);

        Bind();
    }

    void Bind()
    {
        dotsContainer = (LinearLayout) findViewById(R.id.dots_container);

        dotCount = dotsContainer.getChildCount();
        dots = new ImageView[dotCount];

        for(int i = 0; i < dotCount; i++)
        {
            dots[i] = (ImageView) dotsContainer.getChildAt(i);
        }
    }

    void updateDots(int currentPage, int maxPages)
    {
        for(int i = 0; i < dotCount; i++)
        {
            dots[i].setAlpha(1f);
            dots[i].setColorFilter(Color.WHITE);
        }

        if(currentPage > 0) {
            dots[currentPage - 1].setColorFilter(getResources().getColor(R.color.theme));
        }

        if(currentPage == 0 || currentPage == (maxPages-1))
        {
            hideDots();
        }
        else
            showDots();
    }

    public void showDots()
    {
        dotsContainer.setVisibility(View.VISIBLE);
    }

    public void hideDots()
    {
        dotsContainer.setVisibility(View.INVISIBLE);
    }

    public void setAdapter(PagerAdapter adapter)
    {
        pageAdapter = adapter;
    }

    public void onPageScrolled(int position) {
        int pageCount = pageAdapter.getCount();
        updateDots(position, pageCount);
    }
}
