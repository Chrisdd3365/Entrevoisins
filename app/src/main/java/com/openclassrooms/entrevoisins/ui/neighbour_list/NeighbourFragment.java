package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity.LAUNCH_SECOND_ACTIVITY;
import static com.openclassrooms.entrevoisins.ui.neighbour_list.NeighbourDetailsActivity.BUNDLE_EXTRA_NEIGHBOUR;


public class NeighbourFragment extends Fragment implements ListNeighboursInterface {
    //-- PROPERTIES
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;
    private MyNeighbourRecyclerViewAdapter myNeighbourRecyclerViewAdapter;
    private Context context;

    /**
     * Create and return a new instance
     * @return @{@link NeighbourFragment}
     */
    public static NeighbourFragment newInstance(List<Neighbour> neighbours) {
        NeighbourFragment fragment = new NeighbourFragment();

        Bundle args = new Bundle();
        args.putSerializable("neighbours", (Serializable) neighbours);
        fragment.setArguments(args);

        return fragment;
    }

    //-- VIEW LIFE CYCLE
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);

        context = view.getContext();

        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));

        initList();

        assert getArguments() != null;
        mNeighbours = (List<Neighbour>) getArguments().getSerializable("neighbours");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {
        myNeighbourRecyclerViewAdapter = new MyNeighbourRecyclerViewAdapter(mNeighbours, this);
        mRecyclerView.setAdapter(myNeighbourRecyclerViewAdapter);
    }

    //-- METHODS
    public void updateList(List<Neighbour> neighbours) {
        mNeighbours = neighbours;
        myNeighbourRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickNeighbour(Neighbour neighbour) {
        Intent intent = new Intent(getActivity(), NeighbourDetailsActivity.class);

        intent.putExtra(BUNDLE_EXTRA_NEIGHBOUR, neighbour);

        if (getActivity() != null)
            getActivity().startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);

    }

    @Override
    public void onDeleteNeighbour(Neighbour neighbour) {
        mNeighbours.remove(neighbour);
        initList();
    }

}
