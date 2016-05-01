package com.example.titos.movieguide;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragmentFragment extends Fragment {

    public DetailFragmentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail_fragment, container, false);
//        Intent intent = getActivity().getIntent();
//        final String[] data = intent .getStringArrayExtra("data");
//        TextView titel = (TextView) view.findViewById(R.id.detailMovieName);
//        titel.setText(data[0]);
//        ImageView poster = (ImageView) view.findViewById(R.id.detailMovieImage);
//        Picasso.with(getContext())
//                .load(data[1])
//                .into(poster);
//        TextView date = (TextView) view.findViewById(R.id.detailMovieReleaseDate);
//        date.setText(data[3]);
//        TextView over = (TextView) view.findViewById(R.id.detailMovieOverview);
//        over.setText(data[2]);
//        TextView vote = (TextView) view.findViewById(R.id.detailMovieVote_average);
//        vote.setText(data[4]);
        return view;


    }
}
