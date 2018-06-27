package org.incoder.security;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * FragmentPagerAdapter
 *
 * @author Jerry xu
 * @date 2018/06/14 10:31.
 */
public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mTitles;

    FragmentPagerAdapter(FragmentManager fm,
                         List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    FragmentPagerAdapter(FragmentManager fm,
                         List<Fragment> fragments, List<String> titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}

