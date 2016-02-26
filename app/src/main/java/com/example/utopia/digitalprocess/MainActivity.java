package com.example.utopia.digitalprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private TextView mResult;
    private Button btnDel;
    private DigitalUtilCallback mCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = (EditText) findViewById(R.id.input);
        mResult = (TextView) findViewById(R.id.result);
        btnDel = (Button) findViewById(R.id.btn_del);

        mCallback = new DigitalUtilCallback() {
            @Override
            public void onProcessDone(String result) {
                super.onProcessDone(result);
//                mResult.setText(result);
            }

            @Override
            public void onProcessDone(ArrayMap<String, String> result) {
                super.onProcessDone(result);
                //
                String number = result.get("RESULT");
                String count = result.get("GROUPS_COUNT");
                mResult.setText(count+"组,"+" "+number);
            }
        };

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DigitalUtil(4, mCallback).processData(mInput.getText().toString());
            }
        });
    }
}
