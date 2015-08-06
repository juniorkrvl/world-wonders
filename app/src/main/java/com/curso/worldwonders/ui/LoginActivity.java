package com.curso.worldwonders.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.curso.worldwonders.R;
import com.curso.worldwonders.entity.User;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.infrastructure.ProviderTest;
import com.curso.worldwonders.manager.UserManager;

/**
 * Created by Junior on 02/07/2015.
 */
public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    Button btnSignup;
    EditText txtSenha;
    EditText txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        loadUi();
        setListeners();
    }

    private void initialize() {
        SharedPreferences mSharedPreferences = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        boolean logged = mSharedPreferences.getBoolean(Constants.SharedPrefsConsts.KEY_LOGGED, false);
        if (logged){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    protected void loadUi() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSign);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        txtLogin = (EditText) findViewById(R.id.txtLogin);
    }

    protected void loginUser(User user) {
        UserManager userManager = new UserManager(LoginActivity.this);

        ProviderTest test = new ProviderTest(this);
        test.insertTestWonder("Cristo Redentor", "Brazil", "Rio de Janeiro", "");

        if (userManager.login(user)) {
            Toast.makeText(LoginActivity.this, "Logado com sucesso!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "Login ou senha inválidos!", Toast.LENGTH_LONG).show();
        }

    }

    protected void setListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            User user = new User();
                                            if (TextUtils.isEmpty(txtLogin.getText())) {
                                                txtLogin.setError("Campo Obrigatorio");
                                            } else {
                                                user.email = txtLogin.getText().toString();
                                            }
                                            if (TextUtils.isEmpty(txtSenha.getText())) {
                                                txtSenha.setError("Campo Obrigatorio");
                                            } else {
                                                user.password = txtSenha.getText().toString();
                                            }
                                            if (!TextUtils.isEmpty(txtLogin.getText()) && !TextUtils.isEmpty(txtSenha.getText())) {
                                                loginUser(user);
                                            }
                                        }
                                    }
        );

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.email = txtLogin.getText().toString();

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra(Constants.IntentConsts.EXTRA_USER, user);
                startActivityForResult(intent, Constants.IntentConsts.REQUEST_CODE_LOGIN);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.IntentConsts.REQUEST_CODE_LOGIN && data != null) {
                User user = (User) data.getSerializableExtra(Constants.IntentConsts.EXTRA_USER);
                loginUser(user);
                finish();
            }
        }
    }
}
