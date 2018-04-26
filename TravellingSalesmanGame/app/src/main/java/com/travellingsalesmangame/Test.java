package com.travellingsalesmangame;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.travellingsalesmangame.Views.Game.StateMenu_Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test extends Fragment implements View.OnClickListener{

    private View view;

    private Handler handler = new Handler();
    private List<String[]> list;
    private TextView txtSkor, txtSoru, txtSure;
    private ProgressBar progressBar;
    private Button A, B, C, D;
    private int progressStatus = 60, txtPuanInt = 0, sayac = 0,Soru=0;
    private boolean suspended = false;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private void init() {

        txtSure = view.findViewById(R.id.txtSure);
        txtSkor = view.findViewById(R.id.txtviewSkor);
        txtSoru = view.findViewById(R.id.txtSoru);
        progressBar = view.findViewById(R.id.progressBar);
        A = view.findViewById(R.id.A);
        B = view.findViewById(R.id.B);
        C = view.findViewById(R.id.C);
        D = view.findViewById(R.id.D);
        //sonrakiSoru = view.findViewById(R.id.SonrakiSoru);
    }

    private void oyunuBaslat() {
        btnVarsayılan();

        if (Soru>9) {

            Test_sonuc sonuc=new Test_sonuc();

            Bundle bundle=new Bundle();
            bundle.putInt("test_sonucu",txtPuanInt);

            sonuc.setArguments(bundle);

            fragmentManager=getFragmentManager();
            transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.context_main,sonuc);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        txtSoru.setText(list.get(Soru)[0]);
        A.setText("A ) " + list.get(Soru)[1]);
        B.setText("B ) " + list.get(Soru)[3]);
        C.setText("C ) " + list.get(Soru)[5]);
        D.setText("D ) " + list.get(Soru)[7]);
    }

    private void btnVarsayılan(){

        A.setBackgroundResource(R.drawable.shape);
        A.setTextColor(Color.WHITE);
        B.setBackgroundResource(R.drawable.shape);
        B.setTextColor(Color.WHITE);
        C.setBackgroundResource(R.drawable.shape);
        C.setTextColor(Color.WHITE);
        D.setBackgroundResource(R.drawable.shape);
        D.setTextColor(Color.WHITE);
        click_true();
    }

    //Ekranda gösterilecek solan sorular burada okunup ArrayListe taşınıyor.
    private void readFile() {

        list = new ArrayList<>();
        try {

            InputStream inputStream = view.getContext().getAssets().open("sorular.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String satir = "";
            while ((satir = bufferedReader.readLine()) != null) {
                list.add(satir.split(";"));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_test,container,false);
        view.findViewById(R.id.A).setOnClickListener(this);
        view.findViewById(R.id.B).setOnClickListener(this);
        view.findViewById(R.id.C).setOnClickListener(this);
        view.findViewById(R.id.D).setOnClickListener(this);
        view.findViewById(R.id.SonrakiSoru).setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();
        readFile();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        timeStart();
        oyunuBaslat();
    }


    public void A_btn_click(View view) {
        if (list.get(sayac)[2].equals("1")) {
            A.setBackgroundColor(Color.GREEN);
            txtPuanInt += 10;
            txtSkor.setText("Skor  : " + txtPuanInt);
            suspended = true;
            click_false();
            sayac++;
        }
        else {
            A.setBackgroundColor(Color.RED);
            txtPuanInt += 0;
            txtSkor.setText("Skor  : " + txtPuanInt);
            suspended=true;
            click_false();
            sayac++;
        }

    }
    public void B_btn_click(View view) {
        if (list.get(sayac)[4].equals("1")) {
            B.setBackgroundColor(Color.GREEN);
            txtPuanInt += 10;
            txtSkor.setText("Skor  : " + txtPuanInt);
            sayac++;
            suspended=true;
            click_false();
        } else {
            B.setBackgroundColor(Color.RED);
            txtPuanInt += 0;
            txtSkor.setText("Skor  : " + txtPuanInt);
            sayac++;
            suspended=true;
            click_false();
        }
    }
    public void C_btn_click(View view) {
        if ( list.get(sayac)[6].equals("1")) {
            C.setBackgroundColor(Color.GREEN);
            txtPuanInt += 10;
            txtSkor.setText("Skor  : " + txtPuanInt);
            suspended=true;
            click_false();
            sayac++;
        }
        else {
            C.setBackgroundColor(Color.RED);
            txtPuanInt += 0;
            txtSkor.setText("Skor  : " + txtPuanInt);
            suspended=true;
            click_false();
            sayac++;
        }
    }
    public void D_btn_click(View view) {
        if ( list.get(sayac)[8].equals("1")) {
            D.setBackgroundColor(Color.GREEN);
            txtPuanInt += 10;
            txtSkor.setText("Skor  : " + txtPuanInt);
            suspended=true;
            click_false();
            sayac++;

        } else {
            D.setBackgroundColor(Color.RED);
            txtPuanInt += 0;
            txtSkor.setText("Skor  : " + txtPuanInt);
            suspended=true;
            click_false();
            sayac++;
        }
    }

    public void sonraki_click(View view) {

        suspended=false;
        oyunuBaslat();
        Soru++;
    }

    public void click_false(){
        A.setClickable(false);
        B.setClickable(false);
        C.setClickable(false);
        D.setClickable(false);
    }
    public void click_true() {
        A.setClickable(true);
        B.setClickable(true);
        C.setClickable(true);
        D.setClickable(true);
    }

    @Override
    public void onClick(View v) {

        int id=v.getId();
        switch (id){
            case R.id.A:A_btn_click(v);break;
            case R.id.B:B_btn_click(v);break;
            case R.id.C:C_btn_click(v);break;
            case R.id.D:D_btn_click(v);break;
            case R.id.SonrakiSoru:sonraki_click(v);break;
        }
    }

    // Uygulama 60sn süre ile progressbar ın değerini gösterir.
    private void timeStart() {
        // Start long running operation in a background thread
        new Thread(new Runnable() {

            public void run() {
                while (progressStatus >0) {
                    while (suspended) { //Eğer kronometre durdurulduysa bekle
                        try {
                            // Sleep for 500 milliseconds.
                            Thread.sleep(600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    progressStatus -= 1;
                    // Update the progress bar and display the
                    //current value in the text view
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            txtSure.setText(progressStatus + "/" + progressBar.getMax());
                        }
                    });

                    if (progressStatus == 0) {

                        Test_sonuc sonuc=new Test_sonuc();

                        Bundle bundle=new Bundle();
                        bundle.putInt("test_sonucu",txtPuanInt);

                        sonuc.setArguments(bundle);

                        fragmentManager=getFragmentManager();
                        transaction=fragmentManager.beginTransaction();
                        transaction.replace(R.id.context_main,sonuc);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    try {
                        // Sleep for 500 milliseconds.
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
