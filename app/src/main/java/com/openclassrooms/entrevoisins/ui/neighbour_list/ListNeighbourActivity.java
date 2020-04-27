package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListNeighbourActivity extends AppCompatActivity {
    //-- PROPERTIES
    // UI
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;

    // ADAPTER
    ListNeighbourPagerAdapter mPagerAdapter;

    // FRAGMENTS
    NeighbourFragment neighbourFragment;
    NeighbourFragment favoriteNeighbourFragment;

    // LISTS
    List<Neighbour> listNeighbours = new ArrayList<>();
    List<Neighbour> listFavoritesNeighbours = new ArrayList<>();

    // API SERVICE
    NeighbourApiService mApiService;

    // START ACTIVITY FOR RESULT
    public static final int LAUNCH_SECOND_ACTIVITY = 0;


    //-- VIEW LIFE CYCLE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiService = DI.getNeighbourApiService();

        listNeighbours = mApiService.getNeighbours();

        setContentView(R.layout.activity_list_neighbour);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        setupViewPager();

    }

    //-- METHODS
    /**
     * Setup the view pager with the fragments.
     */
    private void setupViewPager() {
        neighbourFragment = NeighbourFragment.newInstance(listNeighbours);
        favoriteNeighbourFragment = NeighbourFragment.newInstance(listFavoritesNeighbours);

        mPagerAdapter = new ListNeighbourPagerAdapter(getSupportFragmentManager());
        mPagerAdapter.addFragment(neighbourFragment,"MY NEIGHBOURS");
        mPagerAdapter.addFragment(favoriteNeighbourFragment,"FAVORITES");

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Neighbour favoriteNeighbour = (Neighbour) data.getSerializableExtra("favoriteNeighbour");

                checkFavoriteNeighbour(favoriteNeighbour, listNeighbours, listFavoritesNeighbours);

                favoriteNeighbourFragment.updateList(listFavoritesNeighbours);
            }
        }
    }

    // UPDATE FAVORITE NEIGHBOURS LIST
    public static void checkFavoriteNeighbour(Neighbour favoriteNeighbour, List<Neighbour> listNeighbours, List<Neighbour> listFavoritesNeighbours) {
        if (favoriteNeighbour.getFavorite()) {
            if (!listFavoritesNeighbours.contains(favoriteNeighbour)) {
                listFavoritesNeighbours.add(favoriteNeighbour);
            }

            listNeighbours.set(listNeighbours.indexOf(favoriteNeighbour), favoriteNeighbour);
        } else {
            listFavoritesNeighbours.remove(favoriteNeighbour);

            listNeighbours.set(listNeighbours.indexOf(favoriteNeighbour), favoriteNeighbour);
        }
    }

    //-- ON CLICK
    @OnClick(R.id.add_neighbour)
    void addNeighbour() {
        AddNeighbourActivity.navigate(this);
    }

}
