package com.bvrith.snapdirect;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bvrith.snapdirect.common.Snapdirect;
import com.bvrith.snapdirect.gson.FeedEntryJson;
import com.bvrith.snapdirect.network.FeedLoader;

import java.util.ArrayList;

@SuppressLint("NewApi") 
public class FeedFragmentTab extends Fragment {

    private FeedAdapter mAdapter;
    private FeedLoader mFeedLoader;
    private ArrayList<FeedEntryJson> feedEntryList = new ArrayList<FeedEntryJson>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FeedAdapter(getActivity(), R.layout.item_feed, feedEntryList);
        mFeedLoader = new FeedLoader();
        Snapdirect.setFeedFragmentTab(this);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        FeedListView listView = (FeedListView) rootView.findViewById(R.id.feedList);

        mFeedLoader.setFeedListView(listView);
        listView.setFeedComponents(mFeedLoader, mAdapter, feedEntryList);
        listView.setOnItemClickListener(OnItemClickListener);
        return rootView;
    }

    AdapterView.OnItemClickListener OnItemClickListener
            = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            FeedEntryJson feedItem = mAdapter.getItem(position);
            Intent mIntent = new Intent(Snapdirect.getMainActivity(), ImageDetailsActivity.class);
            Snapdirect.setImageDetails(feedItem);
            startActivity(mIntent);
        }
    };

}
