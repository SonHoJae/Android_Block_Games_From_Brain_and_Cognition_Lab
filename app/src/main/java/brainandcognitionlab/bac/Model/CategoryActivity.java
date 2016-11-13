package brainandcognitionlab.bac.Model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import brainandcognitionlab.bac.Controller.DB.User;
import brainandcognitionlab.bac.Model.test_list.concentration_test.Category_concentration;
import brainandcognitionlab.bac.Model.test_list.memory_test.Category_memory;
import brainandcognitionlab.bac.R;

public class CategoryActivity extends AppCompatActivity {

    private Button test_category_btn[] = new Button[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        final User user = intent.getExtras().getParcelable("user");
        test_category_btn[0] = (Button)findViewById(R.id.test_1);
        test_category_btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toConcentrationTestIntent = new Intent(CategoryActivity.this, Category_concentration.class);
                toConcentrationTestIntent.putExtra("user",user);
                startActivity(toConcentrationTestIntent);
            }
        });
        test_category_btn[1] = (Button)findViewById(R.id.test_2);
        test_category_btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toMemoryTestIntent = new Intent(CategoryActivity.this, Category_memory.class);
                toMemoryTestIntent.putExtra("user",user);
                startActivity(toMemoryTestIntent);
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
