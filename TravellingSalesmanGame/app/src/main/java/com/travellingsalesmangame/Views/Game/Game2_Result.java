package com.travellingsalesmangame.Views.Game;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.travellingsalesmangame.Models.Game.Result;
import com.travellingsalesmangame.R;

public class Game2_Result extends Fragment {

    private View view;
    private ImageView imgView;
    private TextView txtYorum,txtSure_Sonuc,txtPuan_Sonuc,txtSkorGoster;
    private int levelSaved,levelClicked;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private Button btn_Oyun;
    private Result result;
    private void init(){

        getActivity().setTitle("Oyun Skorunuz");
        imgView=view.findViewById(R.id.imgView);
        btn_Oyun=view.findViewById(R.id.btn_Oyun);

        txtYorum=view.findViewById(R.id.txtYorum);
        txtPuan_Sonuc=view.findViewById(R.id.txtPuan_Sonuc);
        txtSure_Sonuc=view.findViewById(R.id.txtSure_Sonuc);
        txtSkorGoster=view.findViewById(R.id.txtSkorGoster);


        Bundle bundle =getArguments();
        result= (Result) bundle.getSerializable("result");

        if(result.getUser_skor()>result.getPc_skor())
            txtYorum.setText("Üzgünüm! Bilgisayarın skorunu geçemediniz.");

        else if(result.getUser_skor()==result.getPc_skor())
            txtYorum.setText("Beraber! Bilgisayar ile aynı skoru buldunuz.");

        else
            txtYorum.setText("Tebrikler! Bilgisayarın skorundan daha iyi bir yol buldunuz");

        txtSure_Sonuc.setText("Süre :  "+result.getSureTxt());
        txtPuan_Sonuc.setText("Kazanılan Puan : "+String.valueOf(result.getPuan()));
        txtSkorGoster.setText("Bilgisayarın Skoru : "+result.getPc_skor()+"\n\nKullanıcının Skoru : "+result.getUser_skor()+"\n\n");

        levelClicked=result.getLevelClicked();
        levelSaved=result.getLevelSaved();

        if(result.getPuan()>0)
            imgView.setImageResource(R.drawable.prize);

        else
            imgView.setImageResource(R.drawable.odul_kayip);

    }

    @Override
    public void onResume() {
        super.onResume();
        btn_Oyun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(result.isLevel_state_durum()==true){
                    StateMenu2_Fragment state=new StateMenu2_Fragment();

                    Bundle bundle=new Bundle();
                    bundle.putInt("levelSaved",levelSaved);
                    bundle.putInt("levelClicked",levelClicked);

                    state.setArguments(bundle);
                    fragmentManager=getFragmentManager();
                    transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.context_main,state);
                    transaction.commit();
                }
                else {
                    LevelMenu2_Fragment level=new LevelMenu2_Fragment();
                    fragmentManager=getFragmentManager();
                    transaction=fragmentManager.beginTransaction();
                    transaction.replace(R.id.context_main,level);
                    transaction.commit();
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_game2_result,container,false);
        init();
        return view;
    }
}
