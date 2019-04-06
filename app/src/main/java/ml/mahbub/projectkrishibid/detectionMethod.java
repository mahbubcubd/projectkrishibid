package ml.mahbub.projectkrishibid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class detectionMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection_method);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("রোগ সনাক্তকরণ");
        setSupportActionBar(toolbar);


    }

    public void onlineDetection(View view){
        //Navigate the activity to online detection
        Intent b=new Intent(this,online_detection.class);
        startActivity(b);
    }
    public void offlineDetection(View view){
        //Navigate the activity to online detection
        Intent c=new Intent(this,online_detection.class);
        startActivity(c);
    }

}
