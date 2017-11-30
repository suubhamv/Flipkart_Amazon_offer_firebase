package com.ssvmakers.amzonew.autobuynew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ssvmakers.amzonew.autobuynew.Fragments.ChatFragment;
import com.ssvmakers.amzonew.autobuynew.Fragments.ContactUsFragment;
import com.ssvmakers.amzonew.autobuynew.Fragments.HomeFragment;
import com.ssvmakers.amzonew.autobuynew.Fragments.OfferBySite;
import com.ssvmakers.amzonew.autobuynew.Fragments.PostFragment;

import java.util.ArrayList;

/**
 *
 */
public class DemoViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public DemoViewPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments.clear();
        fragments.add(HomeFragment.newInstance("0"));
        fragments.add(OfferBySite.newInstance("", ""));
      //  fragments.add(PostFragment.newInstance("", ""));
        fragments.add(ChatFragment.newInstance("", ""));
        fragments.add(ContactUsFragment.newInstance("", ""));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {

        }
        super.setPrimaryItem(container, position, object);
    }

    /**
     * Get the current fragment
     */
    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}