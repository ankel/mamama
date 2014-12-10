package ankel.mamama;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ALLOWED = "allowed";
        private int allowed;

        private final int perDime = 10;

        private static final String LAST_RAN_DAY = "last-ran-day";
        private long lastRanDate;

        private TextView viewRemaining;
        private SharedPreferences preferences;
        private EditText usageAmount;

        private View rootView;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.rootView = inflater.inflate(R.layout.fragment_main, container, false);

            rootView.findViewById(R.id.subtractButton).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subtractUsage(v);
                }
            });

            return rootView;
        }

        @Override
        public void onResume()
        {
            super.onResume();

            this.preferences = this.getActivity().getPreferences(MODE_PRIVATE);

            this.allowed = preferences.getInt(ALLOWED, 0);
            this.lastRanDate = preferences.getLong(LAST_RAN_DAY, getCurrentDay());

            this.allowed += (getCurrentDay() - lastRanDate) * this.perDime;


            this.viewRemaining = (TextView) this.rootView.findViewById(R.id.remainAmountView);
            this.usageAmount = (EditText) this.rootView.findViewById(R.id.usageAmount);
            updatePersistentData();
        }

        private void updatePersistentData() {
            this.preferences.edit()
                    .putInt(ALLOWED, this.allowed)
                    .putLong(LAST_RAN_DAY, getCurrentDay())
                    .apply();

            this.viewRemaining.setText("$" + Integer.toString(this.allowed));
        }

        public void subtractUsage(View view)
        {
                this.allowed -= Integer.parseInt(this.usageAmount.getText().toString());
                updatePersistentData();
        }

        private static final long MILIS_IN_DAY = 1000 * 60 * 60 * 24;

        private long getCurrentDay() {
            return System.currentTimeMillis() / MILIS_IN_DAY;
        }
    }

}
