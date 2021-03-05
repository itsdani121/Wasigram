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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.wasigram.databinding.FragmentCommentListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class commentListFragment extends Fragment {

    ArrayList<commentsListModel> listModels = new ArrayList<>();
    commentListAdapter listAdapter;
    String JSON_URl = "https://wasisoft.com/dev/index.php";
    FragmentCommentListBinding commentListBinding;
    Toolbar toolbar;

    public commentListFragment() {
        // Required empty public constructor
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


        showList();
    }

    void showList() {
        AndroidNetworking.get(JSON_URl)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        if (response != null) {
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                   /* newsFeedModel FeedModel = new newsFeedModel();
                                    FeedModel.setTitleName(object.getString("account_name"));
                                    FeedModel.setMediaType(object.getString("media_type"));
                                    FeedModel.setVideo(object.getString("media"));
                                    FeedModel.setViewImg(object.getString("media"));
                                    String like = object.getString("likes");
                                    FeedModel.setLike(like + " Likes");
                                    description[i] = object.getString("desc");
                                    limitDescription(FeedModel, i, description, false);
                                    int count = Integer.parseInt(comments.getString("count"));
                                    FeedModel.setViewAllComments("view all " + count + " comments");*/
                                    JSONObject comments = object.getJSONObject("comments");
                                    commentsListModel model = new commentsListModel();
                                    JSONArray commentDetails = comments.getJSONArray("comments");
                                    JSONObject commentObject = commentDetails.getJSONObject(i);
                                    model.setUserName(commentObject.getString("account_name"));
                                    model.setUserComments(commentObject.getString("comment"));
                                    model.setMedia(commentObject.getString("media"));
                                    model.setUserLike(String.valueOf(R.drawable.ic_like));
                                    listModels.add(model);
                                    //listModels.add(model);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            uploadFeedData(listModels);
                            /*uploadImageComments(listModels);
                            uploadVideoComments(listModels);*/
                        } else {
                            Toast.makeText(requireActivity(), "None ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(requireActivity(), "No Value " + error.getErrorBody(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void uploadFeedData(ArrayList<commentsListModel> feedModel) {
        //set data object
        listAdapter = new commentListAdapter(requireContext(), feedModel);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext());
        manager.setSmoothScrollbarEnabled(true);
        commentListBinding.videoCommentsListRecycler.setLayoutManager(manager);
        listAdapter.notifyDataSetChanged();
        commentListBinding.videoCommentsListRecycler.setAdapter(listAdapter);
        commentListBinding.videoCommentsListRecycler.setHasFixedSize(true);
    }

}