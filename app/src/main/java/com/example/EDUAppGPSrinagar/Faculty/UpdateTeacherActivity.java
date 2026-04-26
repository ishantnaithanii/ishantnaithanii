package com.example.EDUAppGPSrinagar.Faculty;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.EDUAppGPSrinagar.AdminActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.UpdateProfileActivity;
import com.example.EDUAppGPSrinagar.Login_and_Register.UploadProfilePicActivity;
import com.example.EDUAppGPSrinagar.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class UpdateTeacherActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView updateteacherimage;
    private EditText updateTeacherName,updateTeacherEmail,updateTeacherPost;
    private Button updateTeacherBtn,deleteTeacherBtn;
    private Uri uri;
    private String key,oldImageUrl;
    private String imageUrl="";
    private ProgressDialog pd;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    private String name,email,post,category;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        post = getIntent().getStringExtra("post");
        category = getIntent().getStringExtra("category");
        key=getIntent().getStringExtra("key");
        oldImageUrl=getIntent().getStringExtra("image");

        databaseReference = FirebaseDatabase.getInstance().getReference("Teacher").child(category).child(key);

        toolbar = findViewById(R.id.updateTeacherToolbar);
        TextView textView = toolbar.findViewById((R.id.updateTeacherToolbartv));
        ImageButton imageButton = toolbar.findViewById(R.id.updateTeacherToolbarib);
        textView.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(v -> onBackPressed());

        updateteacherimage = findViewById(R.id.updateteacherimage);
        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherBtn = findViewById(R.id.updateTeacherBtn);
        deleteTeacherBtn = findViewById(R.id.deleteTeacherBtn);
        pd=new ProgressDialog(this);

        try {
            Picasso.get().load(getIntent().getStringExtra("image")).into(updateteacherimage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateTeacherEmail.setText(email);
        updateTeacherName.setText(name);
        updateTeacherPost.setText(post);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            updateteacherimage.setImageURI(uri);
                        } else {
                            Toast.makeText(UpdateTeacherActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        updateteacherimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.isEmpty()){
                    updateTeacherName.setError("Empty");
                    updateTeacherName.requestFocus();
                } else if (post.isEmpty()) {
                    updateTeacherPost.setError("Empty");
                    updateTeacherPost.requestFocus();
                } else if (email.isEmpty()) {
                    updateTeacherEmail.setError("Empty");
                    updateTeacherEmail.requestFocus();
                } else if (uri==null) {
                    updateData();
                }else if(uri !=null) {
                   saveData();
                }else {
                    Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UpdateTeacherActivity.this);
                builder.setMessage("Are you sure want to delete this notice ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData();
                    }
                });
                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }
                );
                AlertDialog dialog=null;
                try {
                    dialog=builder.create();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(dialog !=null) {
                    dialog.show();
                }

           }
        });
    }

    private void saveData() {
        pd.setTitle("Uploading...");
        pd.show();

        name=updateTeacherName.getText().toString().trim();
        email=updateTeacherEmail.getText().toString().trim();
        post=updateTeacherPost.getText().toString().trim();
        storageReference=FirebaseStorage.getInstance().getReference().child("Teacher").child(uri.getLastPathSegment());

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageUrl=urlImage.toString();
                TeacherData data=new TeacherData(name,email,post,imageUrl,key);
                databaseReference.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                            reference.delete();
                            Toast.makeText(UpdateTeacherActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateTeacherActivity.this, UpdateFaculty.class));
                            finish();
                            pd.dismiss();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateData() {
        pd.setTitle("Uploading...");
        pd.show();

        name=updateTeacherName.getText().toString().trim();
        email=updateTeacherEmail.getText().toString().trim();
        post=updateTeacherPost.getText().toString().trim();
        HashMap hp=new HashMap();
        hp.put("name",name);
        hp.put("post",post);
        hp.put("email",email);

        databaseReference.updateChildren(hp).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(UpdateTeacherActivity.this, "Teacher Updated SuccessFully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UpdateTeacherActivity.this,UpdateFaculty.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deleteData() {
        pd.setTitle("Uploading...");
        pd.show();

        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
             @Override
             public void onComplete(@NonNull Task<Void> task) {
                 if(task.isComplete()){
                     if(imageUrl.isEmpty()){
                         StorageReference storage=FirebaseStorage.getInstance().getReferenceFromUrl(oldImageUrl);
                         storage.delete();
                     }else {
                         StorageReference storage2=FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                         storage2.delete();
                     }
                     Toast.makeText(UpdateTeacherActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                     finish();
                     pd.dismiss();
                 }
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 pd.dismiss();
                 Toast.makeText(UpdateTeacherActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
             }
         });
    }

}