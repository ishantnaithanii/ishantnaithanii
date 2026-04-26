package com.example.EDUAppGPSrinagar.Faculty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.EDUAppGPSrinagar.AdminActivity;
import com.example.EDUAppGPSrinagar.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add_Teacher extends AppCompatActivity {
    private ImageView addTeacherImage;
    private EditText addTeacherEmail, addTeacherName, addTeacherPost;
    private Spinner addTeacherCategory;
    private Button addTeacherBtn;
    private Toolbar toolbar;
    public Uri imageUri = null;
    private String value;
    private ProgressDialog pd;
    private String[] category = {"Select Department", "Computer Science", "Information Technology"};
    private String email;
    private String post;
    private String name;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference("Teacher");
    private DatabaseReference dbRef, databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher");

        setContentView(R.layout.activity_add_teacher);
        addTeacherImage = findViewById(R.id.addTeacherImage);
        addTeacherName = findViewById(R.id.addTeacherName);
        addTeacherEmail = findViewById(R.id.addTeacherEmail);
        addTeacherPost = findViewById(R.id.addTeacherPost);
        addTeacherCategory = findViewById(R.id.addTeacherCategory);
        addTeacherBtn = findViewById(R.id.addTeacherBtn);
        pd = new ProgressDialog(this);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageUri = data.getData();
                            addTeacherImage.setImageURI(imageUri);
                        } else {
                            Toast.makeText(Add_Teacher.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        addTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        //toolbar
        toolbar=findViewById(R.id.addFacultyToolBar);
        TextView textView=toolbar.findViewById((R.id.addFacultyToolbartv));
        ImageButton imageButton=toolbar.findViewById(R.id.addFacultyToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        //spinner
        addTeacherCategory = findViewById(R.id.addTeacherCategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add_Teacher.this, R.layout.item_file, category);
        adapter.setDropDownViewResource(R.layout.item_file);
        addTeacherCategory.setAdapter(adapter);

        addTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value = parent.getItemAtPosition(position).toString();
                Toast.makeText(Add_Teacher.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });

    }

    private void checkValidation() {
        name = addTeacherName.getText().toString();
        email = addTeacherEmail.getText().toString();
        post = addTeacherPost.getText().toString();
        if (name.isEmpty()) {
            addTeacherName.setError("Empty");
            addTeacherName.requestFocus();
        } else if (email.isEmpty()) {
            addTeacherEmail.setError("Empty");
            addTeacherEmail.requestFocus();
        } else if (post.isEmpty()) {
            addTeacherPost.setError("Empty");
            addTeacherPost.requestFocus();
        } else if (category.equals("Select Category")) {
            Toast.makeText(this, "Please provide teacher category", Toast.LENGTH_SHORT).show();
        } else if (imageUri != null) {
            uploadToFirebase(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri) {
        dbRef = databaseReference.child(value);
        pd.setTitle("Uploading...");
        pd.show();

        final String key = dbRef.push().getKey();

        final StorageReference imageReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));

        imageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        TeacherData teacherData = new TeacherData(name, email, post, uri.toString(), key);
                        dbRef.child(key).setValue(teacherData);
                        Toast.makeText(Add_Teacher.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Add_Teacher.this, AdminActivity.class));
                        finish();
                        pd.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Add_Teacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}