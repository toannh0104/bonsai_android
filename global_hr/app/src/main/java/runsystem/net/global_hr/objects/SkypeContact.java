package runsystem.net.global_hr.objects;

/**
 * Created by LuanDang on 12/28/2015.
 */
public class SkypeContact {
    private String contact_id;
    private String display_name;

    public SkypeContact(String contact_id, String display_name) {
        this.contact_id = contact_id;
        this.display_name = display_name;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }
}
