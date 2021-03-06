package ml.mahbub.projectkrishibid;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import MQTTHelper.MqttHelper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;
import android.os.Bundle;

public class online_detection extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
    public ImagePicker imagePicker;
    public ImageView im;
    public TextView imginfo;
    MqttHelper mqttclnt;
    public Bitmap image_bit;
    public ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_detection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.online_toolbar);
        toolbar.setTitle(getResources().getString(R.string.online));
        setSupportActionBar(toolbar);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
//            //Call Material Design
        }else {
            /* Call without Material Design */
        }

        im = (ImageView) findViewById(R.id.im);
        imginfo = (TextView) findViewById(R.id.img_info);
        startMqtt();


    }

    public void uploader(View view){
        imagePicker = new ImagePicker();
        imagePicker.withActivity(this) //calling from activity
                .chooseFromGallery(true) //default is true
                .chooseFromCamera(true) //default is true
                .withCompression(true) //default is true
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == ImagePicker.SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            //Add compression listener if withCompression is set to true
            imagePicker.addOnCompressListener(new ImageCompressionListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onCompressed(String filePath) {//filePath of the compressed image
                    //convert to bitmap easily

                    Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
                    int bitWidth = selectedImage.getWidth();
                    int outWidth = 200;
                    float rate = bitWidth/outWidth;
                    int bitHeight = selectedImage.getHeight()/(int) rate;
                    Bitmap resized = Bitmap.createScaledBitmap(selectedImage,outWidth, bitHeight, true);
                    image_bit = resized;
                    im.setImageBitmap(resized);
                    int width = resized.getWidth();
                    int height = resized.getHeight();
                    int sizeBytes = getSizeFromBitmap(resized);
                    byte[] img_ready = processImage.convert(resized);
                    String img_to_string = Base64.encodeToString(img_ready, Base64.DEFAULT);
                    String out = "The image height is " + Integer.toString(height) + " and width is " + Integer.toString(width) +
                            " and the size " + Integer.toString(sizeBytes);
                    Log.v("Image Encoded",img_to_string);
                    imginfo.setText(out);
                }
            });
        }
        //call the method 'getImageFilePath(Intent data)' even if compression is set to false
        String filePath = imagePicker.getImageFilePath(data);
        if (filePath != null) {//filePath will return null if compression is set to true
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            im.setImageBitmap(selectedImage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void send(View view){
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setTitle(" আপনার  ছবিটি  আপলোড  করা  হচ্ছে ...");
        dialog.setMessage(" অনুগ্রহ করে  কিছুক্ষণ অপেক্ষা করুন");
        dialog.setCanceledOnTouchOutside(false);
        String topic = "project/krishibid/detection_feed";
        byte[] img_byte = processImage.convert(image_bit);
        MqttMessage message = new MqttMessage(img_byte);
        Log.v("Mqtt", "Payload Ready");
        dialog.show();
        try {
            dialog.show();
            String img_to_string = Base64.encodeToString(img_byte, Base64.DEFAULT);
            Log.v("Mqtt ImageBase64",img_to_string);
            Log.v("Mqtt", "Sending Data");

            mqttclnt.mqttAndroidClient.publish(topic,message);
            Log.v("Mqtt", "Sending Finished");
            imginfo.setText("Image sent. Waiting for The result");
            dialog.dismiss();
            dialog = new ProgressDialog(this);
            dialog.setIndeterminate(true);
            dialog.setTitle(" আপনার  ছবিটি বিশ্লেষণ করা  হচ্ছে ...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (MqttException e) {
            Log.v("Mqtt Sending Failure", e.getMessage());
            dialog.dismiss();
        }

    }

    public static int getSizeFromBitmap(Bitmap bitmap) {
        int pixels = bitmap.getHeight() * bitmap.getWidth();
        int bytesPerPixel = 0;
        switch (bitmap.getConfig()) {
            case ARGB_8888:
                bytesPerPixel = 4;
                break;
            case RGB_565:
                bytesPerPixel = 2;
                break;
            case ARGB_4444:
                bytesPerPixel = 2;
                break;
            case ALPHA_8:
                bytesPerPixel = 1;
                break;
        }
        return pixels * bytesPerPixel;
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
                Log.v("Debug Received", topic.toString());
                if(received_topic[2].equals("detection_result")){
//                    String toShow = mqttMessage.toString();
                    dialog.dismiss();
                    JSONObject received_json  = new JSONObject(mqttMessage.toString());
                    String detectionResult = received_json.getString("detection_result");
                    String accuracy  = received_json.getString("accuracy");
                    startInfo(detectionResult);
                    //String toShow = "Result : " +detectionResult + " Accuracy: " +accuracy;
                    //imginfo.setText(toShow);
                }else {
                    imginfo.setText(received_topic[2]);
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    public void startInfo(String data){
        Intent info = new Intent(this,disease_info.class);
        info.putExtra("result", data);
        startActivity(info);

    }
}
