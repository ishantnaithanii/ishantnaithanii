package com.example.EDUAppGPSrinagar.Paper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.EDUAppGPSrinagar.Notes.NotesViewerActivity;
import com.example.EDUAppGPSrinagar.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaperViewerActivity extends AppCompatActivity {

    private String url;
    private PDFView pdfView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper_viewer);

        url = getIntent().getStringExtra("pdfUrl");
        pdfView = findViewById(R.id.papersPdfView);

        new PdfDownload().execute(url);
    }
        private class PdfDownload extends AsyncTask<String,Void, InputStream> {

            @Override
            protected InputStream doInBackground(String... strings) {
                InputStream inputStream=null;
                try {
                    URL url=new URL(strings[0]);
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();

                    if(urlConnection.getResponseCode()==200){
                        inputStream=new BufferedInputStream(urlConnection.getInputStream());
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                return inputStream;
            }

            @Override
            protected void onPostExecute(InputStream inputStream) {
                pdfView.fromStream(inputStream).load();
            }
        }
    }