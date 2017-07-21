package com.sourcey.LogShare;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Bind;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static String email,savedEmail= "s@g.c" ,password,savedPass="rcle";

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);// program starts here setup first act...
        ButterKnife.bind(this);

        System.out.println("this is the first line in LoinginActivity");
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {//this is the listener that looks at the login Button and starts the app login...
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();//Tis is where the activity is terminated if you click on create new account.....
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {//thsi is the method that dose most of the work...well will...
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }


        _loginButton.setEnabled(false);


        // TODO: Implement your own authentication logic here.


        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        //this sets the temp var that is will compare to the server var
        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        //this is the statmint that is checking if the server and the input var are the same
        if(email.equals(savedEmail)&&password.equals(savedPass)){

            //Im not sure what this dose but is slows the app for 3sec while the "Authenticating" is shown.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        return;
        }else {
            //i need to slow this down by 3 sec......................................................need fix
            onLoginFailed();
            progressDialog.dismiss();
        }

        return;

    }



    protected void onActivityResult(String requestCode, String resultCode, String requesPass,String reaultPass) {// this method start the next Activity MainActivityRunn

        Intent intentRun = new Intent(new Intent(LoginActivity.this,MainActivityRunn.class));
        startActivity(intentRun);
        finish();//this is where this Act will end if the login proses is Successful
                    //MainActivity is still running under this Activity
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {//this starts the method that will start the next Act..
        _loginButton.setEnabled(true);
        onActivityResult(email,savedEmail,password,savedPass);//passing the var on not sure why but i think i will use it to pass to server......?

    }

    public void onLoginFailed() {//if fails
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {//this just checks if its an email or not.. so even this will pass here "k@.c" it just looks for "@" and "."
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
