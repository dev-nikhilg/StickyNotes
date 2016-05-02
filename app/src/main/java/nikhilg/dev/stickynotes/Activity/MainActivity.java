package nikhilg.dev.stickynotes.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import nikhilg.dev.stickynotes.Classes.NotesObject;
import nikhilg.dev.stickynotes.Classes.StopNoteHeadService;
import nikhilg.dev.stickynotes.Fragments.AllNotesFragment;
import nikhilg.dev.stickynotes.Fragments.OneNoteFragment;
import nikhilg.dev.stickynotes.Helper.NotesDb;
import nikhilg.dev.stickynotes.Helper.Utils;
import nikhilg.dev.stickynotes.R;

public class MainActivity extends AppCompatActivity {

    private Intent intent = null;

    private String active_fragment;

    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        active_fragment = null;
        fragmentContainer = (FrameLayout) findViewById(R.id.fragmentContainer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setAllNotesFragment() {
        active_fragment = "all_note";
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("FRAG");
        if (fragment != null) {
            fm.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        fm.beginTransaction()
                .add(R.id.fragmentContainer, new AllNotesFragment(), "FRAG")
                .commit();
    }

    public void setOneNoteFragment(String id) {
        active_fragment = "one_note";
        // Add note id to bundle
        Bundle bundle = new Bundle();
        bundle.putString("note_id", id);
        // Add bundle to fragment instance
        OneNoteFragment frag = new OneNoteFragment();
        frag.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentByTag("FRAG");
        if (fragment != null) {
            fm.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
        fm.beginTransaction()
                .add(R.id.fragmentContainer, frag, "FRAG")
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = getIntent();
        if (intent != null && Utils.getInstance(this).getBoolValue("show_one_note")) {
            Utils.getInstance(this).addBoolValue("show_one_note", false);
            String str_id = intent.getStringExtra("note_id");
            setOneNoteFragment(str_id);
        } else {
            setAllNotesFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    public void showPopUp(String heading_txt, String description_txt, String btn_positive_txt, String btn_negative_txt, final NotesObject note_obj) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);

        TextView heading = (TextView) dialog.findViewById(R.id.messagesentheading);
        heading.setText(heading_txt);
        heading.setTypeface(null, Typeface.BOLD);

        TextView descr = (TextView) dialog.findViewById(R.id.messagesentdescription);
        descr.setText(description_txt);

        Button pos = (Button) dialog.findViewById(R.id.btn_positive);
        TextView neg = (TextView) dialog.findViewById(R.id.btn_negative);

        pos.setText(btn_positive_txt);
        pos.setTypeface(null, Typeface.BOLD);
        neg.setText(btn_negative_txt);
        neg.setTypeface(null, Typeface.BOLD);

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (note_obj.getService_num() != -1) {
                    new StopNoteHeadService( MainActivity.this, note_obj);
                }
                new NotesDb().DeleteNote(note_obj.getId());
                int num = Utils.getInstance(MainActivity.this).getIntValue("active_notes");
                num--;
                Utils.getInstance(MainActivity.this).addIntValue("active_notes", num);
                setAllNotesFragment();
            }
        });

        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showNoteHeadPopUp() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_notehead_layout);

        TextView heading = (TextView) dialog.findViewById(R.id.messagesentheading);
        heading.setText("Change NoteHead Visibility");
        heading.setTypeface(null, Typeface.BOLD);

        TextView descr = (TextView) dialog.findViewById(R.id.messagesentdescription);
        descr.setText("You can change the visibility of the notehead again by selecting/deselecting the checkbox.");

        Button pos = (Button) dialog.findViewById(R.id.btn_positive);

        pos.setText("OK");
        pos.setTypeface(null, Typeface.BOLD);

        final CheckBox cb = (CheckBox) dialog.findViewById(R.id.cb);

        pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (cb.isChecked()) {
                    Utils.getInstance(MainActivity.this).addBoolValue("dont_show_popup", true);
                }
            }
        });
        dialog.show();
    }

    public void startService(NotesObject note) {

    }

    public void stopService(NotesObject note) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (active_fragment.equalsIgnoreCase("one_note") && Utils.getInstance(this).getIntValue("active_notes") > 1) {
            Log.d("nikhilservice", "num of active notes is : " + Utils.getInstance(this).getIntValue("active_notes"));
            setAllNotesFragment();
        } else {
            super.onBackPressed();
        }
    }
}
