package nikhilg.dev.stickynotes.Classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by nik on 2/5/16.
 */
public class NoteHeadServiceFirstNote extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
