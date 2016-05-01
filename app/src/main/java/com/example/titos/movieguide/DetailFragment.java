package com.example.titos.movieguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class DetailFragment extends AppCompatActivity {

    TextView over;
    ListView tlist;
    String []data;
    ArrayList<Mytreiler> treailerkey;
    Button buttonAddFavorite;
    private MovieOperations movieDBOperation;
    Movie_Item movieItem ;
    int isFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_fragment);
        movieDBOperation = new MovieOperations(getApplicationContext());
        try {
            movieDBOperation.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        buttonAddFavorite = (Button)findViewById(R.id.detailMovieFavorite);
        buttonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavorite();
            }
        });
        tlist  = (ListView)findViewById(R.id.Trialerlist);
        Intent intent = getIntent();
         data = intent .getStringArrayExtra("data");
        TextView titel = (TextView) findViewById(R.id.detailMovieName);
        titel.setText(data[0]);
        ImageView poster = (ImageView)findViewById(R.id.detailMovieImage );
        Picasso.with(getBaseContext())
                .load(data[1])
                .into(poster);
        TextView date = (TextView)findViewById(R.id.detailMovieReleaseDate);
        date.setText(data[3]);
        over = (TextView)findViewById(R.id.detailMovieOverview);
        over.setText(data[2]);
        TextView vote = (TextView)findViewById(R.id.detailMovieVote_average);
        vote.setText(data[4]);


        getPoster task = new getPoster();
        task.execute(data[5]);
        TrailerTask tssk = new TrailerTask();
        tssk.execute(data[5]);

        isFavorite = movieDBOperation.checkIsFavorite(data[5]);

        tlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String shar = "http://www.youtube.com/watch?v=" + treailerkey.get(position).getKey();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(shar)));


              //  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(treailerkey.get(position).getKey())));
            }
        });

    }


    void addFavorite() {
        if ( isFavorite == -1 ) {

            movieDBOperation.addMovie(data[5],data[0], data[1],
                    data[2], data[4], data[3]);
            isFavorite = 0 ;
        }
        if ( isFavorite == 1 ) {
            movieDBOperation.updateFavoritesList(data[5], 0);
            buttonAddFavorite.setText("Add to favorite");
            isFavorite = 0 ;
        }
        else if ( isFavorite == 0 ) {
            movieDBOperation.updateFavoritesList(data[5] , 1);
            buttonAddFavorite.setText("remove from favorite");
            isFavorite = 1 ;
        }

    }

    class getPoster extends AsyncTask<String, Void, String> {
        Context con1;
        GridView secondgird;




        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String base_string;

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String choice = sharedPreferences.getString("listpref", "");


            base_string = "http://api.themoviedb.org/3/movie/" + params[0] + "/reviews?";



            BufferedReader reader = null;
            String posterJson = "";
            URL url = null;

            String movieJsonStr = null;

            Uri uri = Uri.parse(base_string).buildUpon()
                    .appendQueryParameter("api_key", "c9bb36f94aaa138a81f9546d2e558da8").build();

            try {
                url = new URL(uri.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            try {
//               url= new URL("https://");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

            } catch (IOException e) {
                Log.i("error", e.getMessage());
                //  Toast.makeText(c1,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            try {
                InputStream streamReader = urlConnection.getInputStream();
                if (streamReader == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(streamReader));
                String line;
                while ((line = reader.readLine()) != null) {
                    posterJson += line;
                    Log.v("hi", line);
                }
                 String item = "";

                //Toast.makeText(c1,"size "+String.valueOf(posterJson.length()),Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(posterJson);
                JSONArray arrayJ = jsonObject.getJSONArray("results");
                Log.i("url", uri.toString());
                for (int i = 0; i < arrayJ.length(); i++) {
                    JSONObject mymovie = arrayJ.getJSONObject(i);
                    String auth = mymovie.getString("author");
                    String content = mymovie.getString("content");

                    item =item +"\n" + auth +"\n" + content;
                }
                return item;
            } catch (Exception e) {

                Log.i("error", e.toString());
                return null;
                // Toast.makeText(c1,e.getMessage(),Toast.LENGTH_SHORT).show();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("ahmed", "Error closing stream", e);
                    }

            }

        }

        @Override
        protected void onPostExecute(String strings) {

            over.setText(strings);
        }
    }

    class TrailerTask extends AsyncTask<String, Void, ArrayList<Mytreiler> > {
        Context con1;
        GridView secondgird;




        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected ArrayList<Mytreiler> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            String base_string;

//            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String choice = sharedPreferences.getString("listpref", "");


            base_string = "http://api.themoviedb.org/3/movie/" +params[0]+ "/videos?";



            BufferedReader reader = null;
            String posterJson = "";
            URL url = null;

            String movieJsonStr = null;

            Uri uri = Uri.parse(base_string).buildUpon()
                    .appendQueryParameter("api_key", "c9bb36f94aaa138a81f9546d2e558da8").build();

            try {
                url = new URL(uri.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            try {
//               url= new URL("https://");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

            } catch (IOException e) {
                Log.i("error", e.getMessage());
                //  Toast.makeText(c1,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            try {
                InputStream streamReader = urlConnection.getInputStream();
                if (streamReader == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(streamReader));
                String line;
                while ((line = reader.readLine()) != null) {
                    posterJson += line;
                    Log.v("hi", line);
                }
                String item = "";

                //Toast.makeText(c1,"size "+String.valueOf(posterJson.length()),Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(posterJson);
                JSONArray arrayJ = jsonObject.getJSONArray("results");
                treailerkey=  new ArrayList<Mytreiler>();
                for (int i = 0; i < arrayJ.length(); i++) {
                    JSONObject mymovie = arrayJ.getJSONObject(i);
                    Mytreiler Mytreiler = new Mytreiler();
                    Mytreiler.setName(mymovie.getString("name"));
                    Mytreiler.setKey(mymovie.getString("key"));


                    treailerkey.add(Mytreiler);
                }
                return treailerkey;
            } catch (Exception e) {

                Log.i("error", e.toString());
                return null;
                // Toast.makeText(c1,e.getMessage(),Toast.LENGTH_SHORT).show();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("ahmed", "Error closing stream", e);
                    }

            }

        }

        @Override
        protected void onPostExecute(ArrayList<Mytreiler> strings) {
           tlist.setAdapter(new Trialer_Adpter(getApplicationContext(),strings));


        }
    }
}

