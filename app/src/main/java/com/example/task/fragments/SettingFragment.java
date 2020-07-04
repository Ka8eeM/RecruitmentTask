package com.example.task.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.task.R;

public class SettingFragment extends Fragment {

    public SettingFragment() {

    }


    EditText txtAllowedNumberOfPatient, userName;
    Button btnSave;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView txtCurNum;
    private static final String SHARED = "SHARED_PREF";

    int max;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        txtAllowedNumberOfPatient = view.findViewById(R.id.max_num_patient);
        userName = view.findViewById(R.id.user_name);
        btnSave = view.findViewById(R.id.btn_save);
        txtCurNum = view.findViewById(R.id.cur_add_patient);
        preferences = getContext().getSharedPreferences(SHARED, Context.MODE_PRIVATE);
        editor = preferences.edit();
        int cur = preferences.getInt("curNumOfPatient", -1);
        max = preferences.getInt("max_number", -1);
        if (max != -1)
            txtAllowedNumberOfPatient.setText(max + "");
        if (cur != -1) {
            String temp = txtCurNum.getText().toString();
            txtCurNum.setText(temp + cur);
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = userName.getText().toString();
                String mNumber = txtAllowedNumberOfPatient.getText().toString().trim();
                int mxNumberOfPatient = -1;
                if (!mNumber.isEmpty()) {
                    mxNumberOfPatient = Integer.parseInt(mNumber);
                }
                if (name != null && !name.equals("")) {
                    editor.putString("username", name);
                    editor.commit();
                    editor.apply();
                }
                if (mxNumberOfPatient != -1) {
                    max = mxNumberOfPatient;
                    editor.putInt("max_number", mxNumberOfPatient);
                    editor.commit();
                    editor.apply();
                }
                Toast.makeText(getContext(), "max line " + max + ", usrname " + name, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}