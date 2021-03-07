package com.example.wasigram;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.wasigram.databinding.FragmentCommentListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class commentListFragment extends Fragment {

    ArrayList<commentsListModel> listModels = new ArrayList<>();
    commentListAdapter listAdapter;
    String JSON_URl = ApiUrl.posts;
    FragmentCommentListBinding commentListBinding;
    Toolbar toolbar;
    String post_id;
    Database database;

    public commentListFragment() {
        // Required empty public constructor
        database = Database.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        commentListBinding = FragmentCommentListBinding.inflate(inflater, container, false);
        View view = commentListBinding.getRoot();
        toolbar = view.findViewById(R.id.comments_list_toolbar);
        toolbar.setTitle("Comments List");
        toolbar.setTitleTextColor(Color.WHITE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        valuesDefault();
        showList();
    }

    void valuesDefault() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            post_id = bundle.getString("post_id");
        }
    }

    void showList() {
        database.getJsonArray(JSON_URl + post_id + ApiUrl.comments, new OnArrayClickListener() {
            @Override
            public void onSuccessFullResponse(JSONArray response) {
                if (isAdded()) {

                    if (response != null) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                commentsListModel model = new commentsListModel();
                                model.setUserName(object.getString("username"));
                                model.setUserComments(object.getString("comment"));
                                listModels.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        uploadFeedData(listModels);
                    } else {
                        Toast.makeText(requireActivity(), "None ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailedResponse(String error) {
                Toast.makeText(requireActivity(), "No Value " + error, Toast.LENGTH_SHORT).show();

            }


        });

    }

    void uploadFeedData(ArrayList<commentsListModel> feedModel) {
        //set data object
        listAdapter = new commentListAdapter(requireContext(), feedModel);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setSmoothScrollbarEnabled(true);
        commentListBinding.videoCommentsListRecycler.setLayoutManager(manager);
        commentListBinding.videoCommentsListRecycler.setAdapter(listAdapter);
        commentListBinding.videoCommentsListRecycler.setHasFixedSize(true);
        listAdapter.notifyDataSetChanged();
    }
}