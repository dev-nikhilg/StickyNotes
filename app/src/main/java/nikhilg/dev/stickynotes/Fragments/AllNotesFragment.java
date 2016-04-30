package nikhilg.dev.stickynotes.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import nikhilg.dev.stickynotes.Activity.AddNoteActivity;
import nikhilg.dev.stickynotes.Classes.NotesObject;
import nikhilg.dev.stickynotes.Helper.NotesDb;
import nikhilg.dev.stickynotes.Helper.Utils;
import nikhilg.dev.stickynotes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllNotesFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton add_note_fab;
    private TextView no_notes_txt;
    private RecyclerView notes_listview;

    public AllNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_notes, container, false);
        init(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        List<NotesObject> all_db_notes = new NotesDb().FetchAllNotes();
        if (all_db_notes.size() > 0) {
            no_notes_txt.setVisibility(View.GONE);
        } else {
            notes_listview.setVisibility(View.GONE);
        }
    }

    private void init(View v) {
        add_note_fab = (FloatingActionButton) v.findViewById(R.id.add_note_fab);
        no_notes_txt = (TextView) v.findViewById(R.id.no_notes_txt);
        notes_listview = (RecyclerView) v.findViewById(R.id.notes_list);
        add_note_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
            }
        });
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.add_note_fab:
                //showSnackbar(v, "You don't have any saved notes. Make a new note now.");
                Log.d("nikhil", "fab button clicked");
                showSnackbar(v, "You have clicked to add a new note.");
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                break;
        }*/
    }

    private void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
