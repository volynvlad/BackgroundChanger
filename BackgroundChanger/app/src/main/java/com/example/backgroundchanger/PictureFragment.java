package com.example.backgroundchanger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.backgroundchanger.entity.Picture;
import com.example.backgroundchanger.lab.PictureLab;
import com.example.changer.R;

public class PictureFragment extends Fragment {
    private Picture mPicture;
    private ImageButton mImageButton;

    private static final String ARG_PICTURE_ID = "picture_id";

    public static PictureFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PICTURE_ID, id);
        PictureFragment fragment = new PictureFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int crimeId = getArguments().getInt(ARG_PICTURE_ID);
        mPicture = PictureLab.get(getActivity()).getPicture(crimeId);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        return view;
    }
}
