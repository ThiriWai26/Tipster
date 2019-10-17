package com.chann.tipster.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chann.tipster.R;
import com.chann.tipster.data.EditProfile;
import com.chann.tipster.data.Profile;
import com.chann.tipster.data.Token;
import com.chann.tipster.databinding.FragmentProfileBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private String editName = "";
    private String imagePath = "";
    private CompositeDisposable disposable;
    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        disposable = new CompositeDisposable();

        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_profile , container , false);
        binding.setFragment(this);
        Disposable profile = RetrofitService.getApiEnd().getProfile(Token.token)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult , this::handleError);
        disposable.add(profile);
        return binding.getRoot();
    }

    private void handleError(Throwable throwable) {
    }

    private void handleResult(Profile profile) {

        binding.setProfile(profile);
        binding.progressBar.setVisibility(View.GONE);
        if(profile.isSuccess){
            Picasso.get().load(RetrofitService.BASE_URL+"/api/get_image/"+profile.image).resize(100,100).placeholder(R.drawable.img_user).into(binding.profile);
        }
        else {
            Toast.makeText(getContext(),"Sorry , something went wrong.",Toast.LENGTH_LONG).show();
        }
    }

    public void onClickProfile(View view){

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
    public void onClickName(View v){
        final EditText etUsername ;
        final TextView save , tvCancel;

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_edit_profile);

        etUsername = dialog.findViewById(R.id.etUsername);
        save = dialog.findViewById(R.id.tvSave);
        tvCancel = dialog.findViewById(R.id.tvCancel);

        tvCancel.setOnClickListener(view -> dialog.dismiss());

        save.setOnClickListener(view -> {
            editName = etUsername.getText().toString();

            if(editName.equals("")){
                etUsername.setError("Enter user name");
            }
            else {
                binding.tvName.setText(editName);
                binding.tvSave.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void onClickSave(View view){
        if(editName.equals("")){
            editName =  binding.tvName.getText().toString();
        }

        if( imagePath.equals("")){
            Toast.makeText(getContext(),"Please choose an image",Toast.LENGTH_LONG).show();
        }
        else {

            binding.tvSave.setVisibility(View.GONE);

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

            Disposable subscribe = RetrofitService.getApiEnd().profileEdit(token, name, image)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleEditProfile, this::handleError);
            disposable.add(subscribe);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposable.clear();
    }


    private void handleEditProfile(EditProfile editProfile) {

        if(editProfile.isSuccess){
            Log.e("upload","successful");
            Toast.makeText(getContext(),"Update Successfully",Toast.LENGTH_LONG).show();
        }
        else {
            Log.e("upload","fail");
            Toast.makeText(getContext(),"Update fail",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && data != null) {

            Uri uri = data.getData();

            binding.profile.setImageURI(uri);

            imagePath = getImageFilePath(data.getData());
            binding.tvSave.setVisibility(View.VISIBLE);
            
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
