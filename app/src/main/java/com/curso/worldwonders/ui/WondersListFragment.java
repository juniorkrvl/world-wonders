package com.curso.worldwonders.ui;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.curso.worldwonders.R;
import com.curso.worldwonders.adapter.FeedCursorAdapter;
import com.curso.worldwonders.entity.Wonder;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.infrastructure.OperationListener;
import com.curso.worldwonders.manager.UserManager;
import com.curso.worldwonders.manager.WonderManager;
import com.curso.worldwonders.sync.SyncService;

import java.util.List;

/**
 * Created by Junior on 03/09/2015.
 */
public class WondersListFragment extends Fragment {

    // Container Activity must implement this interface
    public interface OnWonderSelectedListener {
        public void onWonderSelected(Wonder wonder);
    }

    private OnWonderSelectedListener mListener;
    private View fragmentView;
    private ListView listView;
    private FeedCursorAdapter feed;
    private Activity hostActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_wonders_list, container, false);
        return fragmentView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        hostActivity = activity;
        try {
            mListener = (OnWonderSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnWonderSelectedListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        loadWonders();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getAdapter().getItem(position);
                mListener.onWonderSelected(new Wonder(cursor));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_wonders_list_fragment,menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id ==  R.id.action_logout){
           return false;
        }
        else if(id == R.id.action_refresh){
            item.setActionView(R.layout.menu_loader);

            final Intent intent = new Intent(hostActivity, SyncService.class);
            Bundle bundleExtras = new Bundle();
            bundleExtras.putString("Command","Wonders");
            bundleExtras.putParcelable("Receiver", new ResultReceiver(new Handler()) {
                @Override
                protected void onReceiveResult(int resultCode, Bundle resultData) {
                    if (Constants.Service.SUCCESS == resultCode) {
                        item.setActionView(null);
                        Bundle b = resultData;
                        Toast.makeText(hostActivity, b.getString("Message"), Toast.LENGTH_SHORT).show();
                        hostActivity.stopService(intent);
                    } else if (Constants.Service.SUCCESS == resultCode) {
                        Bundle b = resultData;
                        Toast.makeText(hostActivity, b.getString("Message"), Toast.LENGTH_SHORT).show();
                        hostActivity.stopService(intent);
                    }
                }
            });
            intent.putExtras(bundleExtras);
            hostActivity.startService(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    private void loadWonders()
    {
        listView = (ListView) fragmentView.findViewById(R.id.main_list);

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

        feed = new FeedCursorAdapter(hostActivity, null);
        listView.setAdapter(feed);

        final WonderManager manager = new WonderManager(hostActivity);

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

}


