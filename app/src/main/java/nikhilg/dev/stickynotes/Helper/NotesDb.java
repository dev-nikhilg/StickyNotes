package nikhilg.dev.stickynotes.Helper;

import java.util.List;

import nikhilg.dev.stickynotes.Classes.NotesObject;

/**
 * Created by nik on 29/4/16.
 */
public class NotesDb {
    public void AddToDb(NotesObject note) {
        DbHandler.AddNoteToDb(note.getId(), note.getCreatedOn(), note.getLastModifiedOn(), note.getSmallTitle(), note.getTitle(), note.getNoteBody(), note.getShowIcon(), note.getService_num());
    }

    public List<NotesObject> FetchAllNotes() {
        return DbHandler.FetchAllNotes();
    }

    public NotesObject FetchNote(int id) {
        return DbHandler.FetchNote(id);
    }

    public int DeleteNote(int id) {
        return DbHandler.deleteNote(id);
    }

    public void UpdateNote(NotesObject note) {
        DeleteNote(note.getId());
        AddToDb(note);
    }
}
