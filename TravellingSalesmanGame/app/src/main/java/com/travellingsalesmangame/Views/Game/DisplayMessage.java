package com.travellingsalesmangame.Views.Game;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;

import com.travellingsalesmangame.R;

public class DisplayMessage extends Fragment {

    //Oyun bitince level menuye dönmeye yarayacak methot
    public void show(final GameActivity_Fragment context, String title, String text){

        AlertDialog alertMessage = new AlertDialog.Builder(context.getActivity(),AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).create();
        alertMessage.setTitle(title);
        alertMessage.setMessage(text);
        alertMessage.setCancelable(false);
        alertMessage.setIcon(R.mipmap.information); // icon atanacak
        alertMessage.setButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertMessage.show();
    }
}
