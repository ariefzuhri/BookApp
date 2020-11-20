package com.ariefzuhri.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvAuthor = findViewById(R.id.tv_author);
        TextView tvDescription = findViewById(R.id.tv_description);
        ImageView imgCover = findViewById(R.id.img_cover);

        Intent intent = getIntent();
        if (intent.hasExtra("extra_book")){
            Book book = intent.getParcelableExtra("extra_book");
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvDescription.setText(book.getDescription());
            new LoadImage(imgCover).execute(book.getImage());
        }
    }

    private static class LoadImage extends AsyncTask<String, Void, Bitmap> {
        @SuppressLint("StaticFieldLeak")
        private final ImageView imageView;

        public LoadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream(
                        url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}