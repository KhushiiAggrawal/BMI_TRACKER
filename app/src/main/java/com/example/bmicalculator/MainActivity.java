package com.example.bmicalculator;

import static com.example.bmicalculator.SavingAndLoadingHistory.save_data;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    Uri photoUri,savedImageUri;
    String b,savedImagePath; double height,bmi;
    ArrayList<Person> history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView result;
        EditText edtwgt,hgtinch,hgtft,myname;
        AppCompatButton btn , historybtn, nxtbtn,imgbtn;

        myname=findViewById(R.id.name);
        edtwgt=findViewById(R.id.edtwgt);
        hgtft=findViewById(R.id.hgtft);
        hgtinch=findViewById(R.id.hgtinch);
        result=findViewById(R.id.result);
        btn=findViewById(R.id.btn);
//        nxtbtn=findViewById(R.id.nxtbtn);
        historybtn=findViewById(R.id.historybtn);
        imageView=findViewById(R.id.imageview);
        imgbtn=findViewById(R.id.imgbtn);

        history = SavingAndLoadingHistory.load(this);
        if(history==null)
            history=new ArrayList<>();
        Toast.makeText(MainActivity.this,"Loaded",Toast.LENGTH_SHORT).show();

        imgbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intentimg = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             launcher.launch(intentimg);
         }});

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=""; int weight=0,ft=0,inch=0 ;
                if(myname.getText().toString().equals("") || edtwgt.getText().toString().equals("") || hgtft.getText().toString().equals("") || hgtinch.getText().toString().equals("") || myname.getText().toString().equals(""))
                {
                    Toast.makeText(MainActivity.this,"Please enter a all the values",Toast.LENGTH_SHORT).show();
                }
                else{
                    name = myname.getText().toString();
                    weight = Integer.parseInt(edtwgt.getText().toString());
                    ft = Integer.parseInt(hgtft.getText().toString());
                    inch= Integer.parseInt(hgtinch.getText().toString());
                    height = ((ft*12 + inch)*2.54)/100;
                    bmi = (weight/(height*height));



                    // Assuming you have the saved image path or URI as savedImagePath
                    savedImagePath = saveImageToInternalStorage(photoUri, myname.getText().toString());
                    // Get the new URI for the saved image
                    savedImageUri = getSavedImageUri(savedImagePath);
                    //now here we are saving data in shared preferences in a file named bmi history
                    history.add(new Person(name,String.format("%.2f", bmi),String.format("%.2f", height),Double.toString(weight),savedImageUri.toString()));

                    try {
                        SavingAndLoadingHistory.save_data(getApplicationContext(),history);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(bmi<18)
                    {
                        result.setVisibility(View.VISIBLE);
                        result.setText("Underweight !!");
                    }else if(bmi>25)
                    {
                        result.setVisibility(View.VISIBLE);
                        result.setText("Overweight !!");
                    }
                    else{
                        result.setVisibility(View.VISIBLE);
                        result.setText("Healthy !!");
                    }
                }

            }
        });

//        nxtbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent inext=new Intent(MainActivity.this,NextActivity.class);
//                startActivity(inext);
//            }
//        });

        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inext = new Intent(MainActivity.this,Past.class);
                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable)history);
                inext.putExtra("BUNDLE",args);
                startActivity(inext);
            }
        });
    }
    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null) {
                    photoUri = result.getData().getData();
                    imageView.setImageURI(photoUri);
                }
            });
    private String saveImageToInternalStorage(Uri imageUri, String imageName) {
        String filePath = null;
        try {
            // Open an input stream from the image URI
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            // Create the directory path within internal storage
            File directory = new File(getFilesDir(), "MyImages");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            // Create a file in the custom directory with the person's name as the file name
            File file = new File(directory, imageName + ".jpg");

            // Create an output stream to write the image data to the file
            OutputStream outputStream = new FileOutputStream(file);

            // Copy the image data from the input stream to the output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outputStream.close();
            filePath = file.getAbsolutePath();

            Toast.makeText(MainActivity.this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            // Show a toast message indicating the error in saving the image
            Toast.makeText(MainActivity.this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }return filePath;}
    private Uri getSavedImageUri(String savedImagePath) {
        File savedImageFile = new File(savedImagePath);
        return Uri.fromFile(savedImageFile);}

}