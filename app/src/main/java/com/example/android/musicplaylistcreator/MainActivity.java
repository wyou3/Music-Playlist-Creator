package com.example.android.musicplaylistcreator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Song> songs = new ArrayList<>(); // All songs listed in listView
    private ArrayAdapter<Song> adapterSongs;           // Check box views with song names and artists

    // static songs that are checked off when user creates playlist.
    // -used and displayed in main2Activity
    public static ArrayList<Song> selectedSongs;
    public ListView listView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    // populates arrayList songs with default songs
    private void populateSongs() {
        songs.add(new Song("Shape Of You", "Ed Sheeran",
                "https://www.youtube.com/watch?v=_dK2tDK9grQ",
                "https://en.wikipedia.org/wiki/Shape_of_You",
                "https://en.wikipedia.org/wiki/Ed_Sheeran",
                R.drawable.p_0));
        songs.add(new Song("Bad and Boujee", "Migos Featuring Lil Uzi Vert",
                "https://www.youtube.com/watch?v=S-sJp1FfG7Q",
                "https://en.wikipedia.org/wiki/Bad_and_Boujee",
                "https://en.wikipedia.org/wiki/Migos",
                R.drawable.p_1));
        songs.add(new Song("Castle On The Hill", "Ed Sheeran",
                "https://www.youtube.com/watch?v=7Qp5vcuMIlk",
                "https://en.wikipedia.org/wiki/Castle_on_the_Hill_(song)",
                "https://en.wikipedia.org/wiki/Ed_Sheeran",
                R.drawable.p_2));
        songs.add(new Song("Paris", "The Chainsmokers",
                "https://www.youtube.com/watch?v=RhU9MZ98jxo",
                "https://en.wikipedia.org/wiki/Paris_(The_Chainsmokers_song)",
                "https://en.wikipedia.org/wiki/The_Chainsmokers",
                R.drawable.p_3));
        songs.add(new Song("Million Reasons", "Lady Gaga",
                "https://www.youtube.com/watch?v=WYRJ-ryPEu0",
                "https://en.wikipedia.org/wiki/Lady_Gaga",
                "https://en.wikipedia.org/wiki/Million_Reasons",
                R.drawable.p_4));
        songs.add(new Song("Bad Things", "Machine Gun Kelly x Camila Cabello",
                "https://www.youtube.com/watch?v=QpbQ4I3Eidg",
                "https://en.wikipedia.org/wiki/Bad_Things_(Machine_Gun_Kelly_and_Camila_Cabello_song)",
                "https://en.wikipedia.org/wiki/Machine_Gun_Kelly_(rapper)",
                R.drawable.p_5));
    }

    // creates arrayAdapter of all songs
    private void displaySongs() {
        adapterSongs = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, songs);

        listView = (ListView) findViewById(R.id.song_list);
        listView.setAdapter(adapterSongs);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // change the checkbox state

                CheckedTextView checkedTextView = ((CheckedTextView) view);
                checkedTextView.setChecked(!checkedTextView.isChecked());
                songs.get(position).setChecked(checkedTextView.isChecked());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateSongs();
        displaySongs();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    // setting of menu items on click methods
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.createPlaylist:
                // creates playlist from selected songs
                // then activats Main2Activity to display songs
                startNewActivity();
                break;
            case R.id.clearAllSelected:
                // clears all checked off songs
                clearAllSelected();
                break;
            case R.id.invertSelected:
                // inverts checkmarks
                invertSelected();
                break;
            case R.id.checkAll:
                // clears all check marks
                checkAll();
                break;
        }
        if (id == R.id.createPlaylist) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // activates Main2Activity if at least one song
    // has been selected.
    private void startNewActivity() {
        selectedSongs = new ArrayList<>();
        for (int i = 0; i < adapterSongs.getCount(); i++) {
            Song s = songs.get(i);
            if (s.checked()) {
                selectedSongs.add(s);
            }
        }
        if (!selectedSongs.isEmpty()) {
            Intent playlist = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(playlist);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Select at least 1 song.";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    // clears all checked off songs
    private void clearAllSelected() {
        for (int i = 0; i < adapterSongs.getCount(); i++) {
            Song s = songs.get(i);
            if (s.checked()) {
                s.setChecked(Boolean.FALSE);
                CheckedTextView checkedTextView = ((CheckedTextView) listView.getChildAt(i));
                checkedTextView.setChecked(Boolean.FALSE);
            }
        }
    }


    // flips all checkmarks
    private void invertSelected() {
        for (int i = 0; i < adapterSongs.getCount(); i++) {
            Song s = songs.get(i);
            CheckedTextView checkedTextView = ((CheckedTextView) listView.getChildAt(i));
            checkedTextView.setChecked(!checkedTextView.isChecked());
            s.setChecked(checkedTextView.isChecked());
        }
    }

    // checks off all songs
    private void checkAll() {
        for (int i = 0; i < adapterSongs.getCount(); i++) {
            Song s = songs.get(i);
            if (!s.checked()) {
                s.setChecked(Boolean.TRUE);
                CheckedTextView checkedTextView = ((CheckedTextView) listView.getChildAt(i));
                checkedTextView.setChecked(Boolean.TRUE);
            }
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
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
