package com.example.backgroundchanger;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.backgroundchanger.entity.Picture;
import com.example.backgroundchanger.lab.PictureLab;
import com.example.changer.R;

import java.util.List;

public class PictureListFragment extends Fragment {
    private RecyclerView mPictureRecyclerView;
    private PictureAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture_list,
                                    container,
                                    false);
        mPictureRecyclerView = view.findViewById(R.id.picture_recycler_view);
        mPictureRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void updateUI() {
        PictureLab pictureLab = PictureLab.get(getActivity());
        List<Picture> crimes = pictureLab.getPictures();
        int number;
        if(mAdapter == null){
            mAdapter = new PictureAdapter(crimes);
            mPictureRecyclerView.setAdapter(mAdapter);
        } else {
            //number = 0;
            //mAdapter.notifyItemChanged(number);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class PictureHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mSolvedCheckBox;

        private Picture mPicture;

        public PictureHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void bindPicture(Picture picture) {
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            mPicture = picture;
        }

        @Override
        public void onClick(View v) {
            //TODO choose background
        }
    }

    private class PictureAdapter extends RecyclerView.Adapter<PictureHolder> {
        private List<Picture> mPictures;

        public PictureAdapter(List<Picture> pictures) {
            mPictures = pictures;
        }

        @Override
        public PictureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_picture, parent, false);
            return new PictureHolder(view);
        }

        @Override
        public void onBindViewHolder(PictureHolder holder, int position) {
            Picture picture = mPictures.get(position);
            holder.bindPicture(picture);
        }

        @Override
        public int getItemCount() {
            return mPictures.size();
        }
    }
}
