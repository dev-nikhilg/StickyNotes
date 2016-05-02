package nikhilg.dev.stickynotes.Classes;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

/**
 * Created by nik on 2/5/16.
 */
public class StartNoteHeadService {
    public StartNoteHeadService(Context context, NotesObject note) {
        if (note.getService_num() == 1) {
            Intent i = new Intent(context, NoteHeadService1.class);
            i.putExtra("note_object", new Gson().toJson(note));
            context.startService(i);
        } else if (note.getService_num() == 2) {
            Intent i = new Intent(context, NoteHeadService2.class);
            i.putExtra("note_object", new Gson().toJson(note));
            context.startService(i);
        } else if (note.getService_num() == 3) {
            Intent i = new Intent(context, NoteHeadService3.class);
            i.putExtra("note_object", new Gson().toJson(note));
            context.startService(i);
        } else if (note.getService_num() == 4) {
            Intent i = new Intent(context, NoteHeadService4.class);
            i.putExtra("note_object", new Gson().toJson(note));
            context.startService(i);
        } else {
            Intent i = new Intent(context, NoteHeadService5.class);
            i.putExtra("note_object", new Gson().toJson(note));
            context.startService(i);
        }
    }
}
