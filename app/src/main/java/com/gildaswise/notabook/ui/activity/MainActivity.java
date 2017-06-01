package com.gildaswise.notabook.ui.activity;

import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.gildaswise.notabook.R;
import com.gildaswise.notabook.databinding.ActivityMainBinding;
import com.gildaswise.notabook.ui.fragment.AgendaFragment;
import com.gildaswise.notabook.ui.fragment.MainActivityFragment;
import com.gildaswise.notabook.ui.fragment.SubjectFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SectionsPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setSupportActionBar(binding.toolbar);
        setupUI();
    }

    private void setupUI() {
        setupTabs();
        setupTheme();
        setupFAB();
    }

    private void setupFAB() {
        binding.fab.setOnClickListener(v -> {
            int currentTab = binding.tabLayout.getSelectedTabPosition();
            //https://stackoverflow.com/a/22177319
            MainActivityFragment fragment = pagerAdapter.getFragment(currentTab);
            fragment.onFloatingActionButtonClick();
        });
    }

    private void setupTabs() {
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    private void setupTheme() {

    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new SubjectFragment();
                case 1:
                    return new AgendaFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.subjects);
                case 1:
                    return getString(R.string.agenda);
                default:
                    return null;
            }
        }

        public MainActivityFragment getFragment(int position) {
            return (MainActivityFragment) instantiateItem(null, position);
        }
    }

}
