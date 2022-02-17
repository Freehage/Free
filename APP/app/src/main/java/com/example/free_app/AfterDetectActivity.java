package com.example.free_app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.googlecode.tesseract.android.TessBaseAPI;

import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.common.TensorProcessor;
import org.tensorflow.lite.support.common.ops.NormalizeOp;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class AfterDetectActivity extends AppCompatActivity {

    // camera
    private static final int REQUEST_IMAGE_CODE = 101;

    // OCR API
    Bitmap imageBitmap;
    private TessBaseAPI mTess;
    String datapath = "";
    Button btn_ocr;
    private String imageFilePath;
    private Uri p_Uri;

    // tflite model
    public String MODEL_NAME = "best.tflite";
    protected Interpreter tflite;
    private MappedByteBuffer tfliteModel;
    private TensorImage inputImageBuffer;
    private  int imageSizeX;
    private  int imageSizeY;
    private TensorBuffer outputProbabilityBuffer;
    private Integer output;
    private TensorProcessor probabilityProcessor;
    private static final float IMAGE_MEAN = 0.0f;
    private static final float IMAGE_STD = 1.0f;
    private static final float PROBABILITY_MEAN = 0.0f;
    private static final float PROBABILITY_STD = 255.0f;
    private Bitmap bitmap;
    private List<String> labels;
    ImageView imageView;
    TextView result_detail;
    public org.tensorflow.lite.DataType probabilityDataType;
    public float conf;
    public float[][] class_score = new float[6300][7];
    public float[][] preoutput2;
    public float CONF = 0.25f;


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
            tflite = new Interpreter(loadmodelfile(this,MODEL_NAME));
        }catch (Exception e) {
            e.printStackTrace();
        }

        // ocr
        btn_ocr = findViewById(R.id.btn_ocr);

        // 언어 파일 경로
        datapath = getFilesDir() + "/tesseract/";

        // 트레이닝데이터가 카피되어 있는지 체크
        checkFile(new File(datapath + "tessdata/"), "kor");
        checkFile(new File(datapath + "tessdata/"), "eng");

        String lang = "kor+eng";

        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);

        // 텍스트 추출 버튼
        btn_ocr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // 가져와진 사진을 bitmap으로 추출
                BitmapDrawable d = (BitmapDrawable)((ImageView) findViewById(R.id.result_img)).getDrawable();
                imageBitmap = d.getBitmap();

                String OCRresult = null;
                mTess.setImage(imageBitmap);

                //텍스트 추출
                OCRresult = mTess.getUTF8Text();
                TextView OCRTextView = (TextView) findViewById(R.id.result_detail);
                OCRTextView.setText(OCRresult);

                Log.e("OCR------------------------f", OCRresult);
            }
        });
    }

    // ocr
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    // ocr
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    // ocr
    // 장치에 파일 복사
    private void copyFiles(String lang) {
        try{
            //파일이 있을 위치
            String filepath = datapath + "/tessdata/"+lang+".traineddata";

            //AssetManager에 액세스
            AssetManager assetManager = getAssets();

            //읽기/쓰기를 위한 열린 바이트 스트림
            InputStream instream = assetManager.open("tessdata/"+lang+".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //filepath에 의해 지정된 위치에 파일 복사
            byte[] buffer = new byte[1024];
            int read;

            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ocr
    private void checkFile(File dir, String lang) {
        //디렉토리가 없으면 디렉토리를 만들고 그후에 파일을 카피
        if(!dir.exists()&& dir.mkdirs()) {
            copyFiles(lang);
        }
        //디렉토리가 있지만 파일이 없으면 파일카피 진행
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/"+lang+".traineddata";
            File datafile = new File(datafilepath);
            if(!datafile.exists()) {
                copyFiles(lang);
            }
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
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
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

    // db에서 인식된 상품과 같은 제품군 찾기.
    private void findObLine() {

        result_detail.getText(); // class name

    }

    // yolo model result 출력.
    private void showresult() {
        try {
            String result = "";
            labels = FileUtil.loadLabels(this, "classes.txt");
            TensorBuffer preprocess = probabilityProcessor.process(outputProbabilityBuffer);
            float[] preoutput = preprocess.getFloatArray();
            Log.e("ㅇㅇㅇ", String.valueOf(preprocess.getShape()[1])+" "+String.valueOf(preprocess.getShape()[2]));
            /*for(int i=0; i<preoutput.length;i++){
                Log.e("ㅖㅇㅇㅇㄻㅁㅁㅇㄴ",String.valueOf(i)+' '+ String.valueOf(preoutput[i]));
            }*/


            preoutput2 = arr2arr2(preoutput, 12);
            NMS(preoutput2);
            float[] count_arr = new float[7];
            float max_bbox_conf = preoutput2[4][0];
            for(int i = 0; i< class_score.length; i++){
                int class_label = max(class_score[i]);
                float bbox_conf = preoutput2[4][i];

                if(class_score[i][class_label] > 0 & bbox_conf > max_bbox_conf){
                    max_bbox_conf = bbox_conf;
                    count_arr[class_label] += 1;
                    result = labels.get(class_label);
                    Log.e("class_label", String.valueOf(preoutput2[0][i])+' '+String.valueOf(class_label)+' '+String.valueOf(max_bbox_conf));
                }
            }
            Log.e("정답", String.valueOf(count_arr[0])+' '+String.valueOf(count_arr[1])+
                    ' '+String.valueOf(count_arr[2])+' '+String.valueOf(count_arr[3])+' '+
                    String.valueOf(count_arr[4])+' '+String.valueOf(count_arr[5])+
                    ' '+String.valueOf(count_arr[6]));
            result_detail.setText(result);

        } catch (Exception e) {
            e.printStackTrace();
            result_detail.setText("아직...");
        }
    }

    private void NMS(float[][] arr) {
        for(int i=5; i< 12; i++){
            int maxIndex = max(arr[i]);
            class_score[maxIndex][i-5] = arr[i][maxIndex];
            float[] max_Bbox = {arr[i-5][maxIndex],arr[i-4][maxIndex],arr[i-3][maxIndex],arr[i-2][maxIndex]};
            ArrayList<Integer> next = Iou(max_Bbox,arr[i-5],arr[i-4],arr[i-3],arr[i-2],i,maxIndex);
            while(next != null){
                Integer next_num = next.get(0);
                float[] next_Bbox = {arr[i-5][next_num],arr[i-4][next_num],arr[i-3][next_num],arr[i-2][next_num]};
                next = Iou(next_Bbox,arr[i-5],arr[i-4],arr[i-3],arr[i-2],i,next_num);
            }
        }

    }

    private ArrayList<Integer> Iou(float[] max_Bbox, float[] x1, float[] y1, float[] x2, float[] y2, int k, int max_index) {
        float maxbox_area = (max_Bbox[2] - max_Bbox[0] + 1) * (max_Bbox[3] - max_Bbox[1] + 1);
        ArrayList<Integer> next_max = null;
        for(int i=0; i< x1.length; i++){
            if(preoutput2[k][i] > CONF){
                if(i != max_index ){
                    float box2_area = (x2[i] - x1[i] + 1) * (y2[i] - y1[i] + 1);
                    float inter_x1 = Math.max(max_Bbox[0],x1[i]);
                    float inter_y1 = Math.max(max_Bbox[1],y1[i]);
                    float inter_x2 = Math.max(max_Bbox[2],x2[i]);
                    float inter_y2 = Math.max(max_Bbox[3],y2[i]);

                    float w = Math.max(0,inter_x2-inter_x1 +1);
                    float h = Math.max(0,inter_y2-inter_y1+1);

                    float iou = (w * h) / (maxbox_area + box2_area);

                    if(iou > 0.49){
                        class_score[i][k-5] = 0;
                    }
                    else{
                        class_score[i][k-5] = preoutput2[k][i];
                        Log.e("OTHER", String.valueOf(class_score[i][k-5]));
                        next_max.add(i);
                    }
                }
            }
        }
        return next_max;
    }

    private int max(float[] arr) {
        int maxIndex = 0;
        float max = 0.0f;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private float[][] arr2arr2(float[] arr, int num) {
        float[][] result = new float[num][arr.length/num];
        int k = 0;
        for(int i=0; i< arr.length/num; i++){
            for(int j=4; j<num;j++){
                if(j == 4){
                    result[j-4][i] = arr[k+j-4] - arr[k+j-2];
                    result[j-3][i] = arr[k+j-3] - arr[k+j-1];
                    result[j-2][i] = arr[k+j-4] - arr[k+j-2];
                    result[j-1][i] = arr[k+j-3] - arr[k+j-1];
                    conf = arr[k+j];
                    result[j][i] = conf;
                }
                else{
                    result[j][i] = arr[k+j] * conf;
                }
            }
            k += 12;
        }
        return result;
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
//            bitmap = imageBitmap;

            // yolo 결과 바로 출력.
//            int imageTensorIndex = 0;
//            int[] imageShape = tflite.getInputTensor(imageTensorIndex).shape(); // {1, height, width, 3}
//            imageSizeY = imageShape[1];
//            imageSizeX = imageShape[2];
//            org.tensorflow.lite.DataType imageDataType = tflite.getInputTensor(imageTensorIndex).dataType();
//
//            int probabilityTensorIndex = 0;
//            tflite.getOutputTensorCount();
//            int[] probabilityShape =
//                    tflite.getOutputTensor(probabilityTensorIndex).shape();
//
//            probabilityDataType = tflite.getOutputTensor(probabilityTensorIndex).dataType();
//            inputImageBuffer = new TensorImage(imageDataType);
//            outputProbabilityBuffer = TensorBuffer.createFixedSize(probabilityShape, probabilityDataType);
//
//            probabilityProcessor = new TensorProcessor.Builder().add(getPostprocessNormalizeOp()).build();
//
//            inputImageBuffer = loadImage(bitmap);
//
//            tflite.run(inputImageBuffer.getBuffer(),outputProbabilityBuffer.getBuffer().rewind());
//            showresult();


        }
    }


}