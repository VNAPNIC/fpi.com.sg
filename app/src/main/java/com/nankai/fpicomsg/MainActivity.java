package com.nankai.fpicomsg;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nankai.fpicomsg.customview.FontTextView;
import com.nankai.fpicomsg.menu.GalleryFragment;
import com.nankai.fpicomsg.menu.HomeFragment;
import com.nankai.fpicomsg.menu.MoreFragment;
import com.nankai.fpicomsg.menu.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager contentView;
    private TabLayout tabMenu;
    private MenuAdapter menuAdapter;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        contentView = (ViewPager) findViewById(R.id.content);
        tabMenu = (TabLayout) findViewById(R.id.tab_menu);

        menuAdapter = new MenuAdapter(getSupportFragmentManager());
        contentView.setAdapter(menuAdapter);
        List<Fragment> fragments = addFragment(HomeFragment.newInstantiate(), ServiceFragment.newInstantiate(),
                GalleryFragment.newInstantiate(), MoreFragment.newInstantiate());
        menuAdapter.setFragments(fragments);
        menuAdapter.notifyChanged();

        tabMenu.setupWithViewPager(contentView);
        setTitleMenu(4);
    }

    private List<Fragment> addFragment(Fragment... fragments) {
        List<Fragment> fragmentList = new ArrayList<>();

        for (int i = 0; i < fragments.length; i++) {
            fragmentList.add(fragments[i]);
        }
        return fragmentList;
    }

    private void setTitleMenu(int size) {
        for (int i = 0; i < size; i++) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_menu, null);
            ImageView iconMenu = (ImageView) view.findViewById(R.id.icon_menu);
            FontTextView titleMenu = (FontTextView) view.findViewById(R.id.title_menu);
            if (i == 0) {
                iconMenu.setImageResource(R.drawable.home);
                titleMenu.setText(getResources().getString(R.string.menu_home));
            } else if (i == 1) {
                iconMenu.setImageResource(R.drawable.service);
                titleMenu.setText(getResources().getString(R.string.menu_service));
            } else if (i == 2) {
                iconMenu.setImageResource(R.drawable.gallery);
                titleMenu.setText(getResources().getString(R.string.menu_gallery));
            } else if (i == 3) {
                iconMenu.setImageResource(R.drawable.more);
                titleMenu.setText(getResources().getString(R.string.menu_more));
            }
            tabMenu.getTabAt(i).setCustomView(view);
        }
    }

    private class MenuAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MenuAdapter(FragmentManager fm) {
            super(fm);
            this.fragments = new ArrayList<>();

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragments.get(position);
            return fragment;
        }

        public void setFragments(List<Fragment> fragments) {
            if (fragments == null) {
                fragments = new ArrayList<>();
            } else {
                this.fragments.clear();
            }
            this.fragments.addAll(fragments);
        }

        public void notifyChanged() {
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (fragments == null)
                return 0;
            return fragments.size();
        }
    }
}
