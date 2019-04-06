package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class sensorsData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_data);
    }
    public void rowTemp(View view) {
        //Navigate the activity to online detection
        Intent b = new Intent(this, temperatureLogger.class);
        startActivity(b);
    }
    public void humidityLogger(View view) {
        //Navigate the activity to online detection
        Intent b = new Intent(this, Humidity.class);
        startActivity(b);
    }

    public void moisture(View view) {
        //Navigate the activity to online detection
        Intent b = new Intent(this, moisture.class);
        startActivity(b);
    }
}
