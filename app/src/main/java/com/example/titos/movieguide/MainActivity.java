package com.example.titos.movieguide;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    static View FragDetail;
    static TextView vote;
    static TextView over;
    static TextView date;
    static ImageView poster;
    static TextView titel;
    static Context context;
    GridView gridView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getBaseContext();
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;


        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ||
                screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            FragDetail = findViewById(R.id.fragment);
            FragDetail.setVisibility(View.INVISIBLE);
            MovieFragment.large = true;
            titel = (TextView) findViewById(R.id.detailMovieName);

            poster = (ImageView)findViewById(R.id.detailMovieImage );

            date = (TextView)findViewById(R.id.detailMovieReleaseDate);

            over = (TextView)findViewById(R.id.detailMovieOverview);

            vote = (TextView)findViewById(R.id.detailMovieVote_average);
        }
        else {
            MovieFragment.large = false;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id== R.id.action_settings){
            startActivity(new Intent(this, SettingsActivity.class));

        }
        return true;
    }




    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public static void clickdItemGrid(String[] ty) {
        FragDetail.setVisibility(View.VISIBLE);
        final String[] data = ty;

        titel.setText(data[0]);
        Picasso.with(context)
                .load(data[1])
                .into(poster);
        date.setText(data[3]);
        over.setText(data[2]);
        vote.setText(data[4]);
    }
}

