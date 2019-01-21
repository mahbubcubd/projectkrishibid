package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;



public class disease_info extends AppCompatActivity {
    public TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_info);
        Bundle extra = getIntent().getExtras();
        final String diseaseName = extra.getString("result");
        display = (TextView) findViewById(R.id.all_info);
        String disease_description = getResources().getString(R.string.blast);
        display.setText(disease_description);
        Log.v("diseaseName",diseaseName);



    }
}
