package com.gildaswise.notabook.utils;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Gildaswise on 04/05/2017.
 */

public class ActivityUtils {

    public static void toggleRefreshing(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(!swipeRefreshLayout.isRefreshing());
        swipeRefreshLayout.setEnabled(!swipeRefreshLayout.isRefreshing());
    }

    public static void runOnUiThread(Activity activity, Runnable runnable) {
        activity.runOnUiThread(runnable);
    }

}
