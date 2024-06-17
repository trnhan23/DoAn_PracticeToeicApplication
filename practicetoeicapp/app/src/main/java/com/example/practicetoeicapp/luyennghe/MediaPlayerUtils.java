package com.example.practicetoeicapp.luyennghe;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;

import com.example.practicetoeicapp.singletonpattern.MessageObject;

public class MediaPlayerUtils {
    public static final String URL_MEDIA_SAMPLE  = "https://github.com/lehoangminhtai/PracticeToeicApp/raw/main/tuvung/LuyenNghe_Hey%20Mama%20-%20David%20Guetta%20feat_%20Nicki%20Mina.mp3";
    public static final String LOG_TAG= "MediaPlayerTutorial";
    public static void playURLMedia(Context context, MediaPlayer mediaPlayer, String videoURL)  {
        try {
            Log.i(LOG_TAG, "Media URL: "+ videoURL);

            Uri uri= Uri.parse( videoURL );
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.prepareAsync();

        } catch(Exception e) {
            MessageObject messageObject = MessageObject.getInstance();
            Log.e(LOG_TAG, "Error Play URL Media: "+ e.getMessage());
            messageObject.ShowDialogMessage(Gravity.CENTER,
                    context,
                    "Error Play URL Media: "+ e.getMessage(),
                    0);
            e.printStackTrace();
        }
    }
}
