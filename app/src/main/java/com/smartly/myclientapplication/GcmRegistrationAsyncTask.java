package com.smartly.myclientapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.smartly.myapplication.gcm.backend.registration.Registration;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by opetraga on 10/05/2015.
 */


class GcmRegistrationAsyncTask extends AsyncTask<Void, Void, String> {
    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;
    public String regId;

    public String username;
    public String password;
    public String email;

    // TODO: change to your own sender ID to Google Developers Console project number, as per instructions above
    private static final String SENDER_ID = "618051867607";  //Cloud Project Number

    public GcmRegistrationAsyncTask(Context context, String usernameReg, String passwordReg, String emailReg) {
        this.context = context;
        this.username = usernameReg;
        this.password = passwordReg;
        this.email = emailReg;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    .setRootUrl("https://wear-notification-001.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end of optional local run code

            regService = builder.build();
        }

        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            regId = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regId;
            Logger.getLogger("REGISTRATION").log(Level.INFO, msg);

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            regService.register(regId).execute();
            Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
            msg = "Device Registered on Datastore";

            regService.registerUser(regId, username, password, email).execute();
            msg = "User Registered on Datastore";
            Logger.getLogger("REGISTRATION").log(Level.INFO, msg);

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    public boolean UserRegistration(String regId, String username, String password, String email) {
        try {
            if (regService == null) {
                Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                        // otherwise they can be skipped
                        .setRootUrl("https://wear-notification-001.appspot.com/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                    throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end of optional local run code

                regService = builder.build();
            }
            regService.registerUser(regId, username, email, password).execute();
            return true;


        } catch (IOException ex) {
            ex.printStackTrace();
            String msg = "Error: " + ex.getMessage();
            return false;
        }

    }


    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}