package nikhilg.dev.stickynotes.Classes;

import android.content.Context;

import nikhilg.dev.stickynotes.Helper.Utils;

/**
 * Created by nik on 2/5/16.
 */
public class GetServicenumber {
    public int getNumber(Context context) {
        int service_num = -1;
        if (Utils.getInstance(context).getIntValue("active_services") < 5) {
            int num_active_services = Utils.getInstance(context).getIntValue("active_services");
            if (num_active_services == -1) {
                num_active_services = 1;
            } else {
                num_active_services++;
            }
            Utils.getInstance(context).addIntValue("active_services", num_active_services);
            if (!Utils.getInstance(context).getBoolValue("service_1_active")) {
                service_num = 1;
                Utils.getInstance(context).addBoolValue("service_1_active", true);
            } else if (!Utils.getInstance(context).getBoolValue("service_2_active")) {
                service_num = 2;
                Utils.getInstance(context).addBoolValue("service_2_active", true);
            } else if (!Utils.getInstance(context).getBoolValue("service_3_active")) {
                service_num = 3;
                Utils.getInstance(context).addBoolValue("service_3_active", true);
            } else if (!Utils.getInstance(context).getBoolValue("service_4_active")) {
                service_num = 4;
                Utils.getInstance(context).addBoolValue("service_4_active", true);
            } else if (!Utils.getInstance(context).getBoolValue("service_5_active")) {
                service_num = 5;
                Utils.getInstance(context).addBoolValue("service_5_active", true);
            }
        }
        return service_num;
    }
}
