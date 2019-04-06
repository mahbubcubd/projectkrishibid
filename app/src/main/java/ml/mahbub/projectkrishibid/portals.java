package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class portals extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portals);
        Toolbar toolbar = (Toolbar) findViewById(R.id.porta_toolbar);
        toolbar.setTitle("কৃষি  পোর্টাল");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }
        setSupportActionBar(toolbar);
    }
    public void ais(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,ais.class);
        startActivity(m);
    }
    public void krishikotha(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,krishikotha.class);
        startActivity(m);
    }
    public void krishibatayon(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,krishibatayon.class);
        startActivity(m);
    }
}
