package edu.uw.fragmentdemo;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uw.fragmentdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";

    public DetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        Bundle bundle = getArguments();

        if(bundle != null) {
            TextView titleView = (TextView) rootView.findViewById(R.id.txtMovieTitle);
            TextView imdbView = (TextView) rootView.findViewById(R.id.txtMovieIMDB);

            String title = bundle.getString("title");
            int year = bundle.getInt("year");
            String imdbId = bundle.getString("imdbId");
            String posterUrl = bundle.getString("posterUrl");
            Movie movie = new Movie(title, year, imdbId, posterUrl); //recreate movie

            titleView.setText(movie.toString());
            imdbView.setText("http://imdb.com/title/"+movie.imdbId);

            Button favButton = (Button)rootView.findViewById(R.id.btnFavorite);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "Favoriting...");

                }
            });


        }

        return rootView;
    }



}
