package exercise.telstra.com.au.telstraexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import exercise.telstra.com.au.telstraexercise.ui.MainFragment;

/**
 * Introduction:
 *
 * The UI of this project is Activity and Fragment. The UI logics are all in the MainFragment.
 * The MainActivity only provide one logic function, set the title.
 *
 * The MainFragment maintains the UI components: RecyclerView, TextViews and Button. The data for
 * the RecyclerView comes from the LiveData maintained by the ViewModel. With DataBinding mechanism,
 * the data from the ViewModel will be displayed on the UI.
 *
 * The ViewModel manages the LiveData, creating, adding data retreved from the serever etc. The interactions
 * between ViewModel and the server was handled by NetworkRepository. The NetworkRepository wrapped
 * Retrofit as the way to send HTTP request and receive response.
 *
 */
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

    /**
     * update the title on the actionbar.
     * @param title a String will be displayed on the actionbar as the title.
     */
    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
