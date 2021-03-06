package vlg.jli.tracker.Intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import vlg.jli.tracker.Intro.IntroPageFragment;
import vlg.jli.tracker.R;
import vlg.jli.tracker.View.PagingDots;

public class IntroActivity extends FragmentActivity {

    ViewPager pager;
    PagingDots pagingDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        pager = (ViewPager)findViewById(R.id.intro_view_pager);
        pagingDots = (PagingDots)findViewById(R.id.paging_dots);

        FragmentStatePagerAdapter adapter = new IntroPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pagingDots.setAdapter(adapter);
        pagingDots.hideDots();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                pagingDots.onPageScrolled(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    class IntroPagerAdapter extends FragmentStatePagerAdapter {

        public IntroPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i)
            {
                case 0: {
                    return constructIntroFragment( R.layout.view_intro);
                }
                case 1: {
                    return constructIntroFragment( R.layout.view_intro_stats);
                }
                case 2: {
                    return constructIntroFragment( R.layout.view_intro_servers);
                }
                case 3: {
                    return constructIntroFragment( R.layout.view_intro_measure);
                }
                case 4: {
                    return new SignupPageFragment();
                }
            }
            return null;
        }

        private Fragment constructIntroFragment(int layoutId)
        {
            IntroPageFragment page = new IntroPageFragment();
            page.setLayoutId(layoutId);
            return page;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
