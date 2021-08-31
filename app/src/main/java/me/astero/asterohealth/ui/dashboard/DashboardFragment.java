package me.astero.asterohealth.ui.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import me.astero.asterohealth.R;
import me.astero.asterohealth.adapters.NotesAdapter;
import me.astero.asterohealth.database.HealthDatabase;
import me.astero.asterohealth.database.dao.HealthDao;
import me.astero.asterohealth.databinding.FragmentDashboardBinding;
import me.astero.asterohealth.xmlparser.XMLParser;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    private FloatingActionButton addNoteButton;

    private RecyclerView notesRecyclerView;
    private NotesAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                addNoteButton = getView().findViewById(R.id.addButton);
                addNoteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            XMLParser.edit("/sdcard/Download/notes.xml", getContext(),"notes");
                            getActivity().recreate();
                            System.out.println("test notes");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });

                try {



                    SharedPreferences sharedPreferences =
                            PreferenceManager.getDefaultSharedPreferences(getContext());

                    if (!sharedPreferences.getBoolean(
                            "first_time", false))
                    {

                        try {
                            XMLParser.createFirstTime(getResources().openRawResource(R.raw.notes), "notes");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    SharedPreferences.Editor sharedPreferencesEditor =
                            PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                    sharedPreferencesEditor.putBoolean(
                            "first_time", true);
                    sharedPreferencesEditor.apply();


                    XMLParser.parse("/sdcard/Download/notes.xml", "notes", getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }




                notesRecyclerView = getView().findViewById(R.id.notesRecyclerView);

                HealthDao healthDao = HealthDatabase.getInstance(getContext()).healthDao();

                System.out.println(healthDao.getAllNotes().size() + " SIZE");
                System.out.println("SIZE");

                adapter = new NotesAdapter(healthDao.getAllNotes());


                notesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                        LinearLayoutManager.VERTICAL));

                notesRecyclerView.setAdapter(adapter);






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