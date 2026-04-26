package com.example.EDUAppGPSrinagar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import java.io.File;

import com.example.EDUAppGPSrinagar.Assignment.StudentAssignmentData;
import com.example.EDUAppGPSrinagar.Assignment.Student_Assignment;
import com.example.EDUAppGPSrinagar.Notice.NoticeData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadAssignments extends AppCompatActivity {
    private CardView addAsg;
    private TextView asgTextView;
    private Toolbar asgToolbar;

    private final int REQ = 1;
    private Uri asgData;
    private EditText asgTitle,asgSub;
    private Button uploadAsgBtn;
    private String asgName,title,subject;
    private ProgressDialog pd;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Assignments");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Assignments");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_assignments);

        pd = new ProgressDialog(this);
        asgTextView=findViewById(R.id.asgTextView);
        addAsg = findViewById(R.id.uploadAsg);
        asgSub=findViewById(R.id.asgsub);
        asgTitle = findViewById(R.id.asgTitle);
        uploadAsgBtn = findViewById(R.id.uploadAsgBtn);

        //toolbar
        asgToolbar=findViewById(R.id.asgToolBar);
        TextView textView=asgToolbar.findViewById((R.id.asgToolbartv));
        ImageButton imageButton=asgToolbar.findViewById(R.id.asgToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());



        addAsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadAsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=asgTitle.getText().toString();
                subject=asgSub.getText().toString();
                if (title.isEmpty()) {
                    asgTitle.setError("Empty");
                    asgTitle.requestFocus();
                }else if (subject.isEmpty()) {
                    asgSub.setError("Empty");
                    asgSub.requestFocus();
                } else if (asgData==null) {
                    Toast.makeText(UploadAssignments.this,"please select assignment",Toast.LENGTH_SHORT).show();

                }
                else {
                    uploadAsg(asgData);
                }
            }

        });
    }

    private void uploadAsg(Uri uri) {
        pd.setTitle("Uploading...");
        pd.show();
        String title=asgTitle.getText().toString();

        final String key=databaseReference.push().getKey();

        Calendar calForDate=Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
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
                        StudentAssignmentData assignmentData=new StudentAssignmentData(title,subject,uri.toString(),date,time,key);
                        databaseReference.child(key).setValue(assignmentData);
                        NotificationHelper.createChannel(UploadAssignments.this);
                        Intent intent=new Intent(UploadAssignments.this, Student_Assignment.class);
                        NotificationHelper.sendNotification(UploadAssignments.this,"New Assignment",subject,intent);
                        Toast.makeText(UploadAssignments.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadAssignments.this,AdminActivity.class));
                        finish();
                        pd.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadAssignments.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

//without url data
//    private void uploadAsg() {
//        pd.setTitle("Please wait");
//        pd.setMessage("Uploading Assignment");
//        pd.show();
//        StorageReference reference=storageReference.child("Assignments/"+asgName+"-"+System.currentTimeMillis()+"*");
//        reference.putFile(asgData)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        Task <Uri>uriTask=taskSnapshot.getStorage().getDownloadUrl();
//                        while (!uriTask.isComplete());
//                        Uri uri=uriTask.getResult();
//                        uploadData(String.valueOf(uri));
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        pd.dismiss();
//                        Toast.makeText(UploadAssignments.this,"Something went wrong",Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void uploadData(String s) {
//        String uniqueKey=databaseReference.child("Assignment").push().getKey();
//        HashMap data =new HashMap();
//        data.put("AssignmentTitle",title);
//        data.put("AssignmentUrl",downloadUrl);
//
//        databaseReference.child("Assignment").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                pd.dismiss();
//                Toast.makeText(UploadAssignments.this,"Assignment uploaded successfully",Toast.LENGTH_SHORT).show();
//                asgTitle.setText("");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
//                Toast.makeText(UploadAssignments.this,"failed to upload file",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select file"),REQ);
    }

    @SuppressLint("Range")
    @Override()
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            asgData =data.getData();
            if(asgData.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor=UploadAssignments.this.getContentResolver().query(asgData,null,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()){
                        asgName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (asgData.toString().startsWith("file://")) {
                asgName=new File(asgData.toString()).getName();
            }
            asgTextView.setText(asgName);
        }
    }
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}