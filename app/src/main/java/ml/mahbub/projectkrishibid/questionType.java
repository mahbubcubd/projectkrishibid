package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class questionType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_type);
    }

    public void feed(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,myQustions.class);
        startActivity(m);
    }
    public void askQuestion(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,askAnything.class);
        startActivity(m);
    }
}
