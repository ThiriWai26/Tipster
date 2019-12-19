package com.chann.tipster.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.chann.tipster.R;
import com.chann.tipster.activity.LoginActivity;
import com.chann.tipster.data.EditProfile;
import com.chann.tipster.data.IsSuccess;
import com.chann.tipster.data.Profile;
import com.chann.tipster.data.Token;
import com.chann.tipster.databinding.FragmentProfileBinding;
import com.chann.tipster.retrofit.RetrofitService;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
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

        Log.e("profile edit",throwable.toString());
    }

    private void handleResult(Profile profile) {

        binding.setProfile(profile);
        binding.progressBar.setVisibility(View.GONE);
        if(profile.image != null){
            Picasso.get().load(RetrofitService.BASE_URL+"/api/get_image/"+profile.image).resize(200,200).placeholder(R.drawable.img_user).into(binding.profile);
        }
        else {
            Picasso.get().load(profile.fbProfile).resize(200,200).placeholder(R.drawable.img_user).into(binding.profile);

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
                Disposable subscribe = RetrofitService.getApiEnd().changeName(Token.token, editName)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleEditProfile, this::handleError);
                disposable.add(subscribe);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void onClickSave(View view){

        if( imagePath.equals("")){
            Toast.makeText(getContext(),"Please choose an image",Toast.LENGTH_LONG).show();
        }
        else {

            binding.tvSave.setVisibility(View.GONE);

//            RequestBody name = RequestBody.create( MediaType.parse("multipart/form-data") , editName);
            RequestBody token = RequestBody.create( MediaType.parse("multipart/form-data"), Token.token);
            MultipartBody.Part image = null;
            File file = new File(imagePath);
            Log.e("originalfilesize",String.valueOf(file.length()/1024));
            File compressFile = null;

            try {
                compressFile = new Compressor(getContext()).compressToFile(file);
                Log.e("compressfilesize",String.valueOf(compressFile.length()/1024));
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.e("fileimagePath",imagePath);
            Log.e("file name",editName);
            Log.e("token",Token.token);

            RequestBody imageBody = RequestBody.create( MediaType.parse("multipart/form-data"), compressFile);
            image = MultipartBody.Part.createFormData("image",file.getName(),imageBody);

            Log.e("file name",file.getName());

            Disposable subscribe = RetrofitService.getApiEnd().changePhoto(token, image)
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


    private void handleEditProfile(IsSuccess isSuccess) {

        if(isSuccess.isSuccess){
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

    public void onClickItem(int itemType){

        Fragment fragment;
        if(itemType==1){
            fragment = new RankFragment();
            loadFragment(fragment);
        }
        if(itemType==2){

            fragment = new BetHistoryFragment();
            loadFragment(fragment);
        }
        if(itemType==3){
            Toast.makeText(getContext(),"About App",Toast.LENGTH_LONG).show();
        }
        if(itemType==4){
            Toast.makeText(getContext(),"Setting",Toast.LENGTH_LONG).show();
        }
        if(itemType==5){

            final TextView save , tvCancel;

            final Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_logout);

            save = dialog.findViewById(R.id.tvSave);
            tvCancel = dialog.findViewById(R.id.tvCancel);

            tvCancel.setOnClickListener(view -> dialog.dismiss());

            save.setOnClickListener(view -> {
                SharedPreferences pref;// 0 - for private mode
                SharedPreferences.Editor editor;
                pref = getContext().getApplicationContext().getSharedPreferences("MyPref", 0);
                editor = pref.edit();

                //fb account logout
                if (pref.getString("fb_token", null) != null)
                    LoginManager.getInstance().logOut();

                editor.clear();
                editor.apply();
                editor.commit();
                dialog.dismiss();

                Intent intent = new Intent(getContext() , LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            });

            dialog.show();

        }
        Log.e("onclickitem",String.valueOf(itemType));

    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack("Tag").commit();
    }
}
