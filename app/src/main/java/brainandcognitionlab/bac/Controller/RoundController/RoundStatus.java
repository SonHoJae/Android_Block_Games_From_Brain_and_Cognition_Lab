package brainandcognitionlab.bac.Controller.RoundController;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

/**
 * Created by hojaeson on 1/23/16.
 */
//모든 테스트에서 Round가 어떻게 돌아가는지 flow chart에 맞게 테스트가 진행되도록 하는 클래스
public class RoundStatus implements Parcelable {
    private int level;
    private Pair<Integer, Integer> internalStage;
    private int numOfStars;
    private boolean finished;

    public RoundStatus(int level, Pair<Integer, Integer> internalStage, int numOfStars, boolean finished) {
        this.level = level;
        this.internalStage = internalStage;
        this.numOfStars = numOfStars;
        this.finished = finished;
    }

    public RoundStatus(Parcel in) {
        readFromParcel(in);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Pair<Integer, Integer> getInternalStage() {
        return internalStage;
    }

    public void setInternalStage(Pair<Integer, Integer> internalStage) {
        this.internalStage = internalStage;
    }

    public int getNumOfStars() {
        return numOfStars;
    }

    public void setNumOfStars(int numOfStars) {
        this.numOfStars = numOfStars;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    //하나의 라운드를 성공했을 때 쓸 함수
    public RoundStatus successToRound() {

        if (this.getLevel() == 6) {
            if (this.getNumOfStars() == 2) {
                this.setFinished(true);
                return this;
            }
            else
            {
                this.increaseNumOfStar();
            }
        }
        //레벨1,2,3와 달리 레벨이 4,5일 경우에는 internalState에 따라서 가짜 값이 들어가야 할 수 있으므로 경우를 분할한다.
        else {

            if (this.getLevel() == 4) {
                //switch (this.getInternalStage().first) { //가짜 값을 고려하는 부분은 나중에 구현하겠다.
                //별이 세개가 되었을 경우 다음 레벨로 넘어간다(별의 갯수는 초기화)
                //또한 이제부터는 internalState도 고려해야하므로 초기화한다.

                if (this.getNumOfStars() == 2) {
                    //가짜
                    if (this.getInternalStage().first == 1 && this.getInternalStage().second == 2) {
                        this.setNumOfStars(0);
                        this.setInternalStage(new Pair(1, 3));
                    }
                    //가짜
                    else if (this.getInternalStage().first == 1 && this.getInternalStage().second == 3) {
                        this.setNumOfStars(0);
                        this.setInternalStage(new Pair(1, 4));
                    }
                    else if(this.getInternalStage().first == 1 && this.getInternalStage().second == 4)
                    {
                        this.setFinished(true);
                    }
                    else {
                        this.increaseLevel();
                        this.setNumOfStars(0);
                        this.setInternalStage(new Pair(this.getInternalStage().first, 0));
                    }
                } else {
                    this.increaseNumOfStar();
                }
            } else if (this.getLevel() == 5) {
                if (this.getNumOfStars() == 2) {
                    if (this.getInternalStage().first < 3) {
                        this.increaseLevel();
                        this.setNumOfStars(0);
                        this.setInternalStage(new Pair(0, 0));
                    } else {
                        //각 노드의 경우의 수가 모두 다르다..
                        if (this.getInternalStage().first == 3) {
                            this.setInternalStage(new Pair(1, 0));
                            this.increaseLevel();
                            this.setNumOfStars(0);
                        } else if (this.getInternalStage().first == 4 && this.getInternalStage().second == 0) {
                            this.setInternalStage(new Pair(2, 0));
                            this.increaseLevel();
                            this.setNumOfStars(0);
                        } else if (this.getInternalStage().first == 4 && this.getInternalStage().second == 1) {
                            //가짜
                            this.setInternalStage(new Pair(4, 2));
                            this.setNumOfStars(0);
                        }
                        //5,(4,2)
                        else if(this.getInternalStage().first == 4 && this.getInternalStage().second == 2) {
                            this.setFinished(true);
                        }


                    }
                } else {
                    this.increaseNumOfStar();
                }
            } else {
                if (this.getNumOfStars() == 2) {
                    this.increaseLevel();
                    this.setNumOfStars(0);
                    this.setInternalStage(new Pair(0, 0));
                }
                else {
                    this.increaseNumOfStar();
                }
            }

        }
        return this;
    }


    //하나의 라운드를 실패했을 때 쓸 함수
    //case별로 InternalStatus를 변화시킨다. 가독성을 위해 level parameter를 넘기겠다.
    public RoundStatus failToRound() {


        if (this.getLevel() <= 3) {
            //1,2,3 단계에서 실패해버리면 그 단계를 반복해야 하므로 별표 갯수만 초기화 시켜준다.
            this.setNumOfStars(0);
        }
        //레벨이 4,5,6일 경우에는 internalStatus를 고려해야만 한다.
        else {
            this.setNumOfStars(0);
            switch (this.level) {
                case 4:
                    //level 4 에서 StateDiagram을 관찰한 결과,  가짜 5,6을 가지는 상태를 가지기 위해서는i nternalStage의 합이 2일 경우임을 확인
                    if (this.getInternalStage().first + this.getInternalStage().second == 2) {
                        this.setInternalStage(new Pair(1, 2));
                        //this.setFinished(true);
                    }
                    if (this.getInternalStage().first + this.getInternalStage().second != 2 && this.getInternalStage().second < 2)
                        this.increaseInternalY();

                    break;
                case 5:
                    if (this.getInternalStage().first < 2) {
                        decreaseLevel();
                        this.increaseInternalX();
                    } else if (this.getInternalStage().first == 2) {
                        this.decreaseLevel();
                        this.setInternalStage(new Pair(1, 2));
                    }
                    //level 5에서 internal stage가 3이상인 경우는 레벨 6을 한번이라도 찍었을 때 나타나는 경우이다.
                    else {
                        if (this.getInternalStage().first + this.getInternalStage().second == 4) {
                            this.setInternalStage(new Pair(4, 1));
                            //this.setFinished(true);
                        }
                        if (this.getInternalStage().first + this.getInternalStage().second < 4 && this.getInternalStage().second < 2)
                            this.increaseInternalY();
                        //else do nothing keep this status
                    }
                    break;
                case 6:
                    if (this.getInternalStage().first < 2) {
                        decreaseLevel();
                        for (int i = 0; i < 3; i++)
                            increaseInternalX();
                    } else {
                        this.decreaseLevel();
                        this.setInternalStage(new Pair(4, 1));
                        //this.setFinished(true);
                    }
                    break;
            }
        }
        return this;
    }


    //이 함수들은 roundStatus클래스를 컨트롤하기 위함
    public void increaseLevel() {
        this.setLevel(this.getLevel() + 1);
    }

    public void increaseNumOfStar() {
        this.setNumOfStars(this.getNumOfStars() + 1);
    }

    public void increaseInternalX() {
        this.setInternalStage(new Pair<>(this.getInternalStage().first + 1
                , this.getInternalStage().second));
    }

    public void increaseInternalY() {
        this.setInternalStage(new Pair<>(this.getInternalStage().first
                , this.getInternalStage().second + 1));
    }

    public void decreaseLevel() {
        this.setLevel(this.getLevel() - 1);
    }

    public void decreaseNumOfStar() {
        this.setLevel(this.getLevel() + 1);
    }

    public void decreaseInternalX() {
        this.setInternalStage(new Pair<>(this.getInternalStage().first - 1
                , this.getInternalStage().second));
    }

    public void decreaseInternalY() {
        this.setInternalStage(new Pair<>(this.getInternalStage().first
                , this.getInternalStage().second - 1));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(level);
        dest.writeInt(numOfStars);
        dest.writeByte((byte) (finished ? 1 : 0));
        dest.writeInt(internalStage.first);
        dest.writeInt(internalStage.second);
    }

    private void readFromParcel(Parcel in) {
        level = in.readInt();
        numOfStars = in.readInt();
        finished = in.readByte() != 0;
        this.setInternalStage(new Pair(in.readInt(), in.readInt()));

    }

    public static final Creator CREATOR = new Creator() {
        public RoundStatus createFromParcel(Parcel in) {
            return new RoundStatus(in);
        }

        public RoundStatus[] newArray(int size) {
            return new RoundStatus[size];
        }
    };
}

