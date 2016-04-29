package nikhilg.dev.stickynotes.Classes;

/**
 * Created by nik on 29/4/16.
 */
public class NotesObject {
    private int id;
    private String createdOn;
    private String LastModifiedOn;
    private String smallTitle;
    private String title;
    private String noteBody;
    private int showIcon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastModifiedOn() {
        return LastModifiedOn;
    }

    public void setLastModifiedOn(String lastModifiedOn) {
        LastModifiedOn = lastModifiedOn;
    }

    public String getSmallTitle() {
        return smallTitle;
    }

    public void setSmallTitle(String smallTitle) {
        this.smallTitle = smallTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public int getShowIcon() {
        return showIcon;
    }

    public void setShowIcon(int showIcon) {
        this.showIcon = showIcon;
    }
}
