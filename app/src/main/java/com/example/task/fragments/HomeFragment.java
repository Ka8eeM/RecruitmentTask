package com.example.task.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.task.R;
import com.example.task.models.Patient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {


    //widget
    FloatingActionButton fab;
    EditText txtName, txtEmail, txtAge;
    TextView txtResult;
    RadioGroup radioGender;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // vars
    private static final String SHARED = "SHARED_PREF";
    String fullName, email, mAge;
    int age = 0, curNumOfPatient = -1, numOfPatient = -1;
    char gender = '/';

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fab = view.findViewById(R.id.fab);
        txtName = view.findViewById(R.id.txt_full_name);
        txtEmail = view.findViewById(R.id.txt_email);
        txtAge = view.findViewById(R.id.txt_age);
        radioGender = view.findViewById(R.id.radio_group);
        txtResult = view.findViewById(R.id.result);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == 0)
                    gender = 'M';
                else if (checkedId == 1)
                    gender = 'F';
                else
                    gender = 'O';
            }
        });
        preferences = container.getContext().getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        editor = preferences.edit();
        if (savedInstanceState == null) {
            int mx = preferences.getInt("max_number", -1);
            String name = preferences.getString("username", "NOT");
            if (!name.equals("NOT"))
                Toast.makeText(getContext(), "Hi again, " + name, Toast.LENGTH_SHORT).show();
            curNumOfPatient = 0;
            editor.putInt("curNumOfPatient", 0);
            if (mx == -1) {
                numOfPatient = 5;
                editor.putInt("max_number", 5);
                editor.commit();
                editor.apply();
            } else {
                numOfPatient = preferences.getInt("max_number", -1);
            }

        } else {
            numOfPatient = preferences.getInt("max_number", -1);
            curNumOfPatient = preferences.getInt("curNumOfPatient", -1);
            String name = preferences.getString("username", "NOT");
            if (!name.equals("NOT"))
                Toast.makeText(getContext(), "Hi again, " + name, Toast.LENGTH_SHORT).show();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = txtName.getText().toString().trim();
                email = txtEmail.getText().toString().trim();
                mAge = txtAge.getText().toString().trim();
                if (!mAge.isEmpty())
                    age = Integer.parseInt(mAge);
                editor.putString("username", fullName);
                editor.commit();
                editor.apply();
                /*
                 * Create new Patient
                 * */
                Patient patient = new Patient(fullName, email, age, gender);
                curNumOfPatient = preferences.getInt("curNumOfPatient", -1);
                if (fullName.isEmpty() || mAge.isEmpty()) {
                    Toast.makeText(getContext(), "Enter Data", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (curNumOfPatient < numOfPatient) {
                    txtResult.append("{fullname: " + fullName + ", gender: " + gender + ", age: " + age + "}");
                    txtResult.append("\n");
                    curNumOfPatient++;
                    editor.putInt("curNumOfPatient", curNumOfPatient);
                    editor.commit();
                    editor.apply();
                } else {
                    Toast.makeText(getContext(), "Max number of patient reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e("HomeFragment", "HomeFragment");
        int id = item.getItemId();
        switch (id)
        {
            case R.id.clear:
                clearData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.clear_date_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void clearData() {
        editor.putInt("curNumOfPatient", 0);
        editor.commit();
        editor.apply();
        txtResult.setText("");
    }
}