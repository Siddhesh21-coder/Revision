 package com.codersarena.revision.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.codersarena.revision.R;
import com.codersarena.revision.model.Image;
import com.codersarena.revision.viewmodel.MyViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.Timestamp;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


 public class AddTopicActivity extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference reference;
    private ArrayList<Uri> chooseImageList;
    private ArrayList<Image> stringUrls;
    private ArrayList<String> stringUrl = new ArrayList<>();
    private ImageButton pickImageButton;
    private String groupName="";
    private Button uploadBtn;
    private EditText edt1,edt2;
    private MyViewModel myViewModel;
    Map<String,String> stringUl = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        pickImageButton = findViewById(R.id.pickUpImage);
        chooseImageList = new ArrayList<>();
        stringUrls = new ArrayList<>();
        Intent ii = getIntent();
        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        Bundle b = ii.getExtras();
        if (b!=null){
            groupName = (String)b.get("GroupName") + (String) b.get("TopicName");
        }
        pickImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
//                pickImageFromGallery();
//                uploadImages();
            }
        });
        edt1 = findViewById(R.id.editText);
        edt2 = findViewById(R.id.lname);

        uploadBtn = findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImages();
                Log.i("OOAS","" +stringUrls.size());


            }
        });

    }

     private void checkPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(AddTopicActivity.this,
                    Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(AddTopicActivity.this, new String[]{Manifest.permission.READ_MEDIA_IMAGES},2);
            }else {
                pickImageFromGallery();
            }

        }
        else {
            pickImageFromGallery();
        }
     }

     private void pickImageFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
         if(requestCode ==1 && resultCode == RESULT_OK && data != null && data.getClipData() != null){
             int count = data.getClipData().getItemCount();
             for(int i=0;i<count;i++)
             {
                 Uri imageURIS = data.getClipData().getItemAt(i).getUri();
                 chooseImageList.add(imageURIS);
             }
         }
     }

     private void uploadImages() {

         Log.v("DownloadURL",""+chooseImageList.size());
        for(int i=0;i<chooseImageList.size();i++)
        {
            Uri IndividualImage = chooseImageList.get(i);

            if(IndividualImage != null) {
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), IndividualImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                    byte[] imageByte = stream.toByteArray();


                reference = storage.getReference("images");
                String pathString = "image"+i+Timestamp.now().getSeconds();
                StorageReference imageName = reference.child(pathString);
                imageName.putBytes(imageByte)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.v("DownloadURL", uri.toString());
                                        stringUl.put(imageName.getPath(),uri.toString());
                                        Log.v("DownloadURL",""+stringUrl.size());
                                        if (stringUl.size() == chooseImageList.size()) {
                                            // All images have been uploaded and download URLs retrieved
                                            // You can proceed with further operations here
                                            storeLinks(stringUl);
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("DownloadURL", "Failed to retrieve download URL", e);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("Upload", "Failed to upload file", e);
                            }
                        });
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

     }

     private void storeLinks(Map<String,String> stringUrl) {
         TreeMap<String, String> sortedMap = new TreeMap<>(stringUrl);

         for (Map.Entry<String, String> entry : sortedMap.entrySet()) {
             stringUrls.add(new Image(entry.getValue()));
//             System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
         }
         Collections.reverse(stringUrls);

         myViewModel.saveInDb(groupName,edt1.getText().toString(),edt2.getText().toString(),stringUrls);

     }



 }