package brainandcognitionlab.bac.Model;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import brainandcognitionlab.bac.Controller.RoundController.RoundStatus;
import brainandcognitionlab.bac.R;

/**
 * Created by hojaeson on 4/18/16.
 */
public class StatusLayout {
    private ImageView stars[] = new ImageView[3];
    private TextView statusView;
    private Context context;

    public StatusLayout(Context current, ImageView[] stars, TextView statusViewContext) {
        this.context = current;
        this.stars[0] = stars[0];
        this.stars[1] = stars[1];
        this.stars[2] = stars[2];
        this.statusView = statusView;
    }

    public void setStars(RoundStatus rs) {
        for (int i = 0; i < rs.getNumOfStars(); i++) {
            stars[i].setImageDrawable(this.context.getResources().getDrawable(R.drawable.yellow_star));
        }
    }

    public void setStatusView(TextView statusView, RoundStatus rs) {
        statusView.setTextSize(20.0f);
        statusView.setText("level   " + rs.getLevel() + " first   "
                + rs.getInternalStage().first + " second   " + rs.getInternalStage().second + " stars   "
                + rs.getNumOfStars());

    }
    public void setStarYellow(ImageView star) {
        stars[2].setImageDrawable(this.context.getResources().getDrawable(R.drawable.yellow_star));
    }
}
