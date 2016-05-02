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

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nikhilg.dev.stickynotes.Classes.GetServicenumber;
import nikhilg.dev.stickynotes.Classes.NotesObject;
import nikhilg.dev.stickynotes.Classes.StartNoteHeadService;
import nikhilg.dev.stickynotes.Classes.StopNoteHeadService;
import nikhilg.dev.stickynotes.Helper.NotesDb;
import nikhilg.dev.stickynotes.Helper.Utils;
import nikhilg.dev.stickynotes.R;

public class EditNote extends AppCompatActivity implements View.OnClickListener{

    private EditText short_title, title, body;
    private Button save, discard;
    private NotesObject note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        note = new Gson().fromJson(getIntent().getStringExtra("note_object"), NotesObject.class);
        init();
    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        short_title = (EditText) findViewById(R.id.etxt_short_title);
        short_title.setText(note.getSmallTitle());
        title = (EditText) findViewById(R.id.etxt_title);
        title.setText(note.getTitle());
        body = (EditText) findViewById(R.id.etxt_body);
        body.setText(note.getNoteBody());
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
                showPopUp("Discard changes?", "Are you sure you want to discard all the changes? All the changes you have made to the note will be lost.",
                        "CANCEL", "DISCARD");
        }
    }

    private void saveNote() {
        String str = body.getText().toString();
        str = str.trim();
        if (str != null && str.length() > 0) {
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
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = c.getTime();
            String formattedDate = df.format(date);
            note.setCreatedOn(date.toString());
            note .setLastModifiedOn(formattedDate);

            if (note.getService_num() != -1) {
                new StopNoteHeadService(this, note);
            }

            // Get service number for notehead of this note
            note.setService_num(new GetServicenumber().getNumber(this));
            // we have to start service if service_num is not equal to -1
            if (note.getService_num() == -1) {
                // not starting service (already 5 services are active)
                // so set show icon to 0
                note.setShowIcon(0);
            } else {
                // can start service. set show icon to 1
                note.setShowIcon(1);
                new StartNoteHeadService(this, note);
            }

            NotesDb db = new NotesDb();
            db.UpdateNote(note);
            Toast.makeText(this, "Your note has been successfully updated.", Toast.LENGTH_SHORT).show();
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
