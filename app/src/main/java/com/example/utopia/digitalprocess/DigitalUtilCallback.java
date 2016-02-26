package com.example.utopia.digitalprocess;

import android.util.ArrayMap;

/**
 * Created by utopia on 16-2-26.
 */
public abstract class DigitalUtilCallback {
    public void onProcessDone(String result){};
    public void onProcessDone(ArrayMap<String, String> result){};

}
