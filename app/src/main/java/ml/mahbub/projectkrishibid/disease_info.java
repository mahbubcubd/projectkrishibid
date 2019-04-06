package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;


public class disease_info extends AppCompatActivity {
    public TextView disease_description;
    public ImageView disease_image;
    public MediaPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_info);
        Bundle extra = getIntent().getExtras();
        final String diseaseName = extra.getString("result");
        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        toolbar.setTitle(diseaseName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(10f);
        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(" রোগ সনাক্ত  করা যায়নি");
        disease_description = (TextView) findViewById(R.id.disease_description);
        TextView disease_symptomp = (TextView) findViewById(R.id.disease_symptomp);
        TextView disease_remedy = (TextView) findViewById(R.id.disease_remedy);
        disease_image = (ImageView) findViewById(R.id.disease_image);
        audioPlayer = new MediaPlayer();

        if (diseaseName.equals("blast")){
            String disease_desc = getResources().getString(R.string.blast);
            String symptomp = getResources().getString(R.string.blast_symptopm);
            String remedy = getResources().getString(R.string.blast_remedy);
            disease_image.setImageResource(R.mipmap.blast);
            disease_description.setText(disease_desc);
            collapsingToolbar.setTitle("ব্লাস্ট");
            disease_symptomp.setText(symptomp);
            disease_remedy.setText(remedy);
            audioPlayer = MediaPlayer.create(this, R.raw.blast);
            audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            audioPlayer.setLooping(false);
            audioPlayer.start();
        }else {
            audioPlayer = MediaPlayer.create(this, R.raw.no_detection);
            audioPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            audioPlayer.setLooping(false);
            audioPlayer.start();
        }




    }

    public void audioControl(View view){
        if(audioPlayer.isPlaying()){
            audioPlayer.pause();
        } else {
            audioPlayer.start();
        }
    }
    public void onBackPressed() {
        if(audioPlayer.isPlaying()){
            audioPlayer.stop();
        }
    }
}
