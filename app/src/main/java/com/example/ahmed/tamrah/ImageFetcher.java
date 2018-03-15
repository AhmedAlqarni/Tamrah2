package com.example.ahmed.tamrah;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by Warsh on 3/15/2018.
 */

public class ImageFetcher extends AsyncTask<String, Void, Drawable> {
    public Drawable fetch(String url){
        return doInBackground(url);
    }

    @Override
    protected Drawable doInBackground(String... strings) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            return Drawable.createFromStream((InputStream) new URL(strings[0]).getContent(), "fetched");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
