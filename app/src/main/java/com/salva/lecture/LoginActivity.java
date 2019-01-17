package com.salva.lecture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.salva.lecture.App.App;
import com.salva.lecture.api.RestClient;
import com.salva.lecture.api.models.User;
import com.salva.lecture.api.models.UserAuth;
import com.salva.lecture.helpers.SharedData;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText _emailText;
    private EditText _passwordText;
    private Button _loginButton;
    private ProgressBar _progressBar;
    private RestClient restClient = App.getRestClient();
    private SharedData sharedData = App.getSharedData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final LoginActivity _this = this;
        String token = sharedData.getAccessToken();
        Call<User> res = restClient.getUserService().me(token);
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.errorBody() != null || response.body().getEmail() == null) {
                    startLogin();
                    return;
                }
                sharedData.setUserId(response.body().getId());
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                _this.finish();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                startLogin();
            }
        });

    }

    private void startLogin() {
        setContentView(R.layout.activity_login);
        _emailText = findViewById(R.id.email);
        _passwordText = findViewById(R.id.password);
        _loginButton = findViewById(R.id.button);
        _progressBar = findViewById(R.id.progressBar);
        _progressBar.setVisibility(View.INVISIBLE);
    }

    public void login(View view) {
        final LoginActivity _this = this;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        _progressBar.setVisibility(View.VISIBLE);
        if(!validate(email, password)) {
            onLoginFailed("Login fallito");
            return;
        }

        Call<UserAuth> res = restClient.getUserService().makeLogin(email, password);
        res.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, Response<UserAuth> response) {
                if (response.errorBody() != null) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<UserAuth>() {
                    }.getType();
                    UserAuth errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                    onLoginFailed(errorResponse.getExMessage());
                    return;
                }
                //SETTO IL TOKEN IN LOCALE
                sharedData.setAccessToken(response.body().token);
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                _this.finish();
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Log.e("ERROR", "exception", t);
                onLoginFailed("Login fallito");
            }
        });
        Log.d(TAG, res.toString());
    }

    public boolean validate(String email, String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Inserire una mail valida");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty()) {
            _passwordText.setError("Inserire password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onLoginFailed(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
        _progressBar.setVisibility(View.INVISIBLE);
    }

}
