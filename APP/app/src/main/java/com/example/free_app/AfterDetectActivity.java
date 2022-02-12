package com.example.free_app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.jarjarred.org.stringtemplate.v4.Interpreter;

import com.bumptech.glide.util.Util;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.label.TensorLabel;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AfterDetectActivity extends AppCompatActivity {

    // camera
    private static final int REQUEST_IMAGE_CODE = 101;

    // tflite model
    public String MODEL_NAME = "recyclemodel.tflite";
    protected Interpreter tflite;
    private MappedByteBuffer tfliteModel;
    private TensorImage inputImageBuffer;
    private  int imageSizeX;
    private  int imageSizeY;
    private TensorBuffer outputProbabilityBuffer;
    private TensorProcessor probabilityProcessor;
    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    private Bitmap bitmap;
    private List<String> labels;
    ImageView imageView;
    TextView result_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterdetect);

        // yolo model
        imageView=(ImageView)findViewById(R.id.result_img);
        result_detail=(TextView)findViewById(R.id.result_detail);

        // 사진 찍기.
        takePicture();

        openOrCreateDatabase("FreeAppDB.db", MODE_PRIVATE, null);
        // db.close() -- DO NOT USE THIS

        try{
            //tflite=new Interpreter();
            //InputStream fileDescriptor = getAssets().open("classes.txt");
            //Log.e("살려줘", String.valueOf(fileDescriptor));
            tflite = new Interpreter(loadmodelfile(this,MODEL_NAME));
        }catch (Exception e) {
            e.printStackTrace();
            Log.e("DDDDDDDDDDDDDDD","here");
        }
    }

    // yolo 객체 인식을 위해 이미지 load 하기.
    private TensorImage loadImage(final Bitmap bitmap) {
        // Loads bitmap into a TensorImage.
        inputImageBuffer.load(bitmap);

        // Creates processor for the TensorImage.
        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        ImageProcessor imageProcessor =
                new ImageProcessor.Builder()
                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
                        .add(getPreprocessNormalizeOp())
                        .build();
        return imageProcessor.process(inputImageBuffer);
    }


    // yolo 모델 load 하기.
    private MappedByteBuffer loadmodelfile(Activity activity, String modelFilename) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelFilename);
        Log.e("HHHHHHHHHHHHHHHHHHHHHHHH", String.valueOf(fileDescriptor));
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);
    }

    /*private static MappedByteBuffer loadModelFile(AssetManager assetManager, String modelFilename)
            throws IOException{
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelFilename);
        FileInputStream fileInputStream = new FileInputStream(fileDescriptor.getFileDescriptor());

    }*/

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }
    private TensorOperator getPostprocessNormalizeOp(){
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    // db에서 인식된 상품과 같은 제품군 찾기.
    private void findObLine() {

        result_detail.getText(); // class name

    }

    // yolo model result 출력.
    private void showresult() {

        try{
            labels = FileUtil.loadLabels(this,"classes.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Float> labeledProbability =
                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();
        float maxValueInMap =(Collections.max(labeledProbability.values()));

        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
            if (entry.getValue()==maxValueInMap) {
                Log.e("GGGGG",entry.getKey());
                result_detail.setText(entry.getKey());
                // * 추후 class명으로 수정.
                //result_detail.setText("chilsung");
            }
        }
    }

    // camera 사진 찍기.
    public void takePicture() {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
        }
    }

    // yolo model + camera 결과 가져오기.
    @Override
    protected void onActivityResult(int requestcode, int resultcode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        // 카메라로 찍었을 때 imageView.
        if(requestcode == REQUEST_IMAGE_CODE && resultcode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //imageView_.setImageBitmap(imageBitmap);
            imageView.setImageBitmap(imageBitmap);
            // * classify 수행.
            bitmap = imageBitmap;


            // yolo 결과 바로 출력.
            int imageTensorIndex = 0;
            int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
            imageSizeY = imageShape[1];
            imageSizeX = imageShape[2];
            org.tensorflow.lite.DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();

            int probabilityTensorIndex = 0;
            int[] probabilityShape =
                    tflite.getOutputTensor(probabilityTensorIndex).shape(); // {1, NUM_CLASSES}
            org.tensorflow.lite.DataType probabilityDataType = tflite.getOutputTensor(probabilityTensorIndex).dataType();

            inputImageBuffer = new TensorImage(imageDataType);
            outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);
            probabilityProcessor = new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();

            inputImageBuffer = loadImage(bitmap);

            tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());
            showresult();


        }
    }
}