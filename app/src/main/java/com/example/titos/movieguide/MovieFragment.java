package com.example.titos.movieguide;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.util.LinkedList;

/**
 * Created by TITOS on 4/20/2016.
 */
public class MovieFragment extends Fragment {
    GridView gridView;
    LinkedList<Movie_Item> itemsnew;
    ArrayList<String> mPoster;
    static boolean large = false;

    private MovieOperations movieDBOperation;
    SharedPreferences sharedPreferences ;
    String choice ;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        movieDBOperation = new MovieOperations(getActivity());
        try {
            movieDBOperation.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gird_View);
        getMovies();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        getMovies();
    }

    void getMovies(){

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        choice = sharedPreferences.getString("listpref", "");
        Log.d("I'mTry",choice);

        if (choice.equals("Favourite")) {

            ArrayList<Movie_Item> movieItemArray = movieDBOperation.getFavoritesMovies();
            gridView.setAdapter(new Gird_view_AdpterFragment(getActivity(),R.layout.image_movie_view, movieItemArray));
        }
        else {
            getPoster myp = new getPoster(getActivity(), gridView, null);
            myp.execute();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {

            startActivity(new Intent(getActivity(), SettingsActivity.class));


        }
        return true;
    }


    class getPoster extends AsyncTask<Void, Void, ArrayList<Movie_Item>> {
        Context con1;
        GridView secondgird;


        public getPoster(Context con, GridView Firistgrid, ArrayList<Movie_Item> it) {
            con1 = con;
            secondgird = Firistgrid;
            //our_movie_item = it;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie_Item> doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            String base_string;

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

            base_string = "https://api.themoviedb.org/3/movie/popular?";

            if (choice.equals("TopRated")) {
                base_string = "https://api.themoviedb.org/3/movie/top_rated?";
            }


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
                ArrayList<Movie_Item> our_movie_item = new ArrayList<Movie_Item>();

                //Toast.makeText(c1,"size "+String.valueOf(posterJson.length()),Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(posterJson);
                JSONArray arrayJ = jsonObject.getJSONArray("results");
                mPoster = new ArrayList<String>();
                Log.i("url", uri.toString());
                for (int i = 0; i < arrayJ.length(); i++) {
                    JSONObject mymovie = arrayJ.getJSONObject(i);
                    String PP = new String("http://image.tmdb.org/t/p/w185");
                    PP = PP + mymovie.getString("poster_path");
                    String OR = mymovie.getString("original_title");
                    String ov = mymovie.getString("overview");
                    String RD = mymovie.getString("release_date");
                    String VA = mymovie.getString("vote_average");
                    String ID = mymovie.getString("id");

                    Log.v("hello", PP);

                    Movie_Item item = new Movie_Item(RD, ov, PP, OR,VA, ID);
                   our_movie_item.add(item);
                    Log.i("hehoo", ID);

                }
                return our_movie_item;
            } catch (Exception e) {

                Log.i("error", e.toString());
                return null ;
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
        protected void onPostExecute(ArrayList<Movie_Item> strings) {
            final Gird_view_AdpterFragment myg1 = new Gird_view_AdpterFragment(con1, R.layout.image_movie_view, strings);
            secondgird.setAdapter(myg1);
            secondgird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Movie_Item x = myg1.getItem(position);
                    String[] ty = new String[6];
                    String title = x.getOriginal_title();
                    String poster = x.getPoster_path();
                    String date = x.getRelease_data();
                    String vote = x.getVote_average();
                    String over = x.getOverview();
                    String Id = x.getId();

                    ty[0] = title;
                    ty[1] = poster;
                    ty[2] = over;
                    ty[3] = date;
                    ty[4] = Id;
                    ty[5] = vote;
                    if (!large) {
                        Intent intent = new Intent(getActivity(), DetailFragment.class);
                        intent.putExtra("data", ty);
//                    intent.putExtra("poster_url",poster);
                        startActivity(intent);
                    }
                    else {
                        MainActivity.clickdItemGrid(ty);
                    }
                }
            });
        }
    }
}
