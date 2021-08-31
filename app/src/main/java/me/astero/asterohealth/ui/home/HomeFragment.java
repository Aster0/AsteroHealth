package me.astero.asterohealth.ui.home;

import android.Manifest;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import me.astero.asterohealth.R;
import me.astero.asterohealth.adapters.PhysicalAdapter;
import me.astero.asterohealth.adapters.VitalAdapter;
import me.astero.asterohealth.adapters.WaterAdapter;
import me.astero.asterohealth.database.HealthDatabase;
import me.astero.asterohealth.database.dao.HealthDao;
import me.astero.asterohealth.databinding.FragmentHomeBinding;
import me.astero.asterohealth.xmlparser.XMLParser;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private ImageButton themeButton;
    private TextView dateText;

    private boolean darkMode;

    private ConstraintLayout topBar;



    private RecyclerView physicalRecyclerView, waterRecyclerView, vitalRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());

        if (sharedPreferences.getBoolean(
                "dark_mode", false)) {


            darkMode = true;

        }

        int darkModeStyle = R.style.Theme_AsteroHealth;

        if(darkMode)
        {
            darkModeStyle = R.style.Theme_AsteroHealthDark;
        }

        getActivity().getTheme().applyStyle(darkModeStyle, true);


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);


                HealthDatabase.getInstance(getActivity().getApplicationContext());
                // when ready
                dateText = getActivity().findViewById(R.id.dateText);
                String[] currentDate = new Date(System.currentTimeMillis()).toString().split(" ");

                dateText.setText(currentDate[0] + " " + currentDate[2] + " " + currentDate[1]);

                int color = getResources().getColor(R.color.white);

                if(darkMode)
                {
                    color = getResources().getColor(R.color.grey);
                }


                BottomNavigationView bm = getActivity().findViewById(R.id.nav_view);

                bm.setBackgroundColor(color);

                topBar = getActivity().findViewById(R.id.topBar);

                topBar.setBackgroundColor(color);



                themeButton = getActivity().findViewById(R.id.themeButton);


                if(darkMode)
                {
                    themeButton.setImageAlpha(100);
                }
                else
                {
                    themeButton.setImageAlpha(255);
                }

                themeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean darkModeOn = sharedPreferences.getBoolean(
                                "dark_mode", false);



                        SharedPreferences.Editor sharedPreferencesEditor =
                                PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                        sharedPreferencesEditor.putBoolean(
                                "dark_mode", !darkModeOn);
                        sharedPreferencesEditor.apply();

                        getActivity().recreate();




                    }
                });







                HealthDao healthDao = HealthDatabase.getInstance(getContext()).healthDao();
                physicalRecyclerView = getView().findViewById(R.id.physicalRecyclerView);

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);


                PhysicalAdapter adapter = new PhysicalAdapter(healthDao.getAllPhysicalActivities());
                physicalRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

                physicalRecyclerView.setAdapter(adapter);

                waterRecyclerView = getView().findViewById(R.id.waterRecyclerView);



                WaterAdapter waterAdapter = new WaterAdapter(healthDao.getAllWaterIntake());
                waterRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

                waterRecyclerView.setAdapter(waterAdapter);



                vitalRecyclerView = getView().findViewById(R.id.vitalRecyclerView);



                VitalAdapter vitalAdapter = new VitalAdapter(healthDao.getAllVital());
                vitalRecyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext(),
                        LinearLayoutManager.HORIZONTAL, false));

                vitalRecyclerView.setAdapter(vitalAdapter);
                System.out.println(physicalRecyclerView + " TEXT!");









            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}