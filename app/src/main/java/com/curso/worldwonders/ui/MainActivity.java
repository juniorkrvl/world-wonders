package com.curso.worldwonders.ui;
import android.app.FragmentTransaction;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.curso.worldwonders.R;
import com.curso.worldwonders.adapter.FeedCursorAdapter;
import com.curso.worldwonders.entity.Wonder;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.infrastructure.ProviderTest;
import com.curso.worldwonders.manager.UserManager;
import com.curso.worldwonders.manager.WonderManager;

public class MainActivity extends ActionBarActivity implements WondersListFragment.OnWonderSelectedListener {

    private ListView listView;
    private FeedCursorAdapter feed;
    private boolean isTablet;
    private View detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isTablet = getResources().getBoolean(R.bool.is_tablet);
        loadUi();
    }

    private void loadUi(){
        //detailFragment = this.findViewById(R.id.wonder_details_container);

        if (isTablet) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            WonderDetailsFragment details = new WonderDetailsFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.add(R.id.wonder_details_container, details);
            ft.commit();
        }

    }


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
         if(id ==  R.id.action_logout){
            //ProviderTest pt = new ProviderTest(this);
            //pt.deleteTestWonder(4);
            //pt.printWonderList();
            UserManager userManager = new UserManager(this);
            userManager.logout();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.action_refresh){
             return false;
         }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWonderSelected(Wonder wonder) {
        if (isTablet) {
            WonderDetailsFragment details = WonderDetailsFragment.newInstance(wonder);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.wonder_details_container, details);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else{
            Intent intent = new Intent(this, WonderDetailsActivity.class);
            intent.putExtra(Constants.IntentConsts.EXTRA_WONDER, wonder);
            startActivity(intent);
        }
    }
}
