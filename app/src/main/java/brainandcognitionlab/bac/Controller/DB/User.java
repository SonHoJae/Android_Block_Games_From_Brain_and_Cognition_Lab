package brainandcognitionlab.bac.Controller.DB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hojaeson on 1/10/16.
 */
public class User implements Parcelable{
    private String ACCOUNT;
    private String NAME;
    private String EMAIL;
    private String PASSWORD;

    public User(String account,String name,String password, String email)
    {
        ACCOUNT = account;
        NAME = name;
        EMAIL = email;
        PASSWORD = password;
    }

    public User(Parcel in) {
        readFromParcel(in);
    }


    public String getAccount() {
        return ACCOUNT;
    }

    public String getNAME() {
        return NAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setACCOUNT(String account) {
        this.ACCOUNT = account;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ACCOUNT);
        dest.writeString(NAME);
        dest.writeString(EMAIL);
        dest.writeString(PASSWORD);
    }

    private void readFromParcel(Parcel in) {
        ACCOUNT = in.readString();
        NAME = in.readString();
        EMAIL = in.readString();
        PASSWORD = in.readString();

    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
