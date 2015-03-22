package com.kytelabs.bleduino;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kytelabs.bleduino.adapters.DrawerListAdapter;
import com.kytelabs.bleduino.fragments.ConnectionManagerFragment;
import com.kytelabs.bleduino.fragments.ModulesFragment;
import com.kytelabs.bleduino.fragments.SettingsFragment;
import com.kytelabs.bleduino.pojos.NavigationItem;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    //Member Variables
    //--------------------------------------------------------------------------------
    @InjectView(R.id.app_bar)
    Toolbar mToolbar;
    @InjectView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    @InjectView(R.id.listViewDrawer)
    ListView mListView;
    private NavigationItem[] mNavigationItems;

    //================================================================================
    // On Create
    //================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this); //neverforget.jpg

        // Toolbar and Drawer Setup
        //----------------------------------------------------------------------------
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerSetUp();

        // List View Setup
        //----------------------------------------------------------------------------

        populateNavigation();

        List<NavigationItem> navigationList = Arrays.asList(mNavigationItems);
        // Set adapter
        DrawerListAdapter adapter = new DrawerListAdapter(getApplicationContext(), navigationList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(this);

        setFragment(0, ModulesFragment.class);

        //mDrawerRecyclerView.getAdapter();

    }

    //================================================================================
    // Navigation
    //================================================================================

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (!mNavigationItems[position].isHeader()) {
            Class<? extends Fragment> fragmentClass = mNavigationItems[position].getFragmentClass();

            setFragment(position, fragmentClass);
        }
    }


    private void populateNavigation() {
        mNavigationItems = new NavigationItem[6];

        mNavigationItems[0] = new NavigationItem();
        mNavigationItems[0].setText("General");
        mNavigationItems[0].setHeader(true);

        mNavigationItems[1] = new NavigationItem();
        mNavigationItems[1].setFragmentClass(ModulesFragment.class);
        mNavigationItems[1].setText("Modules");
        mNavigationItems[1].setIconId(R.drawable.ic_directions_car_black_36dp);
        mNavigationItems[1].setSelected(true);

        mNavigationItems[2] = new NavigationItem();
        mNavigationItems[2].setFragmentClass(ConnectionManagerFragment.class);
        mNavigationItems[2].setText("Connection Manager");
        mNavigationItems[2].setIconId(R.drawable.ic_directions_subway_black_36dp);

        mNavigationItems[3] = new NavigationItem();
        mNavigationItems[3].setFragmentClass(SettingsFragment.class);
        mNavigationItems[3].setText("Settings");
        mNavigationItems[3].setIconId(R.drawable.ic_directions_bike_black_36dp);

        mNavigationItems[4] = new NavigationItem();
        mNavigationItems[4].setDivider(true);

        mNavigationItems[5] = new NavigationItem();
        mNavigationItems[5].setText("Other Stuff");
        mNavigationItems[5].setHeader(true);

    }

    public void setFragment(int position, Class<? extends Fragment> fragmentClass) {
        try {
            Fragment fragment = fragmentClass.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, fragment, fragmentClass.getSimpleName());
            fragmentTransaction.commit();

            //mListView.setItemChecked(position, true);
            mDrawerLayout.closeDrawers();
            mListView.invalidateViews();

        } catch (Exception ex) {
            Log.e("setFragment", ex.getMessage());
        }
    }


    //================================================================================
    // Drawer Set Up
    //================================================================================

    private void drawerSetUp() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened (Since I don't want anything
                // happening when drawer is open, I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }

        }; // Drawer Toggle Object Made
        mDrawerLayout.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
    }


    //================================================================================
    // Drawer Override Methods
    //================================================================================


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mListView)) {
            mDrawerLayout.closeDrawer(mListView);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    //================================================================================
    // Actionbar/Toolbar Methods
    //================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
