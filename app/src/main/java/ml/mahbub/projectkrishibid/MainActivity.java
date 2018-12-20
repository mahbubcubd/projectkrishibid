package ml.mahbub.projectkrishibid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import MQTTHelper.MqttHelper;
import in.mayanknagwanshi.imagepicker.imageCompression.ImageCompressionListener;
import in.mayanknagwanshi.imagepicker.imagePicker.ImagePicker;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
 public ImagePicker imagePicker;
 public ImageView im;
 public TextView imginfo;
 MqttHelper mqttclnt;
 public Bitmap image_bit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        im = (ImageView) findViewById(R.id.im);
        startMqtt();
        imginfo = (TextView) findViewById(R.id.img_info);

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
        String topic = "cropnet/imgdata";

        byte[] img_byte = processImage.convert(image_bit);
        MqttMessage message = new MqttMessage(img_byte);
        try {
            mqttclnt.mqttAndroidClient.publish(topic,message);
        } catch (MqttException e) {
            Log.v("Sending Failure", e.getMessage());
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
                Log.w("Debug", mqttMessage.toString());
                imginfo.setText(mqttMessage.toString());
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
    public native String stringFromJNI();
}
