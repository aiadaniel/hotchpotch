package com.vigorous.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vigorous.R;
import com.vigorous.adapter.ForumPagerAdapter;
import com.vigorous.entity.Board;
import com.vigorous.fragments.ForumFragment;
import com.vigorous.network.Retrofit2Mgr;
import com.vigorous.utils.Constant;
import com.vigorous.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private ImageView mIvAvatar;
    private TextView mTvNickname, mTvDesc;

    private List<Board> mBoards = new ArrayList<>();
    List<String> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*控制侧滑菜单*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /*菜单的选择事件*/
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);//ll
        mIvAvatar = (ImageView) headerView.findViewById(R.id.iv_content);
        mTvNickname = (TextView) headerView.findViewById(R.id.tv_nickname);
        mTvDesc = (TextView) headerView.findViewById(R.id.tv_desc);
        mIvAvatar.setOnClickListener(this);


        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //// TODO: 2017/5/12
        mBoards.add(new Board(2 + "", "娱乐"));
        mBoards.add(new Board(3 + "", "资讯"));
        initFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String token = SpUtils.get(Constant.LAST_TOKEN);
        if (token != null) {
            //try login using token
            Retrofit2Mgr.getInstance(Constant.HOTCH_USER).loginByToken(token, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.d(TAG, "login with token " + response.code());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        initProfile();
    }

    private void initFragments() {
        mFragments.clear();
        mTitles.clear();
        for (Board b : mBoards) {
            ForumFragment fragment = new ForumFragment();
            Bundle bundle = new Bundle();
            bundle.putString(ForumFragment.BUNDLE_BOARDID, b.getBoardid());
            bundle.putString(ForumFragment.BUNDLE_BOARDNAME, b.getBoardName());
            fragment.setArguments(bundle);
            mFragments.add(fragment);
            mTitles.add(b.getBoardName());
        }
        initViewPager();
    }

    private void initViewPager() {
        ForumPagerAdapter adapter = new ForumPagerAdapter(getSupportFragmentManager(), mTitles, mFragments);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //todo need to adjust tab width
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "on page selected " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initProfile() {
        if (SpUtils.get(Constant.LAST_AVATAR) != null)
            Glide.with(MainActivity.this).load(Constant.HOTCH_FILE + "down?filePath=" + SpUtils.get(Constant.LAST_AVATAR))
                    .asBitmap()
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(mIvAvatar);
        if (SpUtils.get(Constant.LAST_NAME) != null)
            mTvNickname.setText(SpUtils.get(Constant.LAST_NAME));
        if (SpUtils.get(Constant.LAST_UID) != null)
            mTvDesc.setText(SpUtils.get(Constant.LAST_UID));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_content:
                //how to adjust if token exist
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
