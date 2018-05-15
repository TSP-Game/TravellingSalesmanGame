package com.travellingsalesmangame;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.travellingsalesmangame.Controllers.Login.Encode;
import com.travellingsalesmangame.Models.Hash192.MyHash;
import com.travellingsalesmangame.Models.Login.User;
import com.travellingsalesmangame.Views.Game.Master_Main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;


import static android.app.Activity.RESULT_OK;

public class Profil extends Fragment{

    private final DatabaseReference users = FirebaseDatabase.getInstance().getReference("User_b327a12217d490250cc533b28ddf2be79d3e6c5591a96ec3");
    private final DatabaseReference salts = FirebaseDatabase.getInstance().getReference("Salt_8ff2ba9c135413f689dc257d70a4a75091110497a69c5b3c");

    private View view;
    private SharedPreferences prefs;
    private User user;
    private Gson gson;
    private EditText txtKullaniciAdi,txtEmail,txtParola,txtParolaYeni;

    private Button btnChose,btnUpdate;
    private Uri filePath;
    private ImageView profileImageView;
    private final int PICK_IMAGE_REQUEST = 71;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ValueEventListener listenerSalt,listenerUser;
    private String salt,paswordInput;
    private MyHash myHash;

    private FirebaseStorage fStorage;


    private void init(){
        gson=new Gson();
        getActivity().setTitle("Profil Bilgileri");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        txtKullaniciAdi=view.findViewById(R.id.txtKullaniciAdi);
        txtEmail=view.findViewById(R.id.txtEmail);
        txtParola=view.findViewById(R.id.txtParola);
        txtParolaYeni=view.findViewById(R.id.txtParolaYeni);
        btnChose=view.findViewById(R.id.btnChoose);
        btnUpdate=view.findViewById(R.id.btnUpdate);
        profileImageView=view.findViewById(R.id.imgProfil);

        prefs= PreferenceManager.getDefaultSharedPreferences(view.getContext());
        String json=prefs.getString("user","");

        user=new User(gson.fromJson(json,User.class));
        txtKullaniciAdi.setText(user.getUserName());
        txtEmail.setText(user.getEmail());
        txtEmail.setEnabled(false);
        txtEmail.setKeyListener(null);

        myHash = new MyHash();

        // Read salt from the database
        listenerSalt = new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    salt = dataSnapshot.getValue(String.class);

                    if(salt != null && salt.length() == 48) {
                        users.child(Encode.encode(user.getEmail())).addValueEventListener(listenerUser);
                        salts.child(Encode.encode(user.getEmail())).removeEventListener(listenerSalt);
                    }
                    else
                        Toast.makeText(getActivity(), "Hatalı parola", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getActivity(), "Hatalı parola", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), R.string.error_database_read, Toast.LENGTH_SHORT).show();
            }
        };

        // Read user from the database
        listenerUser = new ValueEventListener() {       //veri tabanı dinleyicisi
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                paswordInput = String.valueOf(txtParola.getText());
                paswordInput = myHash.hash(myHash.hash(paswordInput)+salt);

                if(dataSnapshot.child("password").exists()
                        && dataSnapshot.child("password").getValue() != null
                        && dataSnapshot.child("password").getValue(String.class).length() == 48
                        ){                                                                          //geçerli kullanıcı olup olmama durumu

                    String parola = (dataSnapshot.child("password").getValue(String.class));

                    if(parola.equals(paswordInput)) {//şifrelerin eşleşme durumu

                        salt = createRandomSalt();
                        paswordInput = String.valueOf(txtParolaYeni.getText());
                        paswordInput = myHash.hash(myHash.hash(paswordInput)+salt);
                        user.setPassword(paswordInput);
                        user.setUserName(String.valueOf(txtKullaniciAdi.getText()));

                        users.child(Encode.encode(user.getEmail())).setValue(user);
                        salts.child(Encode.encode(user.getEmail())).setValue(salt);

                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                        SharedPreferences.Editor preEditor = pref.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        preEditor.putString("user", json);
                        preEditor.apply();

                        //bellekteki verileri silme, güvenlik için
                        salt = null;

                        Toast.makeText(getActivity(), "Bilgileriniz Güncellendi!", Toast.LENGTH_SHORT).show();

                        users.child(Encode.encode(user.getEmail())).removeEventListener(listenerUser);
                    }
                    else{
                        Toast.makeText(getActivity(), "Hatalı parola", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Hatalı parola", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getActivity(), R.string.error_database_read, Toast.LENGTH_SHORT).show();
            }
        };

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                salts.child(Encode.encode(user.getEmail())).addValueEventListener(listenerSalt);
            }
        });

        btnChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        fStorage = FirebaseStorage.getInstance();

        StorageReference storageRef = fStorage.getReference().child("images").child(user.getEmail());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {

                try {
                    URL url = new URL(uri.toString());
                    Bitmap bitImage = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    profileImageView.setImageBitmap(bitImage);
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    //rastgele tuz üretimi; 16'lik sistemde 48 karakter (farkli da olabilirdi, hash ciktim ile ayni olsun istedim)
    private String createRandomSalt() {

        Random rand = new Random();
        StringBuilder salt=new StringBuilder();
        for(int i=0; i<48; i++)
            salt.append(Integer.toHexString(rand.nextInt(16)));

        return salt.toString();
    }

    private void uploadImage() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ user.getEmail());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                profileImageView.setImageBitmap(bitmap);
                Toast.makeText(getActivity(),"Resim alındı",Toast.LENGTH_SHORT).show();
                uploadImage();
                Intent intent=new Intent(getActivity(), Master_Main.class);
                startActivity(intent);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        view=inflater.inflate(R.layout.activity_profil,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        init();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            salts.child(Encode.encode(user.getEmail())).removeEventListener(listenerSalt);
            users.child(Encode.encode(user.getEmail())).removeEventListener(listenerUser);
        }catch (Exception ignored){}
    }
}
