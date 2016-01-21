package edu.uw.fragmentdemo;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import edu.uw.fragmentdemo.R;

/**
 * Shows the list of favorite movies.
 */
public class FavoriteFragment extends Fragment {

    private static final String TAG = "FavoriteFragment";

    private SimpleCursorAdapter adapter; //adapter for list view

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            MovieFragment.OnMovieSelectionListener callback = (MovieFragment.OnMovieSelectionListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnMovieSelectionListener");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        /** List View - code from MovieFragment **/
        //model (starts out empty)
        //ArrayList<Movie> list = new ArrayList<Movie>();

        //controller
//        adapter = new ArrayAdapter<Movie>(
//                getActivity(), R.layout.list_item, R.id.txtItem, list);

        String[] cols = new String[] {MovieDatabase.FavoriteEntry.COL_TITLE};
        int[] ids = new int[] {R.id.txtItem};

        adapter = new SimpleCursorAdapter(
                getActivity(), R.layout.fragment_favorite, MovieDatabase.queryDatabase(getActivity()),cols,ids, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        //support ListView or GridView
        AdapterView listView = (AdapterView)rootView.findViewById(R.id.favoriteList);
        listView.setAdapter(adapter);

        //respond to item clicking
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Log.i(TAG, "selected: " + movie.toString());

                //swap the fragments to show the detail
                ((MovieFragment.OnMovieSelectionListener)getActivity()).onMovieSelected(movie);
            }
        });


        return rootView;
    }

}
