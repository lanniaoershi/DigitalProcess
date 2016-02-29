package com.example.utopia.digitalprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private TextView mResult;
    private Button btnDel, btnOk;
    private DigitalUtilCallback mCallback;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInput = (EditText) findViewById(R.id.input);
        mResult = (TextView) findViewById(R.id.result);
        btnDel = (Button) findViewById(R.id.btn_del);
        btnOk = (Button) findViewById(R.id.btn_ok);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);


        mCallback = new DigitalUtilCallback() {
            @Override
            public void onProcessDone(String result) {
                super.onProcessDone(result);
//                mResult.setText(result);
            }

            @Override
            public void onProcessDone(ArrayMap<String, String> result) {
                super.onProcessDone(result);

                String number = result.get("RESULT");
                String count = result.get("GROUPS_COUNT");

                mResult.setText(count + "组\n" + number);

            }
        };

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInput.getSelectionStart() > 0  ) {
                    mInput.getEditableText().delete(mInput.getSelectionStart() - 1, mInput.getSelectionStart());
                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DigitalUtil(5, mCallback, false, mProgressBar).processData(mInput.getText().toString());
            }
        });
    }
}
