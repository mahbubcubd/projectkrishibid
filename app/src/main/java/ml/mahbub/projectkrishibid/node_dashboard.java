package ml.mahbub.projectkrishibid;

import MQTTHelper.MqttHelper;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class node_dashboard extends AppCompatActivity {
    MqttHelper mqttclnt;
    TextView ltemp;
    TextView lhum;
    TextView lamb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_dashboard);
        ltemp = (TextView) findViewById(R.id.ltemp);
        lhum = (TextView) findViewById(R.id.lhum);
        lamb  = (TextView) findViewById(R.id.lmois);
        startMqtt();

    }


    private void startMqtt() {
        mqttclnt = new MqttHelper(getApplicationContext());

        mqttclnt.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                String[] received_topic= topic.toString().split("/",5);
                if(received_topic[2].equals("livenode")){
                    JSONObject received_json  = new JSONObject(mqttMessage.toString());
                    String soil_temp = received_json.getString("soil temperature");
                    String rela_hum  = received_json.getString("relative humidity");
                    String amb_temp  = received_json.getString("ambient temperature");
                    ltemp.setText(soil_temp);
                    lamb.setText(amb_temp);
                    lhum.setText(rela_hum);
                    Log.v("MQTT live"," Payload "+mqttMessage.toString());

                    //String toShow = "Result : " +detectionResult + " Accuracy: " +accuracy;
                    //imginfo.setText(toShow);
                }else {
                    Log.v("MQTT debug","topic "+received_topic[2]+" paload "+mqttMessage.toString());
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }
}
