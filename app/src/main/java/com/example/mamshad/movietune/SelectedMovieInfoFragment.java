package com.example.mamshad.movietune;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
import java.util.List;

import models.GlobalData;
import models.JsonFieldsMovieList;
import models.MovieList;
import models.SelectedMovie;

public class SelectedMovieInfoFragment extends Fragment {

    private RecyclerView horizontal_recycler_view;
    private ArrayList<HorizontalItem> horizontalList;
    private HorizontalAdapter horizontalAdapter;

    private RecyclerView mRecyclerView;
    private HorizontalViewAdapter mHorizontalAdapter;

    private String FEED_URL = "http://stacktips.com/?json=get_recent_posts&count=45";

    private static String jsonUrl = "https://api.themoviedb.org/3/movie/259316/similar?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private static String jsonPartialUrl = "https://api.themoviedb.org/3/movie/";
    private static String jsonUrlApiKey = "/similar?api_key=c37d3b40004717511adb2c1fbb15eda4&page=1";
    private static String jsonFullUrl = "";

    private static String imagePartialUrl = "http://image.tmdb.org/t/p/w500";
    private static String urlApiKey = "?api_key=c37d3b40004717511adb2c1fbb15eda4";
    private static String imageFullUrl = "";

    private ProgressBar mProgressBar;
    private ProgressDialog pDialog;

    TextView productionCompany;
    TextView productionCountry;
    TextView budget;
    TextView language;

    TextView movieName;
    TextView genreOne;
    TextView genreTwo;
    TextView genreThree;
    TextView voteAverage;
    TextView popularity;

    public SelectedMovieInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected_movie_info, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.progressBar);

        movieName = (TextView) getView().findViewById(R.id.movie_name);
        genreOne = (TextView) getView().findViewById(R.id.genre_one);
        genreTwo = (TextView) getView().findViewById(R.id.genre_two);
        genreThree = (TextView) getView().findViewById(R.id.genre_three);
        voteAverage = (TextView) getView().findViewById(R.id.vote_average);
        popularity = (TextView) getView().findViewById(R.id.popularity);
        productionCompany = (TextView) getView().findViewById(R.id.production_company);
        productionCountry = (TextView) getView().findViewById(R.id.production_country);
        budget = (TextView) getView().findViewById(R.id.budget);
        language = (TextView) getView().findViewById(R.id.language);

        SelectedMovie selectedMovie = GlobalData.selectedMovieInfo;

        movieName.setText(GlobalData.selectedMovieInfo.getTitle());
        voteAverage.setText(String.valueOf(GlobalData.selectedMovieInfo.getVoteAverage()));
        popularity.setText(String.valueOf(GlobalData.selectedMovieInfo.getPopularity()));

        if(GlobalData.selectedMovieInfo.getGenres().size() > 0){
            if(GlobalData.selectedMovieInfo.getGenres().size() == 1 ){
                genreOne.setText(GlobalData.selectedMovieInfo.getGenres().get(0).getGenresName());
            }else if(GlobalData.selectedMovieInfo.getGenres().size() == 2 ){
                genreOne.setText(GlobalData.selectedMovieInfo.getGenres().get(0).getGenresName());
                genreTwo.setText(GlobalData.selectedMovieInfo.getGenres().get(1).getGenresName());
            }else if(GlobalData.selectedMovieInfo.getGenres().size() == 3 ){
                genreOne.setText(GlobalData.selectedMovieInfo.getGenres().get(0).getGenresName());
                genreTwo.setText(GlobalData.selectedMovieInfo.getGenres().get(1).getGenresName());
                genreThree.setText(GlobalData.selectedMovieInfo.getGenres().get(2).getGenresName());
            }
        }

        productionCompany.setText(selectedMovie.getProductionCompanyList().get(0).getName());
        productionCountry.setText(selectedMovie.getProductionCountriesList().get(0).getName());
        budget.setText(selectedMovie.getBudget());
        language.setText(selectedMovie.getOriginalLanguage());

        horizontal_recycler_view = (RecyclerView) getView().findViewById(R.id.horizontal_recycler_view);
        horizontalList = new ArrayList<>();
        horizontalAdapter = new HorizontalAdapter(horizontalList);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        horizontal_recycler_view.setAdapter(horizontalAdapter);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        jsonFullUrl = jsonPartialUrl + selectedMovie.getId() + jsonUrlApiKey;

        new AsyncHttpTask().execute(jsonFullUrl);
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

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

            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            horizontalAdapter = new HorizontalAdapter(horizontalList);
            horizontal_recycler_view.setAdapter(horizontalAdapter);
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
            HorizontalItem item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);

                MovieList movieList = new MovieList();
                movieList.setPosterPath(post.getString(JsonFieldsMovieList.POSTER_PATH));
                movieList.setId(post.getInt(JsonFieldsMovieList.ID));

//                movieLists.add(movieList);

                imageFullUrl = imagePartialUrl + movieList.getPosterPath() + urlApiKey;
                item = new HorizontalItem();
                item.setImage(imageFullUrl);
                horizontalList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<HorizontalItem> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;

            public MyViewHolder(View view) {
                super(view);
                imageView = (ImageView) view.findViewById(R.id.image_view);
            }
        }

        public HorizontalAdapter(List<HorizontalItem> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.horizontal_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
//            holder.imageView.set(horizontalList.get(position));

//            holder.txtView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(),holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();
//                }
//            });
            HorizontalItem item = horizontalList.get(position);
            Picasso.with(getActivity()).load(item.getImage()).into(holder.imageView);
//            Picasso.with(getActivity()).load(item.getImage()).resize(120, 60).into(viewHolder.img_android);
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }
}
