package nikhilg.dev.stickynotes.Classes;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import nikhilg.dev.stickynotes.R;

/**
 * Created by nik on 28/4/16.
 */
public class HeadLayer extends View {
    private Context mContext;
    private FrameLayout mFrameLayout;
    private WindowManager mWindowManager;
    private NotesObject note;
    private StartActivityFromNoteHead startActivityFromNoteHead;

    private boolean clicked = false;

    public HeadLayer(Context context, StartActivityFromNoteHead startActivityFromNoteHead, NotesObject note) {
        super(context);

        mContext = context;
        mFrameLayout = new FrameLayout(mContext);
        this.note = note;
        this.startActivityFromNoteHead = startActivityFromNoteHead;
        addToWindowManager(note.getSmallTitle());
    }

    private void addToWindowManager(String title) {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.addView(mFrameLayout, params);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Here is the place where you can inject whatever layout you want.
        layoutInflater.inflate(R.layout.note_head, mFrameLayout);

        // Support dragging the image view
        //final ImageView imageView = (ImageView) mFrameLayout.findViewById(R.id.imageView);
        final TextView txtView = (TextView) mFrameLayout.findViewById(R.id.note_head_txt);
        txtView.setText(title);

        txtView.setOnTouchListener(new OnTouchListener() {
            private int initX, initY;
            private int initTouchX, initTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initX = params.x;
                        initY = params.y;
                        initTouchX = x;
                        initTouchY = y;
                        clicked = true;
                        return true;

                    case MotionEvent.ACTION_UP:
                        if (clicked) {
                            Log.d("nikhil", "image was tapped");
                            startActivityFromNoteHead.startMainActivity();
                        } else {
                            Log.d("nikhil", "image was dragged");
                        }
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        clicked = false;
                        params.x = initX + (x - initTouchX);
                        params.y = initY + (y - initTouchY);

                        // Invalidate layout
                        mWindowManager.updateViewLayout(mFrameLayout, params);
                        return true;
                }
                return false;
            }
        });

    }

    /**
     * Removes the view from window manager.
     */
    public void destroy() {
        mWindowManager.removeView(mFrameLayout);
    }
}
