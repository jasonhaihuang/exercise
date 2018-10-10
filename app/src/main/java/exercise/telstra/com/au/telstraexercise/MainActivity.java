package exercise.telstra.com.au.telstraexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import exercise.telstra.com.au.telstraexercise.ui.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    // update the title on actionbar.
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
