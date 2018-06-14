package org.incoder.security;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity
 *
 * @author Jerry xu
 * @date 2018/06/14 10:31.
 */
public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_asymmetric:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_symmetric:
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_jwt:
                    mViewPager.setCurrentItem(2);
                    return true;
                default:
                    mViewPager.setCurrentItem(0);
                    break;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                navigation.setSelectedItemId(R.id.navigation_asymmetric);
            } else if (position == 1) {
                navigation.setSelectedItemId(R.id.navigation_symmetric);
            } else {
                navigation.setSelectedItemId(R.id.navigation_jwt);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragments = new ArrayList<>();
        mFragments.add(new AsymmetricFragment());
        mFragments.add(new SymmetricFragment());
        mFragments.add(new JwtFragment());
        navigation = findViewById(R.id.navigation);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
