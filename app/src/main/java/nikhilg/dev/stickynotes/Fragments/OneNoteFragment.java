package nikhilg.dev.stickynotes.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import nikhilg.dev.stickynotes.Activity.AddNoteActivity;
import nikhilg.dev.stickynotes.Activity.EditNote;
import nikhilg.dev.stickynotes.Activity.MainActivity;
import nikhilg.dev.stickynotes.Classes.NotesObject;
import nikhilg.dev.stickynotes.Helper.NotesDb;
import nikhilg.dev.stickynotes.Helper.Utils;
import nikhilg.dev.stickynotes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OneNoteFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton add_note_fab;
    private TextView no_notes_txt;
    private RecyclerView mRecyclerView;
    private MyAllNotesViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<NotesObject> all_notes;
    NotesObject single_note;

    private int note_id;

    public OneNoteFragment() {
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
        note_id = Integer.parseInt(getArguments().getString("note_id"));
        Log.d("nikhilservice", "in one frag on resume, note id is : " + note_id);
        single_note = new NotesDb().FetchNote(note_id);
        all_notes.clear();
        all_notes.add(single_note);
        if (all_notes.size() > 0) {
            no_notes_txt.setVisibility(View.GONE);
            mAdapter = new MyAllNotesViewAdapter(all_notes, getActivity());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    private void init(View v) {
        add_note_fab = (FloatingActionButton) v.findViewById(R.id.add_note_fab);
        no_notes_txt = (TextView) v.findViewById(R.id.no_notes_txt);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.notes_list);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        add_note_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
            }
        });
        all_notes = new NotesDb().FetchAllNotes();
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

    public class MyAllNotesViewAdapter extends RecyclerView.Adapter<MyAllNotesViewAdapter.DataObjectHolder> {
        public List<NotesObject> mDataset;
        Context context;

        public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView small_title, title, body, edit, del, modified_on;
            CheckBox checkBox;

            public DataObjectHolder(View v) {
                super(v);
                small_title = (TextView) v.findViewById(R.id.small_title);
                title = (TextView) v.findViewById(R.id.title);
                body = (TextView) v.findViewById(R.id.note_body);
                edit = (TextView) v.findViewById(R.id.btn_edit);
                del = (TextView) v.findViewById(R.id.btn_delete);
                checkBox = (CheckBox) v.findViewById(R.id.allow_noteheads);
                modified_on = (TextView) v.findViewById(R.id.modified_on);

                edit.setOnClickListener(this);
                del.setOnClickListener(this);
                checkBox.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_edit:
                        Intent i = new Intent(getActivity(), EditNote.class);
                        i.putExtra("note_object", new Gson().toJson(mDataset.get((Integer) v.getTag())));
                        startActivity(i);
                        getActivity().overridePendingTransition(R.animator.pull_in_right, R.animator.push_out_left);
                        break;
                    case R.id.btn_delete:
                        ((MainActivity) getActivity()).showPopUp("Delete Note?", "Are you sure you want to permanently delete this note. You will not be able to recover it later.",
                                "DELETE", "CANCEL", mDataset.get((Integer) v.getTag()));
                        break;
                    case R.id.allow_noteheads:
                        CheckBox cbox = (CheckBox) v;
                        if (cbox.isChecked()) {
                            ((MainActivity) getActivity()).startService(mDataset.get((Integer) v.getTag()));
                        } else {
                            ((MainActivity) getActivity()).stopService(mDataset.get((Integer) v.getTag()));
                        }
                        if (!Utils.getInstance(context).getBoolValue("dont_show_popup")) {
                            ((MainActivity) getActivity()).showNoteHeadPopUp();
                        }
                        break;

                }
            }
        }

        public MyAllNotesViewAdapter(List<NotesObject> mDataset, Context context) {
            this.mDataset = mDataset;
            this.context = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_single_note, parent, false);
            DataObjectHolder dHolder = new DataObjectHolder(v);
            return dHolder;
        }

        @Override
        public void onBindViewHolder(DataObjectHolder holder, int position) {
            Typeface type_bold = Typeface.createFromAsset(getResources().getAssets(),"fonts/Roboto-Bold.ttf");
            Typeface type_medium = Typeface.createFromAsset(getResources().getAssets(),"fonts/Roboto-Medium.ttf");
            Typeface type_regular = Typeface.createFromAsset(getResources().getAssets(),"fonts/Roboto-Regular.ttf");

            holder.small_title.setText(mDataset.get(position).getSmallTitle());
            holder.title.setText(mDataset.get(position).getTitle());
            holder.body.setText(mDataset.get(position).getNoteBody());
            holder.modified_on.setText("Last Modified On : " + mDataset.get(position).getLastModifiedOn());
            if (mDataset.get(position).getShowIcon() == 1) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
                if (Utils.getInstance(getActivity()).getIntValue("active_services") < 5) {
                    holder.checkBox.setEnabled(true);
                    holder.checkBox.setTypeface(type_medium);
                } else {
                    holder.checkBox.setEnabled(false);
                    holder.checkBox.setTypeface(type_regular);
                }
            }
            holder.edit.setTypeface(type_bold);
            holder.edit.setTag(position);
            holder.del.setTypeface(type_bold);
            holder.del.setTag(position);
            holder.title.setTypeface(type_bold);
            holder.modified_on.setTypeface(type_regular);
            holder.body.setTypeface(type_regular);
            holder.checkBox.setTag(position);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}
