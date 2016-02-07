package com.noobswe.kphai.helloworld;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    int sum = 0;

    TextView tvResult;
    Button btnFinished;
    EditText editTextResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        sum = intent.getIntExtra("result", 0);

        Bundle bundle = intent.getBundleExtra("cBundle");
        int x = bundle.getInt("x");
        int y = bundle.getInt("y");
        int z = bundle.getInt("z");

        CoordinateSerializable c2 = (CoordinateSerializable)
                intent.getSerializableExtra("cSerializable");

        CoordinateParcelable c3 = intent.getParcelableExtra("cParcelable");

        initInstances();

    }

    private void initInstances() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText("Result = " + sum);
        editTextResult = (EditText) findViewById(R.id.ediTextResult);
        btnFinished = (Button) findViewById(R.id.btnFinished);
        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", editTextResult.getText().toString());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        //
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
