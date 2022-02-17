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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.free_app.after_search.OcrAdapter;
import com.example.free_app.after_search.RecommendAdapter;
import com.example.free_app.database.DatabaseHelper;
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


    private Bitmap bitmap;
    private List<String> labels;
    ImageView imageView;
    TextView result_detail;
    public org.tensorflow.lite.DataType probabilityDataType;
    public float conf;
    public float[][] class_score = new float[6300][7];
    public float[][] preoutput2;
    public float CONF = 0.25f;

    //db에서 값 찾아오기
    private DatabaseHelper mDBHelper;

    ArrayList OCRlist = new ArrayList();
    ArrayList OCRlist2 = new ArrayList();
    ArrayList OCRlist3 = new ArrayList();
    public ArrayList FinalList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterdetect);

        imageView = (ImageView) findViewById(R.id.result_img);
        result_detail = (TextView) findViewById(R.id.result_detail);
        // 사진 찍기.
        takePicture();
        openOrCreateDatabase("FreeAppDB.db", MODE_PRIVATE, null);
        // db.close() -- DO NOT USE THIS

        // ocr
        btn_ocr = findViewById(R.id.btn_ocr);

        // 언어 파일 경로
        datapath = getFilesDir() + "/tesseract/";

        // 트레이닝 데이터가 카피되어 있는지 체크
        checkFile(new File(datapath + "tessdata/"), "kor");
        checkFile(new File(datapath + "tessdata/"), "eng");

        String lang = "kor";

        mTess = new TessBaseAPI();
        mTess.init(datapath, lang);


        mDBHelper = new DatabaseHelper(this);

    }  //여기까지 oncreate

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
        try {
            // 파일이 있을 위치
            String filepath = datapath + "/tessdata/" + lang + ".traineddata";

            // AssetManager에 액세스
            AssetManager assetManager = getAssets();

            // 읽기, 쓰기를 위한 열린 바이트 스트림
            InputStream instream = assetManager.open("tessdata/" + lang + ".traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            // filepath에 의해 지정된 위치에 파일 복사
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
        // 디렉토리가 없으면 디렉토리를 만들고 그 후에 파일 copy
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(lang);
        }
        // 디렉토리가 있지만 파일이 없으면 파일 copy 진행
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/" + lang + ".traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(lang);
            }
        }
    }


    // db에서 인식된 상품과 같은 제품군 찾기.
    private void findObLine() {

        result_detail.getText(); // class name

    }


    // camera 사진 찍기.
    public void takePicture() {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (imageTakeIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CODE);
        }
    }

    // OCR + camera 결과 가져오기.
    @Override
    protected void onActivityResult(int requestcode, int resultcode, @Nullable Intent data) {
        super.onActivityResult(requestcode, resultcode, data);

        // 카메라로 찍었을 때 imageView.
        if (requestcode == REQUEST_IMAGE_CODE && resultcode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //imageView_.setImageBitmap(imageBitmap);
            imageView.setImageBitmap(imageBitmap);

            // 가져와진 사진을 bitmap 으로 추출
            BitmapDrawable d = (BitmapDrawable) ((ImageView) findViewById(R.id.result_img)).getDrawable();
            imageBitmap = d.getBitmap();

            String OCRresult = null;
            mTess.setImage(imageBitmap);

            // 텍스트 추출
            OCRresult = mTess.getUTF8Text();
            TextView OCRTextView = (TextView) findViewById(R.id.result_detail);
            OCRTextView.setText(OCRresult);

            // 특수 문자 제거
            String match = "[^\uAC00-\uD7A30-9a-zA-Z]";
            OCRresult = OCRresult.replaceAll(match, " ");
            Log.e("OCR result", OCRresult);

            // 인식한 text split 후 -> list
            String[] OCRSplit = OCRresult.split(" ");


            // split 한 문자열 모두 출력
            for (int i = 0; i < OCRSplit.length; i++) {
                Log.e("OCRSplit------------", OCRSplit[i]);
                Log.e("   ", "   ");
                OCRlist.add(OCRSplit[i]);

//                    System.out.println(OCRSplit[i]);
            }
            Log.e("OCR------------------------", OCRSplit[0]);
            Log.e("OCRLIST------------------------", OCRlist.get(0).toString());
            Log.e("type-----------------------", OCRSplit.getClass().getName());

            for (int i = 0; i < OCRlist.size(); i++) {
                //ArrayList OCRlist2 = new ArrayList();
                OCRlist2 = mDBHelper.getObjectResult(OCRlist.get(i).toString());
                for (int j = 0; j < OCRlist2.size(); j++) {
                    if (!OCRlist3.contains(OCRlist2.get(j).toString())) {
                        OCRlist3.add(OCRlist2.get(j));
                    }
                }
            }
            Log.e("OCRLIST3333333------------------------", OCRlist3.get(0).toString());
        }
        FinalList = new ArrayList();
        FinalList = mDBHelper.ChageforAdapter(OCRlist3);
        Log.e("ㅡㅡㅡㅡㅡㅡㅡ","?");
        ListView listView = (ListView) findViewById(R.id.recomment_result);
        OcrAdapter ocrAdapter = new OcrAdapter(this, FinalList);
        listView.setAdapter(ocrAdapter);
    }



}