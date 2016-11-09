package com.pathfinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by connor on 11/8/16.
 */

public class DLoadImageTask extends AsyncTask<List<Building>, Void, Void> {

    private Context context;
    private Listener listener;
    private static final String TAG = "DLoadImageTask";
    public DLoadImageTask(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }
    @Override
    protected Void doInBackground(List<Building>... buildingLists) {
        List<Building> buildings = buildingLists[0];
        File cache = context.getCacheDir();
        for (int i = 0; i < buildings.size(); i++) {
            try {
                String url = buildings.get(i).getUrl();
                int index = url.lastIndexOf('/', url.lastIndexOf('/') - 1);
                url = url.substring(0, index) + "/h_100" + url.substring(index, url.length());
                Bitmap bmp  = BitmapFactory.decodeStream((new URL(url)).openConnection().getInputStream());
                String fileName = buildings.get(i).getId() + ".jpg";
                File file = new File(cache, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
                Log.d(TAG, file.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //if (result != null)
         //   imageView.setImageBitmap(result);
        Log.d(TAG, "Downloaded all images");
        listener.onIconsDownloaded();
    }
    public interface Listener {
        void onIconsDownloaded();
    }
}
