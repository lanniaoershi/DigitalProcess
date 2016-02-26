package com.example.utopia.digitalprocess;

import android.os.AsyncTask;
import android.util.ArrayMap;



/**
 * Created by utopia on 16-2-26.
 */
public class DigitalUtil {
    private int mDigits;
    private DigitalUtilCallback mCallback;
    private int mGroupsCount;

    public DigitalUtil(int digits, DigitalUtilCallback callBack){
        mDigits = digits;
        mCallback = callBack;
    }

    public void processData(final String data) {

        new AsyncTask<String, Integer, ArrayMap<String, String>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mGroupsCount = 0;
            }

            @Override
            protected ArrayMap<String, String> doInBackground(String... params) {
                ArrayMap<String, String> dataMap = new ArrayMap<>();
                String result;
                String digits = decode2digits(params[0]);
                result = packet(digits);
                dataMap.put("GROUPS_COUNT", String.valueOf(mGroupsCount));
                dataMap.put("RESULT",result);


                return dataMap;
            }

            @Override
            protected void onPostExecute(ArrayMap<String, String> map) {
                super.onPostExecute(map);
                mCallback.onProcessDone(map);
            }
        }.execute(data);
    }

    /**
     * @param data raw string data
     * @return digits only
     */
    private  synchronized String decode2digits(String data) {
        StringBuilder result = new StringBuilder();
        int length = data.length();
        char temp;
        for (int i = 0; i < length; i++) {
            temp = data.charAt(i);
            if (Character.isDigit(temp)) {
                switch (temp) {
                    case '0':
                        result.append(0);
                        continue;
                    case '1':
                        result.append(1);
                        continue;
                    case '2':
                        result.append(2);
                        continue;
                    case '3':
                        result.append(3);
                        continue;
                    case '4':
                        result.append(4);
                        continue;
                    case '5':
                        result.append(5);
                        continue;
                    case '6':
                        result.append(6);
                        continue;
                    case '7':
                        result.append(7);
                        continue;
                    case '8':
                        result.append(8);
                        continue;
                    case '9':
                        result.append(9);
                        continue;
                }
            }
        }
        return result.toString();
    }

    private synchronized String packet(String digits) {

        StringBuilder temp = new StringBuilder(digits);
        StringBuilder result = new StringBuilder();
        int length = temp.length();
        int i = 0;
        String tail = "";
        if (length%mDigits != 0) {
            tail = temp.substring((length - length % mDigits), length).toString();
            temp = temp.delete((length - length % mDigits), length);
        }
        while (temp.length() > 0) {
            result.append(temp.charAt(0));
            temp.deleteCharAt(0);
            i++;
            if (i == mDigits) {
                result.append(",");
                mGroupsCount++;
                i = 0;
            }
        }
        result.append(tail);

        return result.toString();
    }
}
