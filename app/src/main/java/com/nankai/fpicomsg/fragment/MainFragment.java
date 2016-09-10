package com.nankai.fpicomsg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nankai.fpicomsg.BaseFragment;
import com.nankai.fpicomsg.MainActivity;
import com.nankai.fpicomsg.R;
import com.nankai.fpicomsg.customview.FontTextView;
import com.nankai.fpicomsg.fragment.gallery.GalleryFragment;
import com.nankai.fpicomsg.fragment.more.MoreFragment;
import com.nankai.fpicomsg.fragment.service.ServiceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nankai on 9/10/2016.
 */
public class MainFragment extends BaseFragment {
    private ViewPager contentView;
    private TabLayout tabMenu;
    private MenuAdapter menuAdapter;

    public static MainFragment newInstantiate() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setupView() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity)getActivity()).enableBack(false);
        contentView = (ViewPager) view.findViewById(R.id.content_fragment);
        tabMenu = (TabLayout) view.findViewById(R.id.tab_menu);
        menuAdapter = new MenuAdapter(getChildFragmentManager());
        contentView.setAdapter(menuAdapter);
        List<Fragment> fragments = addFragment(HomeFragment.newInstantiate(), ServiceFragment.newInstantiate(),
                GalleryFragment.newInstantiate(), MoreFragment.newInstantiate());
        menuAdapter.setFragments(fragments);
        menuAdapter.notifyChanged();

        tabMenu.setupWithViewPager(contentView);
        setTitleMenu(4);
        contentView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                String title = "";
                if (position == 0) {
                    ((MainActivity)getActivity()).enableBack(false);
                    title = getResources().getString(R.string.menu_home);
                } else if (position == 1) {
                    title = getResources().getString(R.string.menu_service);
                } else if (position == 2) {
                    ((MainActivity)getActivity()).enableBack(false);
                    title = getResources().getString(R.string.menu_gallery);
                } else if (position == 3) {
                    ((MainActivity)getActivity()).enableBack(false);
                    title = getResources().getString(R.string.menu_more);
                }
                ((MainActivity)getActivity()).setTitle(title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (isAdded())
            ((MainActivity) context).enableBack(false);
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_menu, null);
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
