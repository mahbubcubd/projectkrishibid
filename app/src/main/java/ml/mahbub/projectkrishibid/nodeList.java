package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class nodeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_list);
    }

    public void node700(View view){
        //Navigate the activity to online detection
        Intent a=new Intent(this,node_dashboard.class);
        a.putExtra("result", "NODE700");
        startActivity(a);
    }
    public void node800(View view){
        //Navigate the activity to online detection
        Intent a=new Intent(this,node_dashboard.class);
        a.putExtra("result", "NODE800");
        startActivity(a);
    }
    public void overall(View view){
        //Navigate the activity to online detection
        Intent a=new Intent(this,sensorsData.class);
        startActivity(a);
    }
}
