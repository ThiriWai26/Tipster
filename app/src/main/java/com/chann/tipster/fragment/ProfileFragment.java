package com.chann.tipster.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.tipster.R;
import com.chann.tipster.data.EditProfile;
import com.chann.tipster.data.Profile;
import com.chann.tipster.data.Token;
import com.chann.tipster.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {


    private de.hdodenhof.circleimageview.CircleImageView imgProfile;
    private TextView tvSave,tvName,tvRank;
    private String editName = "";
    private String imagePath = "";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        getProfileFeatures();
        return view;
    }

    private void getProfileFeatures() {

        RetrofitService.getApiEnd().getProfile(Token.token).enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.isSuccessful()){

                    if(response.body().isSuccess){

                        Picasso.get().load(RetrofitService.BASE_URL+"/api/get_image/"+response.body().image).resize(100,100).into(imgProfile);
                        tvName.setText(response.body().name);
                        tvRank.setText(response.body().tipStarRank);
                        Log.e("image",response.body().image);

                    }else {
                        Toast.makeText(getContext(),"Sorry , something went wrong.",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getContext(),"Sorry , something went wrong.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

                Log.e("onfailure", t.toString());
            }
        });
    }

    private void init(View view) {

        imgProfile = view.findViewById( R.id.profile);
        tvSave = view.findViewById(R.id.tvSave);
        tvName = view.findViewById(R.id.tvName);
        tvRank = view.findViewById(R.id.tvRank);

        imgProfile.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvName.setOnClickListener(this);


    }

    @Override
    public void onClick(final View view) {

       if(view == imgProfile){

           if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                   != PackageManager.PERMISSION_GRANTED) {
               ActivityCompat.requestPermissions(this.getActivity(),
                       new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                       2);
           } else {
               Intent intent = new Intent();
               intent.setType("image/*");
               intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);

           }

       }
       if(view == tvName){


           AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
           builder.setTitle("Enter User Name");

           final EditText etName = new EditText(this.getContext());
           etName.setInputType(InputType.TYPE_CLASS_TEXT);

           builder.setView(etName);

           builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {

                   if(etName.getText().toString().equals("")){
                       dialogInterface.cancel();
                   }
                   else {
                       editName = etName.getText().toString();
                       tvName.setText(editName);
                       tvSave.setVisibility(View.VISIBLE);
                   }

               }
           });

           builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {

                   dialogInterface.cancel();
               }
           });

           builder.show();

       }
       if( view == tvSave){

            uploadServer();

       }

    }

    private void uploadServer() {

        if(editName.equals("")){
            editName = tvName.getText().toString();
        }

        if( imagePath.equals("")){
            Toast.makeText(getContext(),"Please choose an image",Toast.LENGTH_LONG).show();
        }
        else {

            tvSave.setVisibility(View.GONE);

            RequestBody name = RequestBody.create( MediaType.parse("multipart/form-data") , editName);
            RequestBody token = RequestBody.create( MediaType.parse("multipart/form-data"), Token.token);
            MultipartBody.Part image = null;
            File file = new File(imagePath);

            Log.e("fileimagePath",imagePath);
            Log.e("file name",editName);
            Log.e("token",Token.token);

            RequestBody imageBody = RequestBody.create( MediaType.parse("multipart/form-data"),file);
            image = MultipartBody.Part.createFormData("image",file.getName(),imageBody);

            Log.e("file name",file.getName());
            RetrofitService.getApiEnd().profileEdit(token,name,image).enqueue(new Callback<EditProfile>() {
                @Override
                public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                    if(response.isSuccessful()){
                        if(response.body().isSuccess){
                            Log.e("upload","successful");
                            Toast.makeText(getContext(),"Update Successfully",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Log.e("upload","fail");
                            Toast.makeText(getContext(),"Update fail",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Log.e("upload","not successful");
                        Toast.makeText(getContext(),"Update fail",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<EditProfile> call, Throwable t) {
                    Log.e("onfailure",t.toString());
                    Toast.makeText(getContext(),"Sorry , something went wrong.",Toast.LENGTH_LONG).show();
                }
            });
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();

            imgProfile.setImageURI(uri);

            imagePath = getImageFilePath(data.getData());
            tvSave.setVisibility(View.VISIBLE);
            
        }
    }

    private String getImageFilePath(Uri data) {
        File file = new File(data.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContext().getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            Log.e("imagePath",imagePath);
            return  imagePath;
        }

        return null;
    }
    
}
