package com.example.EDUAppGPSrinagar;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.EDUAppGPSrinagar.Notice.NoticeData;
import com.example.EDUAppGPSrinagar.Notice.Student_Notice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadNotice extends AppCompatActivity {
    private CardView addImage;
    private ImageView noticeImageView;
    private EditText noticeTitle,noticeTeacher;
    private Button uploadNoticeBtn;
    private Uri imageUri;
    private Toolbar toolbar;
    private ProgressDialog pd;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Notice");
    private StorageReference storageReference=FirebaseStorage.getInstance().getReference("Notice");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        pd = new ProgressDialog(this);

        addImage = findViewById(R.id.image);
        noticeImageView = findViewById(R.id.noticeImageView);
        noticeTitle = findViewById(R.id.noticeTitle);
        noticeTeacher = findViewById(R.id.noticeTeacher);
        uploadNoticeBtn = findViewById(R.id.uploadNoticeBtn);

        //toolbar
        toolbar = findViewById(R.id.noticeToolbar);
        TextView textView = toolbar.findViewById((R.id.noticeToolbartv));
        ImageButton imageButton = toolbar.findViewById(R.id.noticeToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data =result.getData();
                            imageUri=data.getData();
                            noticeImageView.setImageURI(imageUri);
                        }else {
                            Toast.makeText(UploadNotice.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker=new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        uploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                }else {
                    pd.dismiss();
                    Toast.makeText(UploadNotice.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase(Uri uri) {
        pd.setTitle("Uploading...");
        pd.show();
        String title=noticeTitle.getText().toString();
        String teacher=noticeTeacher.getText().toString();

        final String key=databaseReference.push().getKey();

        Calendar calForDate=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
        String date=currentDate.format(calForDate.getTime());

        Calendar calForTime=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
        String time=currentTime.format(calForTime.getTime());

        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        NoticeData noticeData=new NoticeData(title,teacher,uri.toString(),date,time,key);
                        databaseReference.child(key).setValue(noticeData);
                        NotificationHelper.createChannel(UploadNotice.this);
                        Intent intent=new Intent(UploadNotice.this, Student_Notice.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        NotificationHelper.sendNotification(UploadNotice.this,"New Notice",title,intent);
                        Toast.makeText(UploadNotice.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadNotice.this,AdminActivity.class));
                        finish();
                        pd.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}


//        addImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openGallery();
//            };
//        });
//        uploadNoticeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(noticeTitle.getText().toString().isEmpty()) {
//                    noticeTitle.setError("Empty");
//                    noticeTitle.requestFocus();
//                }else {
//                    uploadData();
//                }
//            }
//        });
//    }
//
//    private void uploadData() {
//        reference=reference.child("Notice");
//        final String uniqueKey=reference.push().getKey();
//
//        String title=noticeTitle.getText().toString();
//
//        Calendar calForDate=Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
//        String date=currentDate.format(calForDate.getTime());
//
//        Calendar calForTime=Calendar.getInstance();
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime=new SimpleDateFormat("hh:mm a");
//        String time=currentTime.format(calForTime.getTime());
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
//        byte[] finalImg = baos.toByteArray();
//        final StorageReference filePath;
//        filePath=storageReference.child("Notice").child(finalImg +"jpg");
//        final UploadTask uploadTask=filePath.putBytes(finalImg);
//        uploadTask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()){
//                    pd.dismiss();
//                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    downloadUrl= String.valueOf(uri);
//                                }
//                            });
//
//                        }
//                    });
//                }else {
//                    pd.dismiss();
//                    Toast.makeText(UploadNotice.this,"Something went wrong.",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        NoticeData noticeData=new NoticeData(title,downloadUrl,date,time,uniqueKey);
//
//        reference.child(uniqueKey).setValue(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                pd.dismiss();
//                Toast.makeText(UploadNotice.this,"Notice Uploaded.",Toast.LENGTH_SHORT).show();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
//                Toast.makeText(UploadNotice.this,"Something went wrong.",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void uploadImage() {
//        pd.setMessage("Uploading...");
//        pd.show();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
//        byte[] finalImg = baos.toByteArray();
//        final StorageReference filePath;
//        filePath=storageReference.child("Notice").child(finalImg +"jpg");
//        final UploadTask uploadTask=filePath.putBytes(finalImg);
//        uploadTask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()){
//                    pd.dismiss();
//                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    downloadUrl= String.valueOf(uri);
//                                }
//                            });
//
//                        }
//                    });
//                }else {
//                    pd.dismiss();
//                    Toast.makeText(UploadNotice.this,"Something went wrong.",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void openGallery() {
//        Intent pickImage= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(pickImage,REQ);
//    }
//
//    @Override()
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==REQ && resultCode==RESULT_OK){
//            Uri uri=data.getData();
//            try {
//                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//            noticeImageView.setImageBitmap(bitmap);
//        }else {
//            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//}

