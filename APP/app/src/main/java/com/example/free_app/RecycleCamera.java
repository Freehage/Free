package com.example.free_app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecycleCamera extends AppCompatActivity {

    // tflite model
    protected Interpreter tflite;
    private MappedByteBuffer tfliteModel;
    private TensorImage inputImageBuffer;
    private  int imageSizeX;
    private  int imageSizeY;
    private  TensorBuffer outputProbabilityBuffer;
    private  TensorProcessor probabilityProcessor;
    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    private Bitmap bitmap;
    private List<String> labels;
    ImageView imageView;
    Uri imageuri;
    Button buclassify;
    TextView classitext;

    // camera
    private static final int REQUEST_IMAGE_CODE = 101;
    private Button btn_picture;
    private ImageView imageView_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclecamera);

        // yolo model
        imageView=(ImageView)findViewById(R.id.image);
        buclassify=(Button)findViewById(R.id.classify);
        classitext=(TextView)findViewById(R.id.classifytext);

        // camera
        //imageView_ = findViewById(R.id.imageView); -> 갤러리 연동 안할꺼면 삭제.
        btn_picture = findViewById(R.id.btn_picture);

        // 카메라 버튼 클릭했을 경우.
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        // imageView 클릭했을 경우.
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),12);
            }
        });

        try{
            tflite=new Interpreter(loadmodelfile(this));
        }catch (Exception e) {
            e.printStackTrace();
        }

        // classify 버튼 클릭했을 경우.
        buclassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        });

        // 재활용 결과 글씨를 클릭했을 경우.
        classitext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), RecycleResult_bottle.class);
//                startActivity(intent);
                // 재활용 종류에 따라 페이지 연결
                if(classitext.getText() == "재활용 결과 페이지") {
                    Intent intent = new Intent(getApplicationContext(), RecycleResult_bottle.class);
                    startActivity(intent);
                }
            }
        });

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
    private MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor=activity.getAssets().openFd("model.tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startoffset = fileDescriptor.getStartOffset();
        long declaredLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);
    }

    private TensorOperator getPreprocessNormalizeOp() {
        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
    }
    private TensorOperator getPostprocessNormalizeOp(){
        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
    }

    // yolo model result 출력.
    private void showresult(){

        try{
            labels = FileUtil.loadLabels(this,"dict.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String, Float> labeledProbability =
                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
                        .getMapWithFloatValue();
        float maxValueInMap =(Collections.max(labeledProbability.values()));

        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
//            if (entry.getValue()==maxValueInMap) {
//                classitext.setText(entry.getKey());
//            }
            // * 재활용 종류에 따라 사용자에게 출력하는 text 설정. -> 추후 수정
            if (entry.getValue()==maxValueInMap) {
                if (entry.getKey()=="vinyl") {
                    classitext.setText("재활용 (비닐)");
                } else if (entry.getKey() == "glass") {
                    classitext.setText("재활용 (유리)");
                } else if (entry.getKey() == "paper") {
                    classitext.setText("재활용 (종이)");
                } else if (entry.getKey() == "paperpack") {
                    classitext.setText("재활용 (종이팩)");
                } else if (entry.getKey() == "can") {
                    classitext.setText("재활용 (캔)");
                } else if (entry.getKey() == "pet") {
                    classitext.setText("재활용 (페트)");
                } else if (entry.getKey() == "plastic") {
                    classitext.setText("재활용 (플라스틱)");
                } else {
                    classitext.setText("재활용 결과 페이지");
                }
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
        }

        // imageView 클릭 후 갤러리에 있는 이미지를 업로드 하였을 경우 -> 갤러리 연동 안할꺼면 삭제.
        if(requestcode==12 && resultcode==RESULT_OK && data!=null) {
            imageuri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


//
//package com.example.free_app;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.res.AssetFileDescriptor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import org.tensorflow.lite.Interpreter;
//import org.tensorflow.lite.support.common.FileUtil;
//import org.tensorflow.lite.support.common.TensorOperator;
//import org.tensorflow.lite.support.common.TensorProcessor;
//import org.tensorflow.lite.support.common.ops.NormalizeOp;
//import org.tensorflow.lite.support.image.ImageProcessor;
//import org.tensorflow.lite.support.image.TensorImage;
//import org.tensorflow.lite.support.image.ops.ResizeOp;
//import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
//import org.tensorflow.lite.support.label.TensorLabel;
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//public class RecycleCamera extends AppCompatActivity {
//
//    // tflite model
//    protected Interpreter tflite;
//    private MappedByteBuffer tfliteModel;
//    private TensorImage inputImageBuffer;
//    private  int imageSizeX;
//    private  int imageSizeY;
//    private  TensorBuffer outputProbabilityBuffer;
//    private  TensorProcessor probabilityProcessor;
//    private static final float IMAGE_MEAN = 0.0f;
//    private static final float IMAGE_STD = 1.0f;
//    private static final float PROBABILITY_MEAN = 0.0f;
//    private static final float PROBABILITY_STD = 255.0f;
//    private Bitmap bitmap;
//    private List<String> labels;
//    ImageView imageView;
//    Uri imageuri;
//    Button buclassify;
//    TextView classitext;
//
//    // camera
//    private static final int REQUEST_IMAGE_CODE = 101;
//    private Button btn_picture;
//    private ImageView imageView_;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recyclecamera);
//
//        // yolo model
//        imageView=(ImageView)findViewById(R.id.image);
//        buclassify=(Button)findViewById(R.id.classify);
//        classitext=(TextView)findViewById(R.id.classifytext);
//
//        // camera
//        //imageView_ = findViewById(R.id.imageView); -> 갤러리 연동 안할꺼면 삭제.
//        btn_picture = findViewById(R.id.btn_picture);
//
//        // 카메라 버튼 클릭했을 경우.
//        btn_picture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                takePicture();
//            }
//        });
//
//        // imageView 클릭했을 경우.
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Picture"),12);
//            }
//        });
//
//        try{
//            tflite=new Interpreter(loadmodelfile(this));
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // classify 버튼 클릭했을 경우.
//        buclassify.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int imageTensorIndex = 0;
//                int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
//                imageSizeY = imageShape[1];
//                imageSizeX = imageShape[2];
//                org.tensorflow.lite.DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();
//
//                int probabilityTensorIndex = 0;
//                int[] probabilityShape =
//                        tflite.getOutputTensor(probabilityTensorIndex).shape(); // {1, NUM_CLASSES}
//                org.tensorflow.lite.DataType probabilityDataType = tflite.getOutputTensor(probabilityTensorIndex).dataType();
//
//                inputImageBuffer = new TensorImage(imageDataType);
//                outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);
//                probabilityProcessor = new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();
//
//                inputImageBuffer = loadImage(bitmap);
//
//                tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());
//                showresult();
//            }
//        });
//
//        // 재활용 결과 글씨를 클릭했을 경우.
//        classitext.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(getApplicationContext(), RecycleResult_bottle.class);
////                startActivity(intent);
//                if(classitext.getText() == "재활용 결과 페이지") {
//                    Intent intent = new Intent(getApplicationContext(), RecycleResult_bottle.class);
//                    startActivity(intent);
//                }
//            }
//        });
//
//    }
//
//    // yolo 객체 인식을 위해 이미지 load 하기.
//    private TensorImage loadImage(final Bitmap bitmap) {
//        // Loads bitmap into a TensorImage.
//        inputImageBuffer.load(bitmap);
//
//        // Creates processor for the TensorImage.
//        int cropSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
//        ImageProcessor imageProcessor =
//                new ImageProcessor.Builder()
//                        .add(new ResizeWithCropOrPadOp(cropSize, cropSize))
//                        .add(new ResizeOp(imageSizeX, imageSizeY, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
//                        .add(getPreprocessNormalizeOp())
//                        .build();
//        return imageProcessor.process(inputImageBuffer);
//    }
//
//    // yolo 모델 load 하기.
//    private MappedByteBuffer loadmodelfile(Activity activity) throws IOException {
//        AssetFileDescriptor fileDescriptor=activity.getAssets().openFd("model.tflite");
//        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
//        FileChannel fileChannel=inputStream.getChannel();
//        long startoffset = fileDescriptor.getStartOffset();
//        long declaredLength=fileDescriptor.getDeclaredLength();
//        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startoffset,declaredLength);
//    }
//
//    private TensorOperator getPreprocessNormalizeOp() {
//        return new NormalizeOp(IMAGE_MEAN, IMAGE_STD);
//    }
//    private TensorOperator getPostprocessNormalizeOp(){
//        return new NormalizeOp(PROBABILITY_MEAN, PROBABILITY_STD);
//    }
//
//    // yolo model result 출력.
//    private void showresult(){
//
//        try{
//            labels = FileUtil.loadLabels(this,"classes.txt");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        Map<String, Float> labeledProbability =
//                new TensorLabel(labels, probabilityProcessor.process(outputProbabilityBuffer))
//                        .getMapWithFloatValue();
//        float maxValueInMap =(Collections.max(labeledProbability.values()));
//
//        for (Map.Entry<String, Float> entry : labeledProbability.entrySet()) {
////            if (entry.getValue()==maxValueInMap) {
////                classitext.setText(entry.getKey());
////            }
//            // * 재활용 종류에 따라 사용자에게 출력하는 text 설정. -> 추후 수정
//            if (entry.getValue()==maxValueInMap) {
//                if (entry.getKey()=="vinyl") {
//                    classitext.setText("재활용 (비닐)");
//                } else if (entry.getKey() == "glass") {
//                    classitext.setText("재활용 (유리)");
//                } else if (entry.getKey() == "paper") {
//                    classitext.setText("재활용 (종이)");
//                } else if (entry.getKey() == "paperpack") {
//                    classitext.setText("재활용 (종이팩)");
//                } else if (entry.getKey() == "can") {
//                    classitext.setText("재활용 (캔)");
//                } else if (entry.getKey() == "pet") {
//                    classitext.setText("재활용 (페트)");
//                } else if (entry.getKey() == "plastic") {
//                    classitext.setText("재활용 (플라스틱)");
//                } else {
//                    classitext.setText("재활용 결과 페이지");
//                }
//            }
//        }
//    }
//
//    // camera 사진 찍기.
//    public void takePicture() {
//        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if(imageTakeIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
//        }
//    }
//
//    // yolo model + camera 결과 가져오기.
//    @Override
//    protected void onActivityResult(int requestcode, int resultcode, @Nullable Intent data) {
//        super.onActivityResult(requestcode, resultcode, data);
//
//        // 카메라로 찍었을 때 imageView.
//        if(requestcode == REQUEST_IMAGE_CODE && resultcode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            //imageView_.setImageBitmap(imageBitmap);
//            imageView.setImageBitmap(imageBitmap);
//            // * classify 수행.
//            bitmap = imageBitmap;
//        }
//
//        // imageView 클릭 후 갤러리에 있는 이미지를 업로드 하였을 경우 -> 갤러리 연동 안할꺼면 삭제.
//        if(requestcode==12 && resultcode==RESULT_OK && data!=null) {
//            imageuri = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri);
//                imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}