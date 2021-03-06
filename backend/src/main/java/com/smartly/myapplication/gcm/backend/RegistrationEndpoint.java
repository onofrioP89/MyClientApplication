/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Backend with Google Cloud Messaging" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/GcmEndpoints
*/

package com.smartly.myapplication.gcm.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

import static com.smartly.myapplication.gcm.backend.OfyService.ofy;

/**
 * A registration endpoint class we are exposing for a device's GCM registration id on the backend
 * <p/>
 * For more information, see
 * https://developers.google.com/appengine/docs/java/endpoints/
 * <p/>
 * NOTE: This endpoint does not use any form of authorization or
 * authentication! If this app is deployed, anyone can access this endpoint! If
 * you'd like to add authentication, take a look at the documentation.
 */
@Api(name = "registration", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.gcm.myapplication.smartly.com", ownerName = "backend.gcm.myapplication.smartly.com", packagePath = ""))
public class RegistrationEndpoint {

    private static final Logger log = Logger.getLogger(RegistrationEndpoint.class.getName());

    /**
     * Register a device to the backend
     *
     * @param regId The Google Cloud Messaging registration Id to add
     */
    @ApiMethod(name = "register")
    public void registerDevice(@Named("regId") String regId) {
        if (findRecord(regId) != null) {
            log.info("Device " + regId + " already registered, skipping register");
            return;
        }
        RegistrationRecord record = new RegistrationRecord();
        record.setRegId(regId);
        ofy().save().entity(record).now();
    }


    /**
     * Register a user
     */

    @ApiMethod(name = "registerUser")
    public void registerUser(@Named("regId") String regId, @Named("username") String username, @Named("email") String email, @Named("password") String password) {
        if (findRecord(regId) != null) {
            log.info("Device " + regId + " already registered, registration user in progress...");

            RegistrationUserRecord userRecord = new RegistrationUserRecord();
            userRecord.setRegId(regId);
            userRecord.setUsername(username);
            userRecord.setPassword(password);
            userRecord.setEmail(email);
            ofy().save().entity(userRecord).now();
        } else {
            return;
        }

    }


    private RegistrationRecord findRecord(String regId) {
        return ofy().load().type(RegistrationRecord.class).filter("regId", regId).first().now();
    }


    /**
     * Unregister a device from the backend
     *
     * @param regId The Google Cloud Messaging registration Id to remove

     @ApiMethod(name = "unregister")
     public void unregisterDevice(@Named("regId") String regId) {
     RegistrationRecord record = findRecord(regId);
     if (record == null) {
     log.info("Device " + regId + " not registered, skipping unregister");
     return;
     }
     ofy().delete().entity(record).now();
     }

     /**
      * Return a collection of registered devices
      *
      * @param count The number of devices to list
     * @return a list of Google Cloud Messaging registration Ids

     @ApiMethod(name = "listDevices")
     public CollectionResponse<RegistrationRecord> listDevices(@Named("count") int count) {
     List<RegistrationRecord> records = ofy().load().type(RegistrationRecord.class).limit(count).list();
     return CollectionResponse.<RegistrationRecord>builder().setItems(records).build();
     }

     */


}
