package nikhilg.dev.stickynotes.Classes;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import nikhilg.dev.stickynotes.Helper.Utils;

/**
 * Created by nik on 2/5/16.
 */
public class StopNoteHeadService {
    public StopNoteHeadService(Context context, NotesObject note) {
        Intent i = null;
        int num_active_services = Utils.getInstance(context).getIntValue("active_services");
        num_active_services--;
        Utils.getInstance(context).addIntValue("active_services", num_active_services);
        int service_num = note.getService_num();
        if (service_num == 1) {
            i = new Intent(context, NoteHeadService1.class);
            Utils.getInstance(context).addBoolValue("service_1_active", false);
        } else if (service_num == 2) {
            i = new Intent(context, NoteHeadService2.class);
            Utils.getInstance(context).addBoolValue("service_2_active", false);
        } else if (service_num == 3) {
            i = new Intent(context, NoteHeadService3.class);
            Utils.getInstance(context).addBoolValue("service_3_active", false);
        } else if (service_num == 4) {
            i = new Intent(context, NoteHeadService4.class);
            Utils.getInstance(context).addBoolValue("service_4_active", false);
        } else {
            i = new Intent(context, NoteHeadService5.class);
            Utils.getInstance(context).addBoolValue("service_5_active", false);
        }
        context.stopService(i);
    }
}
