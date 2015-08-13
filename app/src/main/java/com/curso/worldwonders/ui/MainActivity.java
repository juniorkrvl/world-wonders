package com.curso.worldwonders.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import com.curso.worldwonders.R;
import com.curso.worldwonders.adapter.FeedCursorAdapter;
import com.curso.worldwonders.infrastructure.ProviderTest;
import com.curso.worldwonders.manager.UserManager;
import com.curso.worldwonders.manager.WonderManager;

public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private FeedCursorAdapter feed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        listView = (ListView) this.findViewById(R.id.main_list);

        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(500);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(500);
        set.addAnimation(animation);

        set.setInterpolator(new OvershootInterpolator(0.5f));

        LayoutAnimationController controller = new LayoutAnimationController(set, 0.3f);
        listView.setLayoutAnimation(controller);

        feed = new FeedCursorAdapter(this, null);
        listView.setAdapter(feed);

        final WonderManager manager = new WonderManager(this);

        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                return manager.getWondersLoader();
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
// Swap the new cursor in. (The framework will take care of closing the
// old cursor once we return.)
                feed.changeCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                feed.changeCursor(null);
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id ==  R.id.action_logout){

//            ProviderTest pt = new ProviderTest(this);
//            pt.deleteTestWonder(4);
//            pt.printWonderList();

            UserManager userManager = new UserManager(this);
            userManager.logout();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.action_rate){
            ProviderTest test = new ProviderTest(this);
            test.insertTestWonder("Pyramid", "Egypt", "Egypt", "http://www.kingtutone.com/pyramids/information/khafrepyramid.jpg");
        }

        return super.onOptionsItemSelected(item);
    }
}
