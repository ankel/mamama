package ankel.mamama;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private int allowed;

    private TextView viewRemaining;
    private SharedPreferences preferences;
    private EditText usageAmount;

    private View rootView;

    public MainFragment() {
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

        this.preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        this.allowed = preferences.getInt(MainActivity.ALLOWED, 0);
        long lastRanDate = preferences.getLong(MainActivity.LAST_RAN_DAY, getCurrentDay());
        int perDime = preferences.getInt(MainActivity.PER_DIME, 10);

        this.allowed += (getCurrentDay() - lastRanDate) * perDime;


        this.viewRemaining = (TextView) this.rootView.findViewById(R.id.remainAmountView);
        this.usageAmount = (EditText) this.rootView.findViewById(R.id.usageAmount);
        updatePersistentData();
    }

    private void updatePersistentData() {
        this.preferences.edit()
                .putInt(MainActivity.ALLOWED, this.allowed)
                .putLong(MainActivity.LAST_RAN_DAY, getCurrentDay())
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
