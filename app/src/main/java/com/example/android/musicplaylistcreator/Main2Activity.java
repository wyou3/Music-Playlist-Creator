package com.example.android.musicplaylistcreator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import static com.example.android.musicplaylistcreator.MainActivity.selectedSongs;


/*
 *
 *  This activity displays selected songs from MainActivity.
 *  Selected songs are display with their song covers.
 *
 *  Actions:
 *      Pressing song cover: Opens youtube video of select songs
 *      Long Pressing song cover: Opens menu w/ buttons
 *          1: Play video: same as pressing cover
 *          2: Song info: Opens browser to song wiki
 *          3: Artist info: Opens browser to artist wiki
 *
 */


public class Main2Activity extends AppCompatActivity {

    private GridView covers; // displays of all selected song covers in playlist
    private ArrayAdapter<Song> adapterSongs; // Adapter of selected songs
    private int focusElem; // element R.id of selected long press song cover


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        covers = (GridView) findViewById(R.id.coverGrid);
        covers.setAdapter(new ImageAdapter(this));
        covers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                String videoUrl = (selectedSongs.get(position)).videoUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(videoUrl));
                startActivity(i);
            }
        });

        this.registerForContextMenu(covers);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        focusElem = info.position;

        getMenuInflater().inflate(R.menu.context_menu, menu);

    }


    /**
     * Long press menu item actions
     *
     * Cases:
     *  playVideo:  redirects to youtube video of selected song
     *  songInfo:   redirects to song wiki
     *  artistInfo: redirects to artist wiki
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        Intent i;

        switch (selectedItem){
            case R.id.playVideo:
                String videoUrl = (selectedSongs.get(focusElem)).videoUrl();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(videoUrl));
                startActivity(i);
                break;
            case R.id.songInfo:
                String songUrl = (selectedSongs.get(focusElem)).songUrl();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(songUrl));
                startActivity(i);
                break;
            case R.id.artistInfo:
                String artistUrl = (selectedSongs.get(focusElem)).artistUrl();
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(artistUrl));
                startActivity(i);
                break;
        }

        return true;
    }



    // Adapter to display song covers in grid view
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        // references to our images
        private ArrayList<Integer> mThumbIds;

        public ImageAdapter(Context c) {
            mContext = c;

            mThumbIds = new ArrayList<>();
            for(int i=0; i<selectedSongs.size(); i++){
                mThumbIds.add( selectedSongs.get(i).imageId() );
            }
        }

        public int getCount() {
            return mThumbIds.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);

                DisplayMetrics displaymetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                int width = displaymetrics.widthPixels;

                imageView.setLayoutParams(new GridView.LayoutParams(width/2, width/2));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds.get(position));
            return imageView;
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main2 Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
