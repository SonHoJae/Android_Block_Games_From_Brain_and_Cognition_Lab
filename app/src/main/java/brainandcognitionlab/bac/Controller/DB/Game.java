package brainandcognitionlab.bac.Controller.DB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hojaeson on 1/10/16.
 */
public class Game implements Parcelable {

    private int GAME_ID;
    private String GAME_NAME;
    private String GAME_TYPE;

    public Game(int id,String name,String type) {
        GAME_ID = id;
        GAME_NAME = name;
        GAME_TYPE = type;
    }
    public Game(Parcel in)  {
        readFromParcel(in);
    }

    public int getGAME_ID() {
        return GAME_ID;
    }

    public void setGAME_ID(int GAME_ID) {
        this.GAME_ID = GAME_ID;
    }

    public String getGAME_NAME() {
        return GAME_NAME;
    }

    public void setGAME_NAME(String GAME_NAME) {
        this.GAME_NAME = GAME_NAME;
    }

    public String getGAME_TYPE() {
        return GAME_TYPE;
    }

    public void setGAME_TYPE(String GAME_TYPE) {
        this.GAME_TYPE = GAME_TYPE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(GAME_ID);
        dest.writeString(GAME_NAME);
        dest.writeString(GAME_TYPE);
    }

    private void readFromParcel(Parcel in) {
        this.GAME_ID = in.readInt();
        this.GAME_NAME = in.readString();
        this.GAME_TYPE = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
