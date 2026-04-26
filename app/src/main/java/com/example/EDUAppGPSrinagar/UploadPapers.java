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

import com.example.EDUAppGPSrinagar.Assignment.Student_Assignment;
import com.example.EDUAppGPSrinagar.Notice.NoticeData;
import com.example.EDUAppGPSrinagar.Paper.StudentPapersData;
import com.example.EDUAppGPSrinagar.Paper.Student_Papers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UploadPapers extends AppCompatActivity {
    private CardView addPaper;
    private TextView pdfTextView;
    private Toolbar toolbar;

    private final int REQ = 1;
    private Uri pdfData;
    private EditText paperTitle,paperSubject;
    private Button uploadPaperBtn;
    private String pdfName,title,subject;
    private ProgressDialog pd;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Papers");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Papers");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_papers);


        pd = new ProgressDialog(this);
        pdfTextView=findViewById(R.id.pdfTextView);
        addPaper = findViewById(R.id.uploadPaper);
        paperTitle = findViewById(R.id.paperTitle);
        paperSubject=findViewById(R.id.paperSub);

        toolbar=findViewById(R.id.paperToolBar);
        uploadPaperBtn = findViewById(R.id.uploadPaperBtn);
        TextView textView=toolbar.findViewById((R.id.paperToolbartv));
        ImageButton imageButton=toolbar.findViewById(R.id.paperToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());


        addPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        uploadPaperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=paperTitle.getText().toString();
                subject=paperSubject.getText().toString();
                if (title.isEmpty()) {
                    paperTitle.setError("Empty");
                    paperTitle.requestFocus();
                }else if (subject.isEmpty()) {
                    paperSubject.setError("Empty");
                    paperSubject.requestFocus();
                } else if (pdfData==null) {
                    Toast.makeText(UploadPapers.this,"please select paper",Toast.LENGTH_SHORT).show();

                }
                else {
                    uploadPdf(pdfData);
                }
            }

        });
    }
    private void uploadPdf(Uri uri){
    pd.setTitle("Uploading...");
        pd.show();
    String title=paperTitle.getText().toString();
    String subject=paperSubject.getText().toString();

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
                    StudentPapersData paperData=new StudentPapersData(title,subject,uri.toString(),date,time,key);
                    databaseReference.child(key).setValue(paperData);
                    NotificationHelper.createChannel(UploadPapers.this);
                    Intent intent=new Intent(UploadPapers.this, Student_Papers.class);
                    NotificationHelper.sendNotification(UploadPapers.this,"New Papers",subject,intent);
                    Toast.makeText(UploadPapers.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UploadPapers.this,AdminActivity.class));
                    finish();
                    pd.dismiss();
                }
            });
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            pd.dismiss();
            Toast.makeText(UploadPapers.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    });

}

//without url code
//    private void uploadPdf(Uri uri) {
//        pd.setTitle("Please wait");
//        pd.setMessage("Uploading paper");
//        pd.show();
//        StorageReference reference=storageReference.child("Papers/"+pdfName+"-"+System.currentTimeMillis()+"*");
//        reference.putFile(pdfData)
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
//                        Toast.makeText(UploadPapers.this,"Something went wrong",Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//        private void uploadData(String s) {
//        String uniqueKey=databaseReference.child("Paper").push().getKey();
//        HashMap data =new HashMap();
//        data.put("paperTitle",title);
//        data.put("pdfUrl",downloadUrl);
//
//        databaseReference.child("Paper").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                pd.dismiss();
//                Toast.makeText(UploadPapers.this,"Paper uploaded successfully",Toast.LENGTH_SHORT).show();
//                paperTitle.setText("");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
//                Toast.makeText(UploadPapers.this,"failed to upload Paper",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Paper"),REQ);
    }

    @SuppressLint("Range")
    @Override()
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            pdfData =data.getData();
            if(pdfData.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor=UploadPapers.this.getContentResolver().query(pdfData,null,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()){
                        pdfName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (pdfData.toString().startsWith("file://")) {
                pdfName=new File(pdfData.toString()).getName();
            }
            pdfTextView.setText(pdfName);
        }
    }
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }

}