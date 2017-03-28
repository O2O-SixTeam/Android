package com.jspark.android.kardoc.util;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jsPark on 2017. 3. 28..
 */

public class TextUtil {
    public static void alertTextGone(TextView textView) {
        new Handler().postDelayed(() -> textView.setVisibility(View.GONE), 2500);
    }
}
