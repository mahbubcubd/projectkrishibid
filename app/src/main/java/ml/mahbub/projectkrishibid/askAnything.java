package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class askAnything extends AppCompatActivity {
    public EditText topic;
    public EditText description;
    public Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_anything);

        send = (Button)findViewById(R.id.sendData);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                topic = (EditText)findViewById(R.id.topic);
                description = (EditText)findViewById(R.id.description);
                final String desc = description.getText().toString();
                final String topics = topic.getText().toString();




                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String username = sharedPreferences.getString("username", null);



                RequestQueue queue = Volley.newRequestQueue(askAnything.this);
                String URL = "https://cattronics.com/krishibid/default/post_faq";

                final ProgressDialog loading = ProgressDialog.show(askAnything.this,"Uploading...","Please wait...",false,false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Do something with the response
                                if (response.equals("success")) {
                                    yourQuestion();

                                }
                                Log.v("server",response);
                                loading.dismiss();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error
                                Log.v("Volley Error: ","Dont know: "+username+" "+topics+" "+ "desc" + desc);
                                loading.dismiss();

                                }
                        }){
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("username", username);
                        params.put("topic", topics);
                        params.put("description", desc);
                        return params;
                    }
                };

                MySingleton.getInstance(askAnything.this).addTorequestrue(stringRequest);








            }
        });}


    public void yourQuestion() {
        Intent m = new Intent(this,myQustions.class);
        startActivity(m);
    }

    }


