package com.gastell_gehr_haberl.studinazor;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by lucas on 07.09.2017.
 */

public class NewstickerDownloadTask extends AsyncTask<String, Void, Void> {

    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";
    private static final String URLTOIMAGE = "urlToImage";
    private static final String PUBLISHEDAT = "publishedAt";
    
    private ArrayList<NewstickerItem> items;
    private DownloadListener listener;
    
    public NewstickerDownloadTask(DownloadListener listener, ArrayList<NewstickerItem> items) {
        this.listener = listener;
        this.items = items;
    }

    @Override
    protected Void doInBackground(String... params) {

        JSONObject jsonObject;
        new JSONObject();

        try {
            jsonObject = getJSONObjectFromURL(params[0]);
            processJson(jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        listener.onDownloadFinished();
    }

    /**
     * arbeitet das JSON-Objekt ab
     * @param jsonObject
     */
    private void processJson(JSONObject jsonObject) {
        try {

            JSONArray news = jsonObject.getJSONArray("articles");
            for (int i=0; i<news.length(); i++) {
                JSONObject article = news.getJSONObject(i);
                String author = article.getString(AUTHOR);
                String title = article.getString(TITLE);
                String description = article.getString(DESCRIPTION);
                String url = article.getString(URL);
                String urlToImage = article.getString(URLTOIMAGE);
                String publishedAt = article.getString(PUBLISHEDAT);
                NewstickerItem item = new NewstickerItem(author, title, description, url, urlToImage, publishedAt);

                items.add(item);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Holt das JSON-Objekt aus der URL
     * @param urlString
     * @return gibt das JSON-Objekt zurück
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject getJSONObjectFromURL(String urlString) throws  IOException, JSONException {
        URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        bufferedReader.close();
        urlConnection.disconnect();

        return new JSONObject(stringBuilder.toString());
    }

}
