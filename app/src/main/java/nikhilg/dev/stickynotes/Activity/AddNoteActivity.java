package nikhilg.dev.stickynotes.Activity;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nikhilg.dev.stickynotes.Classes.NotesObject;
import nikhilg.dev.stickynotes.Helper.NotesDb;
import nikhilg.dev.stickynotes.Helper.Utils;
import nikhilg.dev.stickynotes.R;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText short_title, title, body;
    private Button save, discard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        short_title = (EditText) findViewById(R.id.etxt_short_title);
        title = (EditText) findViewById(R.id.etxt_title);
        body = (EditText) findViewById(R.id.etxt_body);
        save = (Button) findViewById(R.id.btn_save);
        discard =(Button) findViewById(R.id.btn_discard);
        save.setOnClickListener(this);
        discard.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                saveNote();
                break;
            case R.id.btn_discard:
                showPopUp("Discard all changes?", "Are you sure you want to discard all the changes? All the changes you have made will be lost permanently.",
                        "CANCEL", "DISCARD");
        }
    }

    private void saveNote() {
        String str = body.getText().toString();
        if (str != null && str.length() > 0) {
            NotesObject note = new NotesObject();
            if (short_title.getText().toString() != null && short_title.getText().toString().length() > 0) {
                note.setSmallTitle(short_title.getText().toString());
            } else {
                String s;
                if (str.length() < 5) {
                    s = str;
                } else {
                    s = str.substring(0, 4);
                }
                note.setSmallTitle(s);
            }
            if (title.getText().toString() != null && title.getText().toString().length() > 0) {
                note.setTitle(title.getText().toString());
            } else {
                String s;
                if (str.length() < 11) {
                    s = str;
                } else {
                    s = str.substring(0, 10);
                }
                note.setTitle(s);
            }
            note.setNoteBody(str);
            note.setShowIcon(1);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());
            note.setCreatedOn(formattedDate);
            note .setLastModifiedOn(formattedDate);

            if (Utils.getInstance(this).getBoolValue("experienced_user")) {
                int id = Utils.getInstance(this).getIntValue("all_notes_num");
                id++;
                note.setId(id);
                Utils.getInstance(this).addIntValue("all_notes_num", id);
                int num = Utils.getInstance(this).getIntValue("active_notes");
                num++;
                Utils.getInstance(this).addIntValue("active_notes", num);
            } else {
                note.setId(1);
                Utils.getInstance(this).addIntValue("all_notes_num", 1);
                Utils.getInstance(this).addIntValue("active_notes", 1);
                Utils.getInstance(this).addBoolValue("experienced_user", true);
                Utils.getInstance(this).addBoolValue("firstnote", true);
            }
            NotesDb db = new NotesDb();
            db.AddToDb(note);
            Toast.makeText(this, "Your note has been successfully saved.", Toast.LENGTH_SHORT).show();
            finishThisActivity();
        } else {
            Toast.makeText(this, "Please first make a note of something.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        showPopUp("Leave Activity?", "Are you sure you want to close this activity? Any changes you have made will be lost.",
                "STAY", "CLOSE");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        showPopUp("Leave Activity?", "Are you sure you want to close this activity? Any changes you have made will be lost.",
                "STAY", "CLOSE");
        return true;
    }

    private void showPopUp(String heading_txt, String description_txt, String btn_positive_txt, String btn_negative_txt) {
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
            }
        });

        neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finishThisActivity();
            }
        });
        dialog.show();
    }

    private void finishThisActivity() {
        finish();
        overridePendingTransition(R.animator.pull_in_left, R.animator.push_out_right);
    }
}
