package com.example.android.cloudy.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.cloudy.R;
import com.example.android.cloudy.adpaters.ViewPagerAdapter;
import com.example.android.cloudy.fragments.CurrentForecastFragment;
import com.example.android.cloudy.fragments.FavouriteFragment;
import com.example.android.cloudy.fragments.GoogleMapsFragment;
import com.example.android.cloudy.fragments.PlaceSelected;
import com.example.android.cloudy.fragments.WeeklyForecastFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InitialScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public CurrentForecastFragment currentForecastFragment = new CurrentForecastFragment();

    @BindView(R.id.my_toolbar)
    Toolbar MyToolbar;

    public Menu menuOptions;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
        ButterKnife.bind(this);

        setSupportActionBar(MyToolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        MyToolbar.setTitleTextColor(getColor(R.color.menuItems));
        setSupportActionBar(MyToolbar);

        currentForecastFragment.newInstance();
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CurrentForecastFragment(), "Daily");
        adapter.addFragment(new WeeklyForecastFragment(), "Weekly");
        adapter.addFragment(new GoogleMapsFragment(), "Google maps");
        adapter.addFragment(new FavouriteFragment(), "Favourites");
        viewPager.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menuOptions = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.action_share).getIcon().setTint(getColor(R.color.menuItems));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                //share weather forecast
                break;
            case R.id.action_settings:
                //bring up settings activity, eventually
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("CHRIS", "onConnectionFailed: connection failed");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void placeSelected(Place place) {
        for (Fragment f : getSupportFragmentManager().getFragments()){
            if (f instanceof PlaceSelected) {
                ((PlaceSelected) f).placeSelected(place);
            }
        }
    }
}
