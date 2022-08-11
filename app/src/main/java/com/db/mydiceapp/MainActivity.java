package com.db.mydiceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.menu.MenuBuilder;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Spinner dPicker;
    Integer[] arrChooseDiceSide = {4,6,8,10,12,20};
    EditText edCustomDiceSide;
    Button addBtn,btnROnce,btnRTwice;
    TextView first_roll,second_roll;
    ArrayList<String> arrListViewArray;
    ListView lvSavedList;
    LinearLayout lvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindID();
    }
    private void bindID() {
        dPicker = findViewById(R.id.dPicker);
        edCustomDiceSide=findViewById(R.id.edCustomDiceSide);
        addBtn = findViewById(R.id.addBtn);
        btnROnce = findViewById(R.id.btnROnce);
        btnRTwice = findViewById(R.id.btnRTwice);
        first_roll = findViewById(R.id.first_roll);
        second_roll = findViewById(R.id.second_roll);
        lvSavedList= findViewById(R.id.lvSavedList);
        lvMain= findViewById(R.id.lvMain);
        //initate Shared Pref
        SharedPrefManager.init(MainActivity.this);
        setSpinnerData(arrChooseDiceSide);
        arrListViewArray = new ArrayList<>();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding new array element to spinner
                String strNewCustomSide = edCustomDiceSide.getText().toString().trim();
                if(!strNewCustomSide.equalsIgnoreCase(""))
                {
                    Integer arrNewSide[]  = new Integer[arrChooseDiceSide.length +1 ];
                    for (int i = 0; i < arrChooseDiceSide.length; i++) {
                        //Copy element from old array to new array
                        arrNewSide[i] = arrChooseDiceSide[i];
                    }
                    arrNewSide[arrNewSide.length -1] = Integer.parseInt(strNewCustomSide);
                    //sorting array
                    Arrays.sort(arrNewSide);
                    //copy new array to old array
                    arrChooseDiceSide= Arrays.copyOf(arrNewSide, arrNewSide.length);
                    setSpinnerData(arrChooseDiceSide);
                    Toast.makeText(MainActivity.this ,"Your custom Dice " + strNewCustomSide +" is added to dropdown" ,Toast.LENGTH_SHORT).show();
                    edCustomDiceSide.setText("");
                }
            }
        });

