package com.addtext.textonphoto.textart.TART_viewadapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class TART_ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return -2;
    }

    public TART_ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager, 1);
        this.mFragmentList = new ArrayList();
        this.mFragmentTitleList = new ArrayList();
    }

//    @OverridePagerAdapter
    public Fragment getItem(int i) {
        return this.mFragmentList.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String str) {
        this.mFragmentList.add(fragment);
        this.mFragmentTitleList.add(str);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public CharSequence getPageTitle(int i) {
        return this.mFragmentTitleList.get(i);
    }
}
