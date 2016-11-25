package com.example.mamshad.movietune;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import models.Genre;
import models.GlobalData;
import models.JsonFieldsMovieList;
import models.JsonFieldsSelectedMovie;
import models.MovieList;
import models.ProductionCompany;
import models.ProductionCountries;
import models.SelectedMovie;
import models.SpokenLanguage;


public class UpcomingFragment extends Fragment {

    HomeActivity activity;

    private GridView mGridView;
    private ProgressBar mProgressBar;
    private ProgressDialog pDialog;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;

    //    String url = "http://image.tmdb.org/t/p/w500/9HE9xiNMEFJnCzndlkWD7oPfAOx.jpg?api_key=c37d3b40004717511adb2c1fbb15eda4";
    private String FEED_URL = "http://stacktips.com/?json=get_recent_posts&count=45";

    private static String urlApiKey = "?api_key=c37d3b40004717511adb2c1fbb15eda4";
    private static String movieDetailPartialUrl = "https://api.themoviedb.org/3/movie/";
    private static String movieFullUrl = "";

    private static String jsonUrl = "https://api.themoviedb.org/3/movie/upcoming?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private static String imagePartialUrl = "http://image.tmdb.org/t/p/w500";
    private static String imageUrlApiKey = "?api_key=c37d3b40004717511adb2c1fbb15eda4";
    private static String imageFullUrl = "";




    private ArrayList<MovieList> movieLists = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public UpcomingFragment(HomeActivity activity) {
        // Required empty public constructor
        this.activity = activity;
    }

    public UpcomingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_grid_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mGridView = (GridView) getView().findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.progressBar);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                movieFullUrl = movieDetailPartialUrl + movieLists.get(position).getId() + urlApiKey;

                new UpcomingFragment.SeletedMovieDetails().execute();
            }
        });

        //Start download
        new UpcomingFragment.AsyncHttpTask().execute(jsonUrl);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private class SeletedMovieDetails extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(movieFullUrl);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    SelectedMovie selectedMovie = new SelectedMovie();
                    selectedMovie.setAdult(jsonObj.getBoolean(JsonFieldsSelectedMovie.ADULT));
                    selectedMovie.setId(jsonObj.getInt(JsonFieldsSelectedMovie.ID));
                    selectedMovie.setBackdropPath(jsonObj.getString(JsonFieldsSelectedMovie.BACKDROP_PATH));
                    selectedMovie.setBudget(jsonObj.getString(JsonFieldsSelectedMovie.BUDGET));
                    selectedMovie.setHomepage(jsonObj.getString(JsonFieldsSelectedMovie.HOMEPAGE));
                    selectedMovie.setImdbId(jsonObj.getString(JsonFieldsSelectedMovie.IMDB_ID));
                    selectedMovie.setOriginalLanguage(jsonObj.getString(JsonFieldsSelectedMovie.ORIGINAL_LANGUAGE));
                    selectedMovie.setOriginalTitle(jsonObj.getString(JsonFieldsSelectedMovie.ORIGINAL_TITLE));
                    selectedMovie.setOverview(jsonObj.getString(JsonFieldsSelectedMovie.OVERVIEW));
                    selectedMovie.setPosterPath(jsonObj.getString(JsonFieldsSelectedMovie.POSTER_PATH));
                    selectedMovie.setPopularity(jsonObj.getDouble(JsonFieldsSelectedMovie.POPULARITY));
                    selectedMovie.setReleaseDate(jsonObj.getString(JsonFieldsSelectedMovie.RELEASE_DATE));
                    selectedMovie.setRevenue(jsonObj.getInt(JsonFieldsSelectedMovie.REVENUE));
                    selectedMovie.setTitle(jsonObj.getString(JsonFieldsSelectedMovie.TITLE));
                    selectedMovie.setTagline(jsonObj.getString(JsonFieldsSelectedMovie.TAGLINE));
                    selectedMovie.setStatus(jsonObj.getString(JsonFieldsSelectedMovie.STATUS));
                    selectedMovie.setVoteAverage(jsonObj.getDouble(JsonFieldsSelectedMovie.VOTE_AVERAGE));
                    selectedMovie.setVoteCount(jsonObj.getInt(JsonFieldsSelectedMovie.VOTE_COUNT));
                    selectedMovie.setVideo(jsonObj.getBoolean(JsonFieldsSelectedMovie.VIDEO));
                    selectedMovie.setVoteCount(jsonObj.getInt(JsonFieldsSelectedMovie.VOTE_COUNT));


                    // genre
                    JSONArray genres = jsonObj.getJSONArray("genres");

                    for (int i = 0; i < genres.length(); i++) {
                        JSONObject genreObj = genres.optJSONObject(i);
                        ArrayList<Genre> genreArrayList = new ArrayList<>();
                        Genre genre = new Genre();
                        genre.setGenresId(genreObj.getInt(JsonFieldsSelectedMovie.GENRES_ID));
                        genre.setGenresName(genreObj.getString(JsonFieldsSelectedMovie.GENRES_NAME));
                        genreArrayList.add(genre);
                        selectedMovie.setGenres(genreArrayList);
                    }

                    // spoken language
                    JSONArray spokenLanguages = jsonObj.getJSONArray("spoken_languages");

                    for (int i = 0; i < spokenLanguages.length(); i++) {
                        JSONObject spokenLangobj = spokenLanguages.optJSONObject(i);
                        ArrayList<SpokenLanguage> spokenLangArrayList = new ArrayList<>();
                        SpokenLanguage spokenLanguage = new SpokenLanguage();
                        spokenLanguage.setIso(spokenLangobj.getString(JsonFieldsSelectedMovie.SPOKEN_LANGUAGES_ISO_639_1));
                        spokenLanguage.setName(spokenLangobj.getString(JsonFieldsSelectedMovie.SPOKEN_LANGUAGES_NAME));
                        spokenLangArrayList.add(spokenLanguage);
                        selectedMovie.setSpokenLanguageList(spokenLangArrayList);
                    }

                    // production company
                    JSONArray productionCompanies = jsonObj.getJSONArray("production_companies");

                    for (int i = 0; i < productionCompanies.length(); i++) {
                        JSONObject prdCompObj = productionCompanies.optJSONObject(i);
                        ArrayList<ProductionCompany> productionCompanyArrayList = new ArrayList<>();
                        ProductionCompany productionCompany = new ProductionCompany();
                        productionCompany.setId(prdCompObj.getInt(JsonFieldsSelectedMovie.PRODUCTION_COMPANIES_ID));
                        productionCompany.setName(prdCompObj.getString(JsonFieldsSelectedMovie.PRODUCTION_COMPANIES_NAME));
                        productionCompanyArrayList.add(productionCompany);
                        selectedMovie.setProductionCompanyList(productionCompanyArrayList);
                    }

                    // production countries
                    JSONArray productionCountriesArray = jsonObj.getJSONArray("production_countries");

                    for (int i = 0; i < productionCountriesArray.length(); i++) {
                        JSONObject prdcountriesobj = productionCountriesArray.optJSONObject(i);
                        ArrayList<ProductionCountries> productionCountryArrayList = new ArrayList<>();
                        ProductionCountries productionCountry = new ProductionCountries();
                        productionCountry.setIso(prdcountriesobj.getString(JsonFieldsSelectedMovie.PRODUCTION_COUNTRIES_ISO_3166_1));
                        productionCountry.setName(prdcountriesobj.getString(JsonFieldsSelectedMovie.PRODUCTION_COUNTRIES_NAME));
                        productionCountryArrayList.add(productionCountry);
                        selectedMovie.setProductionCountriesList(productionCountryArrayList);
                    }
                    GlobalData.selectedMovieInfo = selectedMovie;

                } catch (final JSONException e) {
                    Log.e("", "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.e("", "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            Intent intent = new Intent(getActivity(), SelectedMovieActivity.class);
            startActivity(intent);
        }
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            //this
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed
                }
            } catch (Exception e) {
//                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
            mProgressBar.setVisibility(View.GONE);


        }
    }

    String streamToString(InputStream stream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        // Close stream
        if (null != stream) {
            stream.close();
        }
        return result;
    }

    /**
     * Parsing the feed results and get the list
     *
     * @param result
     */
    private void parseResult(String result) {
        try {

            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("results");
            GridItem item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);

                MovieList movieList = new MovieList();
                movieList.setPosterPath(post.getString(JsonFieldsMovieList.POSTER_PATH));
                movieList.setId(post.getInt(JsonFieldsMovieList.ID));

                movieLists.add(movieList);

                imageFullUrl = imagePartialUrl + movieList.getPosterPath() + urlApiKey;
                item = new GridItem();
                item.setImage(imageFullUrl);
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Stuff to do, dependent on requestCode and resultCode
        if (requestCode == Activity.RESULT_OK) {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return false;
    }
}
