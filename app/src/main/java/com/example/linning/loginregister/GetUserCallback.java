package com.example.linning.loginregister;

/**
 * Created by linning on 10/24/15.
 */
interface GetUserCallback {

    /**
     * Invoked when background task is completed
     */

    public abstract void done(User returnedUser);
}
