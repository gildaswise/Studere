package com.gildaswise.notabook;

import android.app.Activity;
import android.app.Application;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.gildaswise.notabook.core.MyObjectBox;
import com.gildaswise.notabook.core.exception.ErrorMessageException;
import com.gildaswise.notabook.utils.DateUtils;
import com.jakewharton.threetenabp.AndroidThreeTen;

import io.objectbox.BoxStore;

//import com.gildaswise.notabook.core.DaoMaster;
//import com.gildaswise.notabook.core.DaoSession;

/**
 * Created by Gildaswise on 04/05/2017.
 */

public class App extends Application {

    private BoxStore storage;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        storage = MyObjectBox.builder().androidContext(App.this).build();
    }

    public BoxStore getStorage() {
        return storage;
    }

    public static BoxStore getStorage(Activity activity) {
        return ((App) activity.getApplication()).getStorage();
    }

    public static void log(String TAG, String message) {
        if (BuildConfig.DEBUG) Log.d(TAG, message);
    }

    public static void startNewThread(Runnable runnable) {
        new Thread(runnable).start();
    }

    public static void handleErrorMessageException(View view, Runnable runnable) {
        try {
            runnable.run();
        } catch (ErrorMessageException exception) {
            Snackbar.make(view, exception.getMessageStringRes(), Snackbar.LENGTH_SHORT).show();
        }
    }

}
