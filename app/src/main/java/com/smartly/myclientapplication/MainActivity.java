package com.smartly.myclientapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MainActivity extends Activity {

    public Button ButtonRegister;
    public EditText inputTextUserName, inputTextPassword, inputTextConfirmPassword, inputTextMail;
    public Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        ButtonRegister = (Button) findViewById(R.id.registerButton);
        inputTextUserName = (EditText) findViewById(R.id.usernameText);
        inputTextPassword = (EditText) findViewById(R.id.passwordText);
        inputTextConfirmPassword = (EditText) findViewById(R.id.passwordConfirmText);
        inputTextMail = (EditText) findViewById(R.id.emailText);


        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!inputTextUserName.getText().toString().equals("") &
                        !inputTextPassword.getText().toString().equals("") &
                        !inputTextConfirmPassword.getText().toString().equals("") &
                        inputTextConfirmPassword.getText().toString().equals(inputTextPassword.getText().toString())
                        ) {
                    new GcmRegistrationAsyncTask(ctx, inputTextUserName.getText().toString(), inputTextPassword.getText().toString(), inputTextMail.getText().toString()).execute();
                } else {
                    String msg = "Registration did not complete. Check input text";
                    Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
                    Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
