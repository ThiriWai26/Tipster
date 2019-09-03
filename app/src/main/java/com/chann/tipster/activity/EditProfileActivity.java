package com.chann.tipster.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chann.tipster.R;
import com.chann.tipster.data.EdiProfile;
import com.chann.tipster.retrofit.RetrofitService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        imageView = findViewById(R.id.imageView);

    }

    public void onBtnClick(View view) {

        if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EditProfileActivity.this,
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();

            imageView.setImageURI(uri);

            String imagePath = getImageFilePath(data.getData());

            if(imagePath==null){
                Log.e("Image Path","null");
            }
            else{
                Log.e("Image Path",imagePath);
                uploadServer(imagePath);
            }


        }
    }

    public String getImageFilePath(Uri uri) {

        File file = new File(uri.getPath());
        String[] filePath = file.getPath().split(":");
        String image_id = filePath[filePath.length - 1];
        Cursor cursor = getContentResolver().query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + " = ? ", new String[]{image_id}, null);
        if (cursor != null) {
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            return  imagePath;
        }

        return null;
    }

    public void uploadServer (String imagePath){

        RequestBody name = RequestBody.create( MediaType.parse("multipart/form-data") , "Chan Myae Moe");
        RequestBody token = RequestBody.create( MediaType.parse("multipart/form-data"),"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vMTM0LjIwOS4xMDIuMjA5L2FwaS9sb2dpbiIsImlhdCI6MTU2NzQ5OTU4OSwiZXhwIjoxNTY4NzA5MTg5LCJuYmYiOjE1Njc0OTk1ODksImp0aSI6IjFBdWV4aVF4TUQwMzMyZnIiLCJzdWIiOjUsInBydiI6IjIzYmQ1Yzg5NDlmNjAwYWRiMzllNzAxYzQwMDg3MmRiN2E1OTc2ZjcifQ.5XKZVuNaAljEwHno2bygSMZ6mnqlEwq1PdiZLt6SSqg");
        MultipartBody.Part image = null;
        File file = new File(imagePath);

        RequestBody imageBody = RequestBody.create( MediaType.parse("multipart/form-data"),file);
        image = MultipartBody.Part.createFormData("image",file.getName(),imageBody);

        Log.e("file name",file.getName());
        RetrofitService.getApiEnd().profileEdit(token,name,image).enqueue(new Callback<EdiProfile>() {
            @Override
            public void onResponse(Call<EdiProfile> call, Response<EdiProfile> response) {
                if(response.isSuccessful()){
                    if(response.body().isSuccess){
                        Log.e("upload","successful");
                    }
                    else {
                        Log.e("upload","fail");
                    }
                }
                else{
                    Log.e("response","fail");
                }
            }

            @Override
            public void onFailure(Call<EdiProfile> call, Throwable t) {

                Log.e("onfailure",t.toString());
            }
        });
        
    }
}
