package com.travellingsalesmangame;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Models.Game.GameInfo;
import com.travellingsalesmangame.Models.Game.ListModel;
import com.travellingsalesmangame.Models.Login.User;

import static com.travellingsalesmangame.R.color.orange;

public class Istatistik extends Fragment {

    private final DatabaseReference games = FirebaseDatabase.getInstance().getReference("Game_eee653b64ab2ff1051e13c092396179e9d29bbc7ed6aa4a8");
    private ValueEventListener listenerGameInfo;
    AdapterView.OnItemSelectedListener listenerSpinner;
    private GameInfo gameInfo;
    private User user;
    private SharedPreferences prefs;
    private View view;
    private ListModel model;
    private Spinner spinner;

    private void init(){

        getActivity().setTitle("İstatistikler");

        Gson gson=new Gson();

        prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json=prefs.getString("user","");
        user=new User(gson.fromJson(json,User.class));


        listenerSpinner = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){
                    //games.addValueEventListener(listenerGameInfo);
                    gameScorelistele();
                    getActivity().setTitle("Kullanıcı Skorları");
                }
                else if(position==2){
                    //games.addValueEventListener(listenerGameInfo);
                    gamePcScorelistele();
                    getActivity().setTitle("Bilgisayar Skorları");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };

        listenerGameInfo=new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    gameInfo = new GameInfo();
                    gameInfo =  dataSnapshot.getValue(GameInfo.class);

                    if (gameInfo==null) {
                        spinner.setOnItemSelectedListener(null);
                        gameInfo = null;
                        Toast.makeText(getActivity(), "Sunucu Hatası!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        spinner.setOnItemSelectedListener(listenerSpinner);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        };
    }

    private void gameScorelistele(){
        model=new ListModel();
        model.listeyiDoldur(gameInfo);
        ListView lstView=view.findViewById(R.id.lstView);
        CustomAdapter customAdapter=new CustomAdapter(getActivity());
        lstView.setAdapter(customAdapter);
        Toast.makeText(getActivity(),"Kullanıcının Skorları Listelenmiştir.",Toast.LENGTH_SHORT).show();
    }

    private void gamePcScorelistele(){
        model=new ListModel();
        model.pc_listeye_doldur(gameInfo);
        ListView lstView=view.findViewById(R.id.lstView);
        CustomAdapter_PC customAdapter=new CustomAdapter_PC(getActivity());
        lstView.setAdapter(customAdapter);
        Toast.makeText(getActivity(),"Bilgisayar Skorları Listelenmiştir.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            games.removeEventListener(listenerGameInfo);
            spinner.setOnItemSelectedListener(listenerSpinner);
        }catch (Exception ignored){}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_istatistik,container,false);
        spinner=view.findViewById(R.id.spinner);
        init();
        games.child(Encode.encode(user.getEmail())).addValueEventListener(listenerGameInfo);
        return view;
    }

    class CustomAdapter_PC extends BaseAdapter{

        LayoutInflater layoutInflater;

        public CustomAdapter_PC(Context context){
            this.layoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return model.getModelList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=layoutInflater.inflate(R.layout.custom_layout_game_pc,parent, false);

            LinearLayout linearSeviye=convertView.findViewById(R.id.linearSeviye);
            LinearLayout linearHeader=convertView.findViewById(R.id.linearHeader);
            LinearLayout linearView=convertView.findViewById(R.id.linearView);
            LinearLayout linearRoot=convertView.findViewById(R.id.linearRoot);


            TextView txtSeviye=convertView.findViewById(R.id.txtSeviye);
            TextView txtLevel=convertView.findViewById(R.id.txtLevel);
            TextView txtState=convertView.findViewById(R.id.txtState);
            TextView txtSure=convertView.findViewById(R.id.txtSure);
            TextView txtPuan=convertView.findViewById(R.id.txtPuan);
            ProgressBar progressBar=convertView.findViewById(R.id.progressBar);


            if(String.valueOf(model.getModelList().get(position)).contains("Seviye : ")){

                txtSeviye.setText(String.valueOf(model.getModelList().get(position)));
                txtSeviye.setTextColor(Color.WHITE);

                linearRoot.removeView(linearView);
                linearRoot.removeView(linearHeader);
                linearRoot.setGravity(Gravity.CENTER);

                linearSeviye.setGravity(Gravity.CENTER);
                linearSeviye.setBackgroundResource(orange);
            }
            else{

                txtSeviye.setText("");
                if(String.valueOf(model.getModelList().get(position)).contains("Level : ")){
                    txtLevel.setText(String.valueOf(model.getModelList().get(position)));
                    txtLevel.setTextColor(Color.WHITE);
                    linearRoot.removeView(linearView);
                    linearRoot.setGravity(Gravity.CENTER);
                    linearView.setGravity(Gravity.CENTER);
                    linearHeader.setBackgroundResource(orange);
                }
                else{
                    ListModel lst=(ListModel) model.getModelList().get(position);
                    txtLevel.setText("");
                    linearView.removeView(txtLevel);
                    int state=lst.getState()+1;
                    txtState.setText("State : "+state);
                    txtSure.setText("Sure  : "+lst.getMilisaniye());
                    txtPuan.setText("Puan : "+lst.getPuan());
                    progressBar.setProgress(lst.getPuan());
                }
            }

            return convertView;
        }
    }

    class CustomAdapter extends BaseAdapter{

        LayoutInflater layoutInflater;

        public CustomAdapter(Context context){
            this.layoutInflater=LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return model.getModelList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=layoutInflater.inflate(R.layout.custom_layout_game_score,parent, false);

            LinearLayout linearView=convertView.findViewById(R.id.linearView);
            LinearLayout linearRoot=convertView.findViewById(R.id.linearRoot);
            LinearLayout linearHeader=convertView.findViewById(R.id.linearHeader);

            TextView txtLevel=convertView.findViewById(R.id.txtLevel);
            TextView txtState=convertView.findViewById(R.id.txtState);
            TextView txtSure=convertView.findViewById(R.id.txtSure);
            TextView txtPuan=convertView.findViewById(R.id.txtPuan);
            ProgressBar progressBar=convertView.findViewById(R.id.progressBar);

            if(String.valueOf(model.getModelList().get(position)).contains("Level : ")){
               txtLevel.setText(String.valueOf(model.getModelList().get(position)));
               txtLevel.setTextColor(Color.WHITE);
               linearRoot.removeView(linearView);
               linearRoot.setGravity(Gravity.CENTER);
               linearView.setGravity(Gravity.CENTER);
               linearHeader.setBackgroundResource(orange);
           }
           else{
               ListModel lst=(ListModel) model.getModelList().get(position);
               txtLevel.setText("");
               linearView.removeView(txtLevel);
               int state=lst.getState()+1;
               txtState.setText("State : "+state);
               txtSure.setText("Sure  : "+lst.getMilisaniye());
               txtPuan.setText("Puan : "+lst.getPuan());
               progressBar.setProgress(lst.getPuan());
           }


            return convertView;
        }
    }
}
