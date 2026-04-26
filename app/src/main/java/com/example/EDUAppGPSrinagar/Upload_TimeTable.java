package com.example.EDUAppGPSrinagar;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.EDUAppGPSrinagar.Timetable.TimetableData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Upload_TimeTable extends AppCompatActivity {

    private CardView cardView;
    private Button button;
    private Uri imageUri;
    private Toolbar toolbar;
    private ProgressDialog pd;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Timetable");
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference("Timetable");
    private ImageView imageView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_timetable);
        cardView=findViewById(R.id.timeTableImage);
        button=findViewById(R.id.uploadTimetableBtn);
        imageView=findViewById(R.id.timetableImageView);
        pd = new ProgressDialog(this);

        toolbar = findViewById(R.id.timetableToolBar);
        TextView textView = toolbar.findViewById((R.id.timetableToolbartv));
        ImageButton imageButton = toolbar.findViewById(R.id.timetableToolbarib);
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
                            imageView.setImageURI(imageUri);
                        }else {
                            Toast.makeText(Upload_TimeTable.this, "No Timetable Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker=new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                }else {
                    pd.dismiss();
                    Toast.makeText(Upload_TimeTable.this, "Please select Timetable", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadToFirebase(Uri imageUri) {
        pd.setTitle("Uploading...");
        pd.show();
        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        TimetableData data=new TimetableData(uri.toString(),"all");
                        databaseReference.child("all").setValue(data);
                        Toast.makeText(Upload_TimeTable.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Upload_TimeTable.this,AdminActivity.class));
                        finish();
                        pd.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_TimeTable.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}