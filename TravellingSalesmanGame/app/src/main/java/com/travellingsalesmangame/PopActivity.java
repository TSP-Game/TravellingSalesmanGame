package com.travellingsalesmangame;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

public class PopActivity extends Fragment {


    private View view;
    private TextView textView;
    private List<String[]> list;
    private int sayac=0;

    private void init(){

        textView=view.findViewById(R.id.textView);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getActivity().getWindow().setLayout((int) (width * .8), (int) (height * .7));


        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getActivity().getWindow().setAttributes(params);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.activity_pop,container,false);
        return view;
    }

    /*private void readFile() {
        Lists = new List<>();
        try {

            InputStream inputStream = getAssets().open("sorular.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String satir = "";
            while ((satir = bufferedReader.readLine()) != null) {
                arrayLists.add(satir.split("!"));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }*/
}
