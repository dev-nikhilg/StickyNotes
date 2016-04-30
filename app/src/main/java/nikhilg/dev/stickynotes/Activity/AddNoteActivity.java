package nikhilg.dev.stickynotes.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import nikhilg.dev.stickynotes.R;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishThisActivity();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finishThisActivity();
        return true;
    }

    private void finishThisActivity() {
        finish();
        overridePendingTransition(R.animator.pull_in_left, R.animator.push_out_right);
    }
}
