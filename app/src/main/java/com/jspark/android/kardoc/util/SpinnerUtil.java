package com.jspark.android.kardoc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jsPark on 2017. 3. 30..
 */

public class SpinnerUtil {
    private static final Pattern VALID_INTEGER_REGEX = Pattern.compile("^[0-9]$");

    public static boolean validateInteger(String numberString) {
        Matcher matcher = VALID_INTEGER_REGEX.matcher(numberString);
        return matcher.matches();
    }
}
