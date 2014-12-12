package ankel.mamama;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;


public class SettingFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingFragment.
     */
    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    public SettingFragment() {
        // Required empty public constructor
    }

    private TextView perDimeText;
    private SharedPreferences preferences;

    public static final int STEP_SIZE = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        SeekBar perDimeSlider = (SeekBar) this.getView().findViewById(R.id.perDimeSlider);
        perDimeText = (TextView) this.getView().findViewById(R.id.perDimeText);
        preferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);

        perDimeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int) Math.round((double) progress / STEP_SIZE)) * STEP_SIZE;
                perDimeText.setText(String.format("$%d per day", progress));
                seekBar.setProgress(progress);
                preferences.edit().putInt(MainActivity.PER_DIME, progress).apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // no op
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // no op
            }
        });

        int perDime = preferences.getInt(MainActivity.PER_DIME, 10);
        perDimeSlider.setProgress(perDime);
        perDimeText.setText(String.format("$%d per day", perDime));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
