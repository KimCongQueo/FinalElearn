package nauq.mal.com.formapp.api.objects;

import com.google.gson.annotations.SerializedName;

public class PasswordInput {

    @SerializedName("oldPass")
    public String oldPass;
    @SerializedName("newPass")
    public String newPass;
    @SerializedName("confirmPass")
    public String confirmPass;

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
