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

import com.example.EDUAppGPSrinagar.Notes.StudentNotesData;
import com.example.EDUAppGPSrinagar.Notes.Student_Notes;
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

public class UploadNotes extends AppCompatActivity {
    private CardView addNotes;
    private TextView notesTextView;
    private Toolbar notesToolbar;
    private final int REQ = 1;
    private Uri notesUri;
    private EditText notesTitle,notesSubject;
    private Button uploadNotesBtn;
    private String notesName,title,subject;
    String downloadUrl = "";
    private ProgressDialog pd;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notes");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Notes");


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notes);


        pd = new ProgressDialog(this);
        notesTextView=findViewById(R.id.notesTextView);
        notesSubject=findViewById(R.id.notesSub);
        addNotes = findViewById(R.id.addNotes);
        notesTitle = findViewById(R.id.notesTitle);
        uploadNotesBtn = findViewById(R.id.uploadNotesBtn);

        notesToolbar =findViewById(R.id.notesToolBar);
        TextView textView=notesToolbar.findViewById((R.id.notesToolbartv));
        ImageButton imageButton=notesToolbar.findViewById(R.id.notesToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());


        addNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        uploadNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=notesTitle.getText().toString();
                subject=notesSubject.getText().toString();
                if (title.isEmpty()) {
                    notesTitle.setError("Empty");
                    notesTitle.requestFocus();
                } else if (subject.isEmpty()) {
                    notesSubject.setError("Empty");
                    notesSubject.requestFocus();
                } else if (notesUri==null) {
                    Toast.makeText(UploadNotes.this,"please select Notes",Toast.LENGTH_SHORT).show();
                }
                else {
                    uploadNotes(notesUri);
                }
            }

        });
    }

    private void uploadNotes(Uri uri) {
        pd.setTitle("Uploading...");
        pd.show();
        String title=notesTitle.getText().toString();
        String subject=notesSubject.getText().toString();

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
                        StudentNotesData notesData=new StudentNotesData(title,subject,uri.toString(),date,time,key);
                        databaseReference.child(key).setValue(notesData);
                        NotificationHelper.createChannel(UploadNotes.this);
                        Intent intent=new Intent(UploadNotes.this, Student_Notes.class);
                        NotificationHelper.sendNotification(UploadNotes.this,"New Notes",subject,intent);
                        Toast.makeText(UploadNotes.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UploadNotes.this,AdminActivity.class));
                        finish();
                        pd.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotes.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

// without url code
//    private void uploadNotes() {
//        pd.setTitle("Please wait");
//        pd.setMessage("Uploading Notes");
//        pd.show();
//        StorageReference reference=storageReference.child("Notes/"+notesName+"-"+System.currentTimeMillis()+"*");
//        reference.putFile(notesData)
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
//                        Toast.makeText(UploadNotes.this,"Something went wrong",Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//
//    private void uploadData(String s) {
//        String uniqueKey=databaseReference.push().getKey();
//        HashMap data =new HashMap();
//        data.put("NotesTitle",title);
//        data.put("NotesUrl",downloadUrl);
//
//        databaseReference.child("Notes").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                pd.dismiss();
//                Toast.makeText(UploadNotes.this,"Notes uploaded successfully",Toast.LENGTH_SHORT).show();
//                notesTitle.setText("");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                pd.dismiss();
//                Toast.makeText(UploadNotes.this,"failed to upload Notes",Toast.LENGTH_SHORT).show();
//            }
//        });
//     }

    private void openGallery() {
        Intent intent=new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Notes"),REQ);
    }

    @SuppressLint("Range")
    @Override()
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ && resultCode==RESULT_OK){
            notesUri =data.getData();
            if(notesUri.toString().startsWith("content://")){
                Cursor cursor=null;
                try {
                    cursor=UploadNotes.this.getContentResolver().query(notesUri,null,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()){
                        notesName=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (notesUri.toString().startsWith("file://")) {
                notesName=new File(notesUri.toString()).getName();
            }
            notesTextView.setText(notesName);
        }
    }
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}