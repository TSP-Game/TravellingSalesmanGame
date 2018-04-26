package com.travellingsalesmangame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Test_sonuc extends Fragment {

    private View view;
    private TextView txtView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_test_sonuc,container,false);
        txtView=view.findViewById(R.id.txtView);
        Bundle bundle=getArguments();
        int sayi=bundle.getInt("test_sonucu",0);
        txtView.setText(String.valueOf(sayi));
        return view;
    }
}
