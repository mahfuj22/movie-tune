package com.example.mamshad.movietune;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import models.GlobalData;

public class SelectedMovieActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView farmerImageView;

    private RecyclerView horizontal_recycler_view;
    private ArrayList<HorizontalItem> horizontalList;
    private SelectedMovieInfoFragment.HorizontalAdapter horizontalAdapter;

    private RecyclerView mRecyclerView;
    private HorizontalViewAdapter mHorizontalAdapter;

    private String FEED_URL = "http://stacktips.com/?json=get_recent_posts&count=45";

    private static String jsonUrl = "https://api.themoviedb.org/3/movie/259316/similar?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private static String jsonPartialUrl = "https://api.themoviedb.org/3/movie/";
    private static String jsonUrlApiKey = "/similar?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private static String jsonFullUrl = "";

    private static String imagePartialUrl = "http://image.tmdb.org/t/p/w500";
    private static String urlApiKey = "?api_key=c37d3b40004717511adb2c1fbb15eda4";
    private static String imageFullUrl = "";

    private ProgressBar mProgressBar;
    private ProgressDialog pDialog;

    TextView productionCompany;
    TextView productionCountry;
    TextView budget;
    TextView language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.abs_layout);

        farmerImageView = (ImageView) findViewById(R.id.header);

        init();
    }

    /**
     * Initialization
     */
    private void init() {
        Intent i = getIntent();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        final Animation zoomInAnim = android.view.animation.AnimationUtils.loadAnimation(
                this, R.anim.zoomin);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        imageFullUrl = imagePartialUrl+ GlobalData.selectedMovieInfo.getBackdropPath()+urlApiKey;

        Picasso.with(getApplicationContext()).load(imageFullUrl).into(farmerImageView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SelectedMovieInfoFragment(), "");
        viewPager.setAdapter(adapter);
    }

    /**
     * Implementation of PagerAdapter that represents each page as a Fragment
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
