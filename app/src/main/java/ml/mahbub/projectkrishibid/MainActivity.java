package ml.mahbub.projectkrishibid;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

 public ImageView im;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("কৃষিবিদ");
        setSupportActionBar(toolbar);

        
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            //Call Material Design
            toolbar.setElevation(10f);
        }else {
            /* Call without Material Design */
        }


    }

    public void detection(View view){
        //Navigate the activity to online detection
        Intent i=new Intent(this,online_detection.class);
        startActivity(i);
    }


    public void monitoring(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,nodeList.class);
        startActivity(m);
    }
    public void portals(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,portals.class);
        startActivity(m);
    }

    public void ask(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,questionType.class);
        startActivity(m);
    }
    public void all(View view){
        //Navigate the activity to online detection
        Intent m=new Intent(this,weather.class);
        startActivity(m);
    }





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
