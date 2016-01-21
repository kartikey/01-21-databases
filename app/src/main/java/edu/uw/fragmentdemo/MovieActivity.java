package edu.uw.fragmentdemo;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity implements MovieFragment.OnMovieSelectionListener {

    private static final String TAG = "MovieActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showSearchFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                showSearchFragment();
                return true;
            case R.id.action_favorites:
                showFavoritesFragment();
                return true;
            case R.id.action_test:
                runTest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new MovieFragment())
                .commit();
    }

    private void showFavoritesFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new FavoriteFragment())
                .addToBackStack(null)
                .commit();
    }

    //a method to test something!
    private void runTest() {
        Log.v(TAG, "Test button clicked!");

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Alert!")
//                .setMessage("Message for alert!!");
//
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                Log.v(TAG, "YAY!!!");
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();

        //new FavoriteFragment().newInstance().show();
        Context context = this;
        String text = "Hello World";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        MovieDatabase.testDatabase(this);

    }

    @Override
    public void onMovieSelected(Movie movie) {
        DetailFragment detail = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("title", movie.title);
        bundle.putInt("year", movie.year);
        bundle.putString("imdbId", movie.imdbId);
        bundle.putString("posterUrl", movie.posterUrl);

        detail.setArguments(bundle);

        //swap the fragments
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detail)
                .addToBackStack(null)
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //for non-support Activity
    //    public void onBackPressed() {
    //        if(getFragmentManager().getBackStackEntryCount() != 0) {
    //            getFragmentManager().popBackStack();
    //        } else {
    //            super.onBackPressed();
    //        }
    //        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    //    }
}
