package nikhilg.dev.stickynotes.Classes;

import android.app.Application;
import android.content.Context;

/**
 * Created by nik on 29/4/16.
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
    }
}
