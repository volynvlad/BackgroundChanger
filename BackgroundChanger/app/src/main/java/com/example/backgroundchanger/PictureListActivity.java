package com.example.backgroundchanger;

import android.support.v4.app.Fragment;

public class PictureListActivity extends SingleFragmentActivity {

    protected Fragment createFragment() {
        return new PictureListFragment();
    }
}
