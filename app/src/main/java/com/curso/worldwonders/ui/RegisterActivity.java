package com.curso.worldwonders.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.curso.worldwonders.R;
import com.curso.worldwonders.entity.User;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.manager.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;


public class RegisterActivity extends ActionBarActivity {

    private Spinner spnCountry;
    private AutoCompleteTextView autoTxtLanguages;
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private RadioGroup rdGroup;
    private Button btnSend;
    private ArrayAdapter<String> autoCompleteAdapter;
    private Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resources = getResources();
        setContentView(R.layout.activity_register);
        loadUi();
        setListeners();
        initialize();
    }

    protected void initialize() {
        setSpinnerAdapter();

        autoCompleteAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<String>());
        autoTxtLanguages.setAdapter(autoCompleteAdapter);

        setAutoCompleteOptions(spnCountry.getSelectedItemPosition());

        User user = (User) getIntent().getSerializableExtra(Constants.IntentConsts.EXTRA_USER);
        txtEmail.setText(user.email);
    }

    protected void loadUi() {
        spnCountry = (Spinner) findViewById(R.id.register_spn_country);
        autoTxtLanguages = (AutoCompleteTextView) findViewById(R.id.register_auto_txt_language);
        txtName = (EditText) findViewById(R.id.register_txt_name);
        txtEmail = (EditText) findViewById(R.id.register_txt_email);
        txtPassword = (EditText) findViewById(R.id.register_txt_password);
        txtConfirmPassword = (EditText) findViewById(R.id.register_txt_password_confirmation);
        rdGroup = (RadioGroup) findViewById(R.id.register_rd_gender);
        btnSend = (Button) findViewById(R.id.register_btn_send);
    }

    protected void setListeners() {
        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAutoCompleteOptions(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

    }

    private void validate() {
        // Validate empty texts

        User user = new User();

        boolean error = false;
        if (TextUtils.isEmpty(txtName.getText())) {
            error = true;
            txtName.setError(resources.getString(R.string.message_name));
        } else {
            user.name = txtName.getText().toString();
        }

        if (TextUtils.isEmpty(txtEmail.getText())) {
            error = true;
            txtEmail.setError(resources.getString(R.string.message_email));
        } else {
            user.email = txtEmail.getText().toString();
        }
        if (TextUtils.isEmpty(txtPassword.getText())) {
            error = true;
            txtPassword.setError(resources.getString(R.string.message_password));
        } else {
            if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
                error = true;
                txtConfirmPassword.setError(resources.getString(R.string.message_password_confirmation));
            } else {
                user.password = txtPassword.getText().toString();
            }
        }
        if (TextUtils.isEmpty(autoTxtLanguages.getText())) {
            error = true;
            autoTxtLanguages.setError(resources.getString(R.string.message_language));
        } else {
            user.language = autoTxtLanguages.getText().toString();
        }

        if (!error) {
            Toast.makeText(this, resources.getString(R.string.message_success), Toast.LENGTH_LONG).show();

            UserManager userManager = new UserManager(this);
            userManager.register(user);

            Intent intent = new Intent();
            intent.putExtra(Constants.IntentConsts.EXTRA_USER, user);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }

    }

    private void setAutoCompleteOptions(int position) {

        List<String> languages = new ArrayList<String>();
        if (position == 0) {
            languages = Arrays.asList(resources.getStringArray(R.array.languages_brazil_items));
        } else if (position == 1) {
            languages = Arrays.asList(resources.getStringArray(R.array.languages_canada_items));
        } else {
            languages = Arrays.asList(resources.getStringArray(R.array.languages_french_items));
        }
        autoCompleteAdapter.clear();
        autoCompleteAdapter.addAll(languages);
        autoCompleteAdapter.notifyDataSetChanged();
    }

    private void setSpinnerAdapter() {
        String[] names = resources.getStringArray(R.array.spinner_items);
        ArrayAdapter<String> countries = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, Arrays.asList(names));
        spnCountry.setAdapter(countries);
    }



}
