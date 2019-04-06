package ml.mahbub.projectkrishibid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;

import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class moisture extends AppCompatActivity {

    ListView list;
    feedAdapter adapter;
    ArrayList<shortfeed> feedlist;
    String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moisture);
        list=(ListView) findViewById(R.id.mosture);
        feedlist = new ArrayList<shortfeed>();


        url ="https://cattronics.com/krishibid/default/mosture.json";


        final ProgressDialog loading = ProgressDialog.show(moisture.this,"Loading Feeds","Please wait",false,false);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url,null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONArray feedjson=response.getJSONArray("feeds");
                                    if(feedjson.length() > 0) {
                                        for (int i = 0; i < feedjson.length(); i++) {

                                            shortfeed f = new shortfeed();
                                            JSONObject feed = feedjson.getJSONObject(i);

                                            f.setDate(feed.getString("ptime"));
                                            f.setNode(feed.getString("node"));
                                            f.setNodevalue(feed.getString("mosture"));

                                            feedlist.add(f);


                                            feedAdapter adapter = new feedAdapter(getApplicationContext(), R.layout.feedlist, feedlist);
                                            list.setAdapter(adapter);
                                            loading.dismiss();
                                        }
                                    }else {
                                        Toast.makeText(moisture.this,"Sorry,no data found",Toast.LENGTH_LONG).show();
                                        loading.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loading.dismiss();
                        Toast.makeText(moisture.this,"An unexpected error occurred",Toast.LENGTH_LONG).show();

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(moisture.this).addTorequestrue(jsObjRequest);





    }






}