package com.ariefzuhri.bookapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchBook extends AsyncTask<String, Void, String> {
    private ArrayList<Book> bookList;
    private BookAdapter adapter;
    @SuppressLint("StaticFieldLeak")
    private final RecyclerView recyclerView;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public FetchBook(Context context, ArrayList<Book> bookList,
                     BookAdapter adapter, RecyclerView recyclerView){
        this.context = context;
        this.bookList = bookList;
        this.adapter = adapter;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        String queryString = strings[0];

        HttpURLConnection urlConnection;
        BufferedReader reader;
        String bookJSONString = null;

        final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
        final String QUERY_PARAM = "q";
        Uri uri = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString).build();

        try {
            URL requestURL = new URL(uri.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) builder.append(line).append("\n");
            if (builder.length() == 0) return null;
            bookJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookJSONString;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        bookList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            String title, author, image, description;
            int i = 0;

            while (i<itemsArray.length()){
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try { // Mencegah error dari parameter yang null
                    title = volumeInfo.getString("title");

                    if (volumeInfo.has("authors")){
                        author = volumeInfo.getString("authors");
                    } else author = "";

                    if (volumeInfo.has("description")){
                        description = volumeInfo.getString("description");
                    } else description = "";

                    if (volumeInfo.has("imageLinks")){
                        image = volumeInfo.getJSONObject("imageLinks")
                                .getString("thumbnail");
                    } else image = "";

                    Book result = new Book(title, author, image, description);
                    bookList.add(result);
                } catch (Exception e){
                    e.printStackTrace();
                }

                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.adapter = new BookAdapter(context, bookList);
        this.recyclerView.setAdapter(this.adapter);
    }
}
