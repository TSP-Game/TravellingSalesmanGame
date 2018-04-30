package com.travellingsalesmangame.Views.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Controllers.Login.UserRules;
import com.travellingsalesmangame.Models.Hash192.MyHash;
import com.travellingsalesmangame.Models.Login.User;
import com.travellingsalesmangame.R;
import com.travellingsalesmangame.Views.Game.Master_layout;

public class LoginActivity extends AppCompatActivity {

    private EditText et_email,et_password;
    private TextView login_error;
    private String email,password;
    private ValueEventListener listenerUser,listenerSalt;
    private String salt = "";
    private final DatabaseReference users = FirebaseDatabase.getInstance().getReference("User");
    private final DatabaseReference salts = FirebaseDatabase.getInstance().getReference("Salt");

    private void init(){

        et_email = findViewById(R.id.login_email);
        et_password = findViewById(R.id.login_password);
        login_error = findViewById(R.id.login_error);

        final MyHash myHash = new MyHash();

        // Read salt from the database
        listenerSalt = new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                    salt = dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                login_error.setText(R.string.error_database_read);
            }
        };

        // Read user from the database
        listenerUser = new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String saltedHashedPassword = myHash.hash(myHash.hash(password)+salt);

                //Giriş butonuna basınca kontrol ediyor.
                if(!dataSnapshot.exists() || !dataSnapshot.child("password").exists() || !dataSnapshot.child("password").getValue().equals(saltedHashedPassword) || salt.equals("")) {                                            //login_onclick ile editboxtan gelen verinin veri tabaninda olmama durumu
                    login_error.setText(R.string.error_wrong_password);
                    //et_email.setVisibility(View.VISIBLE);
                    //err_password.setVisibility(View.VISIBLE);
                }
                else {

                    SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor preEditor=pref.edit();
                    Gson gson=new Gson();
                    User user=new User((String)dataSnapshot.child("userName").getValue(),
                            (String)dataSnapshot.child("email").getValue(),
                            (String)dataSnapshot.child("password").getValue());

                    String json=gson.toJson(user);
                    preEditor.putString("user",json);
                    preEditor.apply();

                    Toast.makeText(LoginActivity.this,"Giriş Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,Master_layout.class);
                    startActivity(intent);
                    finish();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                login_error.setText(R.string.error_database_read);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private boolean ruleChecker() {

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean result = true;

        if(cm.getActiveNetworkInfo() == null) {

            login_error.setText(R.string.error_network);
            result = false;
        }
        else{

            //err_email.setVisibility(View.INVISIBLE);
            //err_password.setVisibility(View.INVISIBLE);

            if(password == null || password.equals("")) {                   //sifrenin bos olma durumu

                login_error.setText(getString(R.string.error_no_password));
                //err_password.setVisibility(View.VISIBLE);
                result = false;
            }
            else if(!UserRules.check_password(password)){                        //sifrenin kurallara uymama durumu (kurala uymuyorsa veri tabanına gitmesine gerek yok)
                login_error.setText(getString(R.string.error_wrong_password));
                //err_password.setVisibility(View.VISIBLE);
                //err_email.setVisibility(View.VISIBLE);
                result = false;
            }
            if(!UserRules.check_email(email)) {                                 //girilen mail adresin desteklenmemesi olma durumu
                login_error.setText(R.string.error_invalid_email);
                //err_email.setVisibility(View.VISIBLE);
                //err_password.setVisibility(View.VISIBLE);
                result = false;
            }
        }
        return result;
    }

    public void login_sign_onclick(View view) {

        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void login_onclick(View view) {

        email = String.valueOf(et_email.getText());
        password = String.valueOf(et_password.getText());

        if(ruleChecker()){

            login_error.setText("");
            salts.child(Encode.encode(email)).addValueEventListener(listenerSalt);
            users.child(Encode.encode(email)).addValueEventListener(listenerUser);  //emaile girilen degere ait veritabanındaki referansa giris kosullarini iceren listener'ı atıyoruz. email yoksa null donuyor
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
