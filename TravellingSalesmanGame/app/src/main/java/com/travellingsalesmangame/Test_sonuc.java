package com.travellingsalesmangame;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.travellingsalesmangame.Views.Game.PopActivity;

public class Test_sonuc extends Fragment {

    private View view;
    private TextView txtView,txtCozulen;
    private Button btn_Oyun;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private void init(){
        txtView=view.findViewById(R.id.txtView);
        txtCozulen=view.findViewById(R.id.txtCozulen);
        btn_Oyun=view.findViewById(R.id.btn_Oyun);

        Bundle bundle=getArguments();
        int test_sonucu=bundle.getInt("test_sonucu",0);
        int soru_sayisi=bundle.getInt("soru_sayisi",0);
        txtView.setText(String.valueOf(test_sonucu));
        txtCozulen.setText(String.valueOf(soru_sayisi));

        btn_Oyun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Kategori_Secimi kategori=new Kategori_Secimi();
                fragmentManager=getFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.context_main,kategori);
                transaction.commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_test_sonuc,container,false);
        init();
        return view;
    }
}
