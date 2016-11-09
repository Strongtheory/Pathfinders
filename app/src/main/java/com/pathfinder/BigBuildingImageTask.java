package com.pathfinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by connor on 11/8/16.
 */

public class BigBuildingImageTask extends AsyncTask<Building, Void, Void> {

    Context context;
    Listener listener;
    public BigBuildingImageTask(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected Void doInBackground(Building... buildings) {
        try {
            File cache = context.getCacheDir();
            Building building = buildings[0];
            String url = building.getUrl();
            int index = url.lastIndexOf('/', url.lastIndexOf('/') - 1);
            url = url.substring(0, index) + "/h_400" + url.substring(index, url.length());
            Bitmap bmp  = BitmapFactory.decodeStream((new URL(url)).openConnection().getInputStream());
            String fileName = building.getId() + "-big.jpg";
            File file = new File(cache, fileName);
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception ex) {

        }

        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        if (listener != null)
            listener.onImageDownloaded();
    }

    public interface Listener {
        void onImageDownloaded();
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
