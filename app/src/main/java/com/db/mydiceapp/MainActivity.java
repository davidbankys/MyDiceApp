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
    Integer[] arrChooseDiceSide = {4, 6, 8, 10, 12, 20};
    EditText my_customDice;
    Button addBtn, btnROnce, btnRTwice;
    LinearLayout lvMain;
    ArrayList<String> arrListViewArray;
    ListView saved_List;
    TextView first_roll, second_roll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_customDice = findViewById(R.id.my_customDice);
        dPicker = findViewById(R.id.dPicker);
        addBtn = findViewById(R.id.addBtn);
        second_roll = findViewById(R.id.second_roll);
        saved_List = findViewById(R.id.saved_List);
        first_roll = findViewById(R.id.first_roll);
        lvMain = findViewById(R.id.lvMain);
        btnROnce = findViewById(R.id.btnROnce);
        btnRTwice = findViewById(R.id.btnRTwice);

        //initate Shared Pref
        SharedPrefManager.init(MainActivity.this);
        setSpinnerData(arrChooseDiceSide);
        arrListViewArray = new ArrayList<>();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding new array element to spinner
                String strNewCustomSide = my_customDice.getText().toString().trim();
                if (!strNewCustomSide.equalsIgnoreCase("")) {
                    Integer arrNewSide[] = new Integer[arrChooseDiceSide.length + 1];
                    for (int i = 0; i < arrChooseDiceSide.length; i++) {
                        //Copy element from old array to new array
                        arrNewSide[i] = arrChooseDiceSide[i];
                    }
                    arrNewSide[arrNewSide.length - 1] = Integer.parseInt(strNewCustomSide);
                    //sorting array
                    Arrays.sort(arrNewSide);
                    //copy new array to old array
                    arrChooseDiceSide = Arrays.copyOf(arrNewSide, arrNewSide.length);
                    setSpinnerData(arrChooseDiceSide);
                    Toast.makeText(MainActivity.this, "Your custom Dice " + strNewCustomSide + " is added to dropdown", Toast.LENGTH_SHORT).show();
                    my_customDice.setText("");
                }
            }
        });

        btnROnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second_roll.setText("");
                String stID = dPicker.getSelectedItem().toString();
                Dice d = new Dice(Integer.parseInt(stID));
                second_roll.setVisibility(View.GONE);
                diceResultBeforeAndAfter(d, 1);
            }
        });
        btnRTwice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stID = dPicker.getSelectedItem().toString();
                Dice d = new Dice(Integer.parseInt(stID));
                second_roll.setVisibility(View.VISIBLE);
                diceResultBeforeAndAfter(d, 2);
            }
        });


}



    private void setSpinnerData(Integer[] arrChooseSide) {
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_dropdown_item_1line, arrChooseSide);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line); // The drop down view
        dPicker.setAdapter(spinnerArrayAdapter);
    }

    private void diceResultBeforeAndAfter(Dice dice, int i) {
        if (i == 1) {
            dice.roll();
            int sideUpAfterRoll = dice.getSideUp();
            first_roll.setText(String.valueOf(sideUpAfterRoll).toString());
        } else {
            dice.roll();
            int sideUpAfterFroll = dice.getSideUp();
            first_roll.setText(String.valueOf(sideUpAfterFroll).toString());
            dice.roll();
            int sideUpAfterSroll = dice.getSideUp();
            second_roll.setText(String.valueOf(sideUpAfterSroll).toString());
        }
        //save data to shared pref
        saveDataToSharePref();
    }

    private void saveDataToSharePref() {
        String stOne = "First Dice " + first_roll.getText().toString();
        String stTwo = second_roll.getText().toString().trim();
        arrListViewArray.add(stOne);
        if (!stTwo.equalsIgnoreCase("")) {
            String stNew = "Second Dice " + stTwo;
            arrListViewArray.add(stNew);
        }
        //Using Gson i am saving data
        Gson gson = new Gson();
        //Saving string to Gson
        String json = gson.toJson(arrListViewArray);
        //pass object into sharePref
        SharedPrefManager.putString("newSaveObject", json);
        getDataFromSharedPref();
    }

    private void getDataFromSharedPref() {
        //getting data from gson
        Gson gson = new Gson();
        String json = SharedPrefManager.getString("newSaveObject", "");
        if (json.isEmpty()) {
            Toast.makeText(MainActivity.this, "There is some error", Toast.LENGTH_LONG).show();
        } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            //getting array from gson and saved
            List<String> arrNewSavedList = gson.fromJson(json, type);
            //set array to listview
            ArrayAdapter<String> mHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrNewSavedList);
            saved_List.setAdapter(mHistory);
        }
    }

}
