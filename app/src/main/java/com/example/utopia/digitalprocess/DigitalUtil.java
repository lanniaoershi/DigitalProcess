package com.example.utopia.digitalprocess;

import android.os.AsyncTask;
import android.util.ArrayMap;


/**
 * Created by utopia on 16-2-26.
 */
public class DigitalUtil {
    private int mDigitsCount;
    private DigitalUtilCallback mCallback;
    private int mGroupsCount;
    private boolean mReservedExtraDigits = false;


    public DigitalUtil(int digitsCount, DigitalUtilCallback callback, boolean reservedExtraDigits) {
        mDigitsCount = digitsCount;
        mCallback = callback;
        mReservedExtraDigits = reservedExtraDigits;
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
                dataMap.put("RESULT", result);


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
    private synchronized String decode2digits(String data) {
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
        int packetSize = 0;
        String tail = "";
        if (length % mDigitsCount != 0) {

            tail = temp.substring((length - length % mDigitsCount), length).toString();

            temp = temp.delete((length - length % mDigitsCount), length);
        }
        while (temp.length() > 0) {
            result.append(temp.charAt(0));
            temp.deleteCharAt(0);
            packetSize++;
            if (packetSize == mDigitsCount) {
                result.append(",");
                mGroupsCount++;
                packetSize = 0;
            }
        }
        if (mReservedExtraDigits) {
            result.append(tail);
        }

        return result.toString();
    }
}
