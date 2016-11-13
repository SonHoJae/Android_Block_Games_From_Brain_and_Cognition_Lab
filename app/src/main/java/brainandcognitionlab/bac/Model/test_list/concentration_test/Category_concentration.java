package brainandcognitionlab.bac.Model.test_list.concentration_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import brainandcognitionlab.bac.Controller.DB.DBHelper;
import brainandcognitionlab.bac.Controller.DB.Game;
import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_BlockChecker_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_BlockMatcher_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_Block_Matcher_Letter_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_CountingBananas_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_JudgeFromStimulus_Color_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_JudgeFromStimulus_OX_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberAsOrder_Fruit_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberAsOrder_Letter_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberAsOrder_Number_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberAsReverseOrder_Fruit_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberAsReverseOrder_Letter_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberAsReverseOrder_Number_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberToBlock_Fruit_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberToBlock_Letter_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_MapNumberToBlock_Number_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_NumberBlockChecker_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_NumberOrderTracker_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_OrderTracker_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_PathFinder_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_ReverseOrderTracker_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_SelectColorFromStimulus_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_SelectDirectionFromStimulus_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_SelectShapeFromStimulus_Activity;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Tests.Test_UserChoosenBlockTracker_Activity;
import brainandcognitionlab.bac.R;

public class Category_concentration extends AppCompatActivity {

    Button tests[] = new Button[24];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration);
        final Game concentrationGame[] = new Game[25];
        final User user = getIntent().getParcelableExtra("user");

        concentrationGame[0] = new Game(1,"OrderTracker","Concentration");
        tests[0] = (Button)findViewById(R.id.test_1);
        tests[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toOrderTracker = new Intent(Category_concentration.this, Test_OrderTracker_Activity.class);
                toOrderTracker.putExtra("game", concentrationGame[0]);
                toOrderTracker.putExtra("user",user);
                startActivity(toOrderTracker);
            }
        });
        concentrationGame[1] = new Game(2,"ReverseOrderTracker","Concentration");
        tests[1] = (Button)findViewById(R.id.test_2);
        tests[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toReverseOrderTracker = new Intent(Category_concentration.this, Test_ReverseOrderTracker_Activity.class);
                toReverseOrderTracker.putExtra("game", concentrationGame[1]);
                toReverseOrderTracker.putExtra("user",user);
                startActivity(toReverseOrderTracker);
            }
        });
        concentrationGame[2] = new Game(3,"BlockChecker","Concentration");
        tests[2] = (Button)findViewById(R.id.test_3);
        tests[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBlockChecker = new Intent(Category_concentration.this,Test_BlockChecker_Activity.class);
                toBlockChecker.putExtra("game",concentrationGame[2]);
                toBlockChecker.putExtra("user",user);
                startActivity(toBlockChecker);
            }
        });
        concentrationGame[3] = new Game(4,"NumberBlockChecker","Concentration");
        tests[3] = (Button)findViewById(R.id.test_4);
        tests[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNumberBlockChecker = new Intent(Category_concentration.this, Test_NumberBlockChecker_Activity.class);
                toNumberBlockChecker.putExtra("game",concentrationGame[3]);
                toNumberBlockChecker.putExtra("user",user);
                startActivity(toNumberBlockChecker);
            }
        });
        concentrationGame[4] = new Game(5,"NumberOrderTracker","Concentration");
        tests[4] = (Button)findViewById(R.id.test_5);
        tests[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNumberOrderTracker = new Intent(Category_concentration.this, Test_NumberOrderTracker_Activity.class);
                toNumberOrderTracker.putExtra("game",concentrationGame[4]);
                toNumberOrderTracker.putExtra("user",user);
                startActivity(toNumberOrderTracker);
            }
        });
        concentrationGame[5] = new Game(6,"BlockMatcher","Concentration");
        tests[5] = (Button)findViewById(R.id.test_6);
        tests[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBlockMatcher = new Intent(Category_concentration.this, Test_BlockMatcher_Activity.class);
                toBlockMatcher.putExtra("game",concentrationGame[5]);
                toBlockMatcher.putExtra("user",user);
                startActivity(toBlockMatcher);
            }
        });
        concentrationGame[6] = new Game(7,"MapNumbertoBlock","Concentration");
        tests[6] = (Button)findViewById(R.id.test_7);
        tests[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberToBlock = new Intent(Category_concentration.this, Test_MapNumberToBlock_Letter_Activity.class);
                toMapNumberToBlock.putExtra("game", concentrationGame[6]);
                toMapNumberToBlock.putExtra("user", user);
                startActivity(toMapNumberToBlock);
            }
        });

        concentrationGame[7] = new Game(8,"UserChoosenBlockTracker","Concentration");
        tests[7] = (Button)findViewById(R.id.test_8);
        tests[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toUserChoosenBlockTracker = new Intent(Category_concentration.this, Test_UserChoosenBlockTracker_Activity.class);
                toUserChoosenBlockTracker.putExtra("game", concentrationGame[7]);
                toUserChoosenBlockTracker.putExtra("user", user);
                startActivity(toUserChoosenBlockTracker);
            }
        });

        concentrationGame[8] = new Game(9,"MapNumberAsOrder","Concentration");
        tests[8] = (Button)findViewById(R.id.test_9);
        tests[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberAsOrder = new Intent(Category_concentration.this, Test_MapNumberAsOrder_Fruit_Activity.class);
                toMapNumberAsOrder.putExtra("game", concentrationGame[8]);
                toMapNumberAsOrder.putExtra("user", user);
                startActivity(toMapNumberAsOrder);
            }
        });

        concentrationGame[9] = new Game(10,"MapNumberAsReverseOrder","Concentration");
        tests[9] = (Button)findViewById(R.id.test_10);
        tests[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toUserChoosenBlockTracker = new Intent(Category_concentration.this, Test_MapNumberAsReverseOrder_Fruit_Activity.class);
                toUserChoosenBlockTracker.putExtra("game", concentrationGame[9]);
                toUserChoosenBlockTracker.putExtra("user", user);
                startActivity(toUserChoosenBlockTracker);
            }
        });
        concentrationGame[10] = new Game(11,"BlockMatcher_Letter","Concentration");
        tests[10] = (Button)findViewById(R.id.test_11);
        tests[10].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBlockMatcher_Letter = new Intent(Category_concentration.this, Test_Block_Matcher_Letter_Activity.class);
                toBlockMatcher_Letter.putExtra("game",concentrationGame[10]);
                toBlockMatcher_Letter.putExtra("user",user);
                startActivity(toBlockMatcher_Letter);
            }
        });
        concentrationGame[11] = new Game(12,"BlockMatcher_Number","Concentration");
        tests[11] = (Button)findViewById(R.id.test_12);
        tests[11].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberToBlock_Number = new Intent(Category_concentration.this, Test_MapNumberToBlock_Number_Activity.class);
                toMapNumberToBlock_Number.putExtra("game",concentrationGame[11]);
                toMapNumberToBlock_Number.putExtra("user",user);
                startActivity(toMapNumberToBlock_Number);
            }
        });
        concentrationGame[12] = new Game(13,"MapNumberToBlock_Fruit","Concentration");
        tests[12] = (Button)findViewById(R.id.test_13);
        tests[12].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberToBlock_Fruit = new Intent(Category_concentration.this, Test_MapNumberToBlock_Fruit_Activity.class);
                toMapNumberToBlock_Fruit.putExtra("game",concentrationGame[12]);
                toMapNumberToBlock_Fruit.putExtra("user",user);
                startActivity(toMapNumberToBlock_Fruit);
            }
        });
        concentrationGame[13] = new Game(14,"MapNumberAsOrder_Letter","Concentration");
        tests[13] = (Button)findViewById(R.id.test_14);
        tests[13].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberAsOrder_Letter = new Intent(Category_concentration.this, Test_MapNumberAsOrder_Letter_Activity.class);
                toMapNumberAsOrder_Letter.putExtra("game",concentrationGame[13]);
                toMapNumberAsOrder_Letter.putExtra("user",user);
                startActivity(toMapNumberAsOrder_Letter);
            }
        });
        concentrationGame[14] = new Game(15,"MapNumberAsOrder_Number","Concentration");
        tests[14] = (Button)findViewById(R.id.test_15);
        tests[14].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberAsOrder_Number = new Intent(Category_concentration.this, Test_MapNumberAsOrder_Number_Activity.class);
                toMapNumberAsOrder_Number.putExtra("game",concentrationGame[14]);
                toMapNumberAsOrder_Number.putExtra("user",user);
                startActivity(toMapNumberAsOrder_Number);
            }
        });
        concentrationGame[15] = new Game(16,"MapNumberAsOrder_Number","Concentration");
        tests[15] = (Button)findViewById(R.id.test_16);
        tests[15].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberAsReverseOrder = new Intent(Category_concentration.this, Test_MapNumberAsReverseOrder_Letter_Activity.class);
                toMapNumberAsReverseOrder.putExtra("game",concentrationGame[15]);
                toMapNumberAsReverseOrder.putExtra("user",user);
                startActivity(toMapNumberAsReverseOrder);
            }
        });
        concentrationGame[16] = new Game(17,"MapNumberAsReverseOrder_Number","Concentration");
        tests[16] = (Button)findViewById(R.id.test_17);
        tests[16].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMapNumberAsReverseOrder_Number = new Intent(Category_concentration.this, Test_MapNumberAsReverseOrder_Number_Activity.class);
                toMapNumberAsReverseOrder_Number.putExtra("game",concentrationGame[16]);
                toMapNumberAsReverseOrder_Number.putExtra("user",user);
                startActivity(toMapNumberAsReverseOrder_Number);
            }
        });
        concentrationGame[17] = new Game(18,"MapNumberAsReverseOrder_Number","Concentration");
        tests[17] = (Button)findViewById(R.id.test_18);
        tests[17].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPathFinder = new Intent(Category_concentration.this, Test_PathFinder_Activity.class);
                toPathFinder.putExtra("game",concentrationGame[17]);
                toPathFinder.putExtra("user",user);
                startActivity(toPathFinder);
            }
        });

        concentrationGame[18] = new Game(19,"CountingBananas","Concentration");
        tests[18] = (Button)findViewById(R.id.test_19);
        tests[18].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCountingBananas = new Intent(Category_concentration.this, Test_CountingBananas_Activity.class);
                toCountingBananas.putExtra("game",concentrationGame[18]);
                toCountingBananas.putExtra("user",user);
                startActivity(toCountingBananas);
            }
        });

        concentrationGame[19] = new Game(20,"JudgeFromStimulus_OX","Concentration");
        tests[19] = (Button)findViewById(R.id.test_20);
        tests[19].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toJudgeFromStimulus_OX = new Intent(Category_concentration.this, Test_JudgeFromStimulus_OX_Activity.class);
                toJudgeFromStimulus_OX.putExtra("game",concentrationGame[19]);
                toJudgeFromStimulus_OX.putExtra("user",user);
                startActivity(toJudgeFromStimulus_OX);

            }
        });

        concentrationGame[20] = new Game(21,"SelectDirectionFromStimulus","Concentration");
        tests[20] = (Button)findViewById(R.id.test_21);
        tests[20].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSelectDirectionFromStimulus = new Intent(Category_concentration.this, Test_SelectDirectionFromStimulus_Activity.class);
                toSelectDirectionFromStimulus.putExtra("game",concentrationGame[20]);
                toSelectDirectionFromStimulus.putExtra("user",user);
                startActivity(toSelectDirectionFromStimulus);

            }
        });

        concentrationGame[21] = new Game(22,"SelectShapeFromStimulus","Concentration");
        tests[21] = (Button)findViewById(R.id.test_22);
        tests[21].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSelectShapeFromStimulus = new Intent(Category_concentration.this, Test_SelectShapeFromStimulus_Activity.class);
                toSelectShapeFromStimulus.putExtra("game",concentrationGame[21]);
                toSelectShapeFromStimulus.putExtra("user",user);
                startActivity(toSelectShapeFromStimulus);

            }
        });

        concentrationGame[22] = new Game(23,"JudgeFromStimulus_Color","Concentration");
        tests[22] = (Button)findViewById(R.id.test_23);
        tests[22].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toJudgeFromStimulus_Color = new Intent(Category_concentration.this, Test_JudgeFromStimulus_Color_Activity.class);
                toJudgeFromStimulus_Color.putExtra("game",concentrationGame[22]);
                toJudgeFromStimulus_Color.putExtra("user",user);
                startActivity(toJudgeFromStimulus_Color);

            }
        });

        concentrationGame[23] = new Game(24,"SelectColorFromStimulus","Concentration");
        tests[23] = (Button)findViewById(R.id.test_24);
        tests[23].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSelectColorFromStimulus = new Intent(Category_concentration.this, Test_SelectColorFromStimulus_Activity.class);
                toSelectColorFromStimulus.putExtra("game",concentrationGame[23]);
                toSelectColorFromStimulus.putExtra("user",user);
                startActivity(toSelectColorFromStimulus);

            }
        });
        //selectDataFromGameTable return isEmpty
        if(DBHelper.getSingleton_DB_Instance(this).selectDataFromGameTable()) {
            for (int i = 0; i < 22; i++) {
                DBHelper.getSingleton_DB_Instance(this).insertDataToGameTable(concentrationGame[i]);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
