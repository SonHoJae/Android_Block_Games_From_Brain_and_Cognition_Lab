package brainandcognitionlab.bac.Model.test_list.memory_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import brainandcognitionlab.bac.Model.test_list.memory_test.Tests.Test_blockchecker_Activity;
import brainandcognitionlab.bac.R;

public class Category_memory extends AppCompatActivity {

    Button tests[] = new Button[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        tests[0] = (Button)findViewById(R.id.test_1);
        tests[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent toBlockChecker = new Intent(Category_memory.this,Test_blockchecker_Activity.class);
                startActivity(toBlockChecker);
            }
        });
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
