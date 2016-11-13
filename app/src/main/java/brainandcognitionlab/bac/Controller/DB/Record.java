package brainandcognitionlab.bac.Controller.DB;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hojaeson on 1/10/16.
 */
public class Record implements Parcelable {
    User user;
    private int R_ID;
    private String DURATION;
    private String LEVEL;
    private boolean isSuccess;
    private int gameId;
    private String start_date;
    private String end_date;

    public Record(User user,String DURATION, String LEVEL,Boolean isSuccess,int gameId,String start_date,String end_date) {
        this.user = user;
        this.DURATION = DURATION;
        this.LEVEL = LEVEL;
        this.isSuccess = isSuccess;
        this.gameId = gameId;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Record(Parcel in)  {
        readFromParcel(in);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getR_ID() {
        return R_ID;
    }

    public void setR_ID(int r_ID) {
        R_ID = r_ID;
    }

    public String getDURATION() {
        return DURATION;
    }

    public void setDURATION(String DURATION) {
        this.DURATION = DURATION;
    }

    public String getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(String LEVEL) {
        this.LEVEL = LEVEL;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getStartDate() { return start_date;}

    public void setStartDate(String start_date) { this.start_date = start_date;}

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String end_date) {
        this.end_date = end_date;
    }

    public String getUserAccount() {return this.user.getAccount();}
    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user, flags);
        dest.writeInt(R_ID);
        dest.writeString(DURATION);
        dest.writeString(LEVEL);
        dest.writeByte((byte) (isSuccess ? 1 : 0));
        dest.writeInt(gameId);
    }

    private void readFromParcel(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        this.R_ID = in.readInt();
        this.DURATION = in.readString();
        this.LEVEL = in.readString();
        this.isSuccess = in.readByte() != 0;
        this.gameId = in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Record createFromParcel(Parcel in) {
            return new Record(in);
        }

        public Record[] newArray(int size) {
            return new Record[size];
        }
    };


}
