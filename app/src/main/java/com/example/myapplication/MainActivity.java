package com.example.myapplication;

import android.os.Bundle;

import me.yokeyword.fragmentation.SupportActivity;

public class MainActivity extends SupportActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findFragment(OperationHistoryFragment.class) == null) {
            loadRootFragment(R.id.rootView, OperationHistoryFragment.newInstance("null","null"));  //load root Fragment
        }
    }

}
