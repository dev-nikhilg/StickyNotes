package nikhilg.dev.stickynotes.Classes;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import nikhilg.dev.stickynotes.Activity.MainActivity;
import nikhilg.dev.stickynotes.Helper.Utils;
import nikhilg.dev.stickynotes.R;

/**
 * Created by nik on 2/5/16.
 */
public class NoteHeadService5 extends Service implements StartActivityFromNoteHead{

    private final static int FOREGROUND_ID = 999;

    private HeadLayer mHeadLayer;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;
    private boolean clicked = false;
    WindowManager.LayoutParams params;
    private NotesObject note;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("nikhilservice", "in noteheadservice5 in onstartcommand");
        if (intent != null && intent.getStringExtra("note_object") != null) {
            note = new Gson().fromJson(intent.getStringExtra("note_object"), NotesObject.class);
            initHeadLayer(note);

            PendingIntent pendingIntent = createPendingIntent();
            Notification notification = createNotification(pendingIntent);
            startForeground(FOREGROUND_ID, notification);
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        destroyHeadLayer();
        mWindowManager.removeView(mFrameLayout);
        stopForeground(true);
    }

    private void initHeadLayer(NotesObject note) {
        mHeadLayer = new HeadLayer(this, this, note);
    }

    private void destroyHeadLayer() {
        mHeadLayer.destroy();
        mHeadLayer = null;
    }

    private PendingIntent createPendingIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

    private Notification createNotification(PendingIntent intent) {
        return new Notification.Builder(this)
                .setContentTitle("New note")
                .setContentText("You have added a new note")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(intent)
                .build();
    }

    @Override
    public void startMainActivity() {
        Utils.getInstance(this).addBoolValue("show_one_note", true);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("note_id", "" + note.getId());
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
