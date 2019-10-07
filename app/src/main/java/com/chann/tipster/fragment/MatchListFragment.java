package com.chann.tipster.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chann.tipster.R;
import com.chann.tipster.activity.OddsActivity;
import com.chann.tipster.adapter.MatchDataAdapter;
import com.chann.tipster.adapter.MatchItemAdapter;
import com.chann.tipster.data.LeagueData;
import com.chann.tipster.data.MatchData;
import com.chann.tipster.data.MatchListData;
import com.chann.tipster.data.MatchListResponse;
import com.chann.tipster.data.RoomListOfLeague;
import com.chann.tipster.data.Token;
import com.chann.tipster.holderInterface.OnHolderItemClickListener;
import com.chann.tipster.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment implements OnHolderItemClickListener , View.OnClickListener{

    private RecyclerView recyclerView;
    private MatchDataAdapter adapter;
    private int roomId;
    private ImageView ivRating;

    public MatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_match_list, container, false);
        initFragment(view);
        return view;
    }

    private void initFragment(View view) {

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new MatchDataAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        getMatchList();
    }

    private void getMatchList() {

        RetrofitService.getApiEnd().getMatchList(Token.token).enqueue(new Callback<MatchListResponse>() {
            @Override
            public void onResponse(Call<MatchListResponse> call, Response<MatchListResponse> response) {

                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        roomId = response.body().roomId;
                        adapter.addData(response.body().matchListData);

                        Log.e("matchlistsize", String.valueOf(response.body().matchListData.size()));
                    }
                }
                else {
                    Log.e("response","is not successful");
                }
            }

            @Override
            public void onFailure(Call<MatchListResponse> call, Throwable t) {
                Log.e("onfailure",t.toString());
            }
        });
    }


    @Override
    public void onHolderitemClick(MatchData matchData) {
        startActivity(OddsActivity.getInstance(getContext(), matchData , roomId));
    }

    @Override
    public void onClick(View view) {



    }
}

