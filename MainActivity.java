package com.example.bmicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //We make our vars public so our entire code can use it
    private RadioButton btnMale;
    private RadioButton btnFemale;
    private EditText txtAge;
    private EditText txtFeet;
    private EditText txtInches;
    private EditText txtWeight;
    private Button btnCalc;
    private TextView resultText; //Is a class variable also called Fields

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findViews();
        setupButtonClickListener();

    }
    //We are equaling our views by their id to our own vars
    private void findViews(){
        btnMale = findViewById(R.id.radio_button_male);
        btnFemale = findViewById(R.id.radio_button_female);
        txtAge = findViewById(R.id.edit_text_age);
        txtFeet = findViewById(R.id.edit_text_feet);
        txtInches = findViewById(R.id.edit_text_inches);
        txtWeight = findViewById(R.id.edit_text_weight);
        btnCalc = findViewById(R.id.button_calculate);
        resultText = findViewById(R.id.text_view_result);
    }
    //This is a method that whenever our button calc is clicked it will trigger
    private void setupButtonClickListener() {
        btnCalc.setOnClickListener(new View.OnClickListener() {//
            @Override
            public void onClick(View v) {
                double bmiResult = calculateBMI();//This method will trigger

                String ageTxt =  txtAge.getText().toString();
                int age = Integer.parseInt(ageTxt);

                if (age >= 18) {
                    displayResult(bmiResult);
                }else {
                    displayGuidance(bmiResult);
                }

            }
        });
    }

    private double calculateBMI() {
        //When we grab the text from user input, it will return an editable type so we need to convert to String type so we get a string in return
       String feetTxt =  txtFeet.getText().toString();
       String inchesTxt =  txtInches.getText().toString();
       String weightTxt =  txtWeight.getText().toString();

       //Converts our variables(String type) to integer so we calculate bmi
      int feet = Integer.parseInt(feetTxt);
      int inches = Integer.parseInt(inchesTxt);
      int weight = Integer.parseInt(weightTxt);

        //Gets our total in inches
      int totalInches = (feet * 12) + inches;

        //Height in meters is inches * by 0.0254
      double heightInMeters = totalInches * 0.0254;

        // BMI formula. Weight(kg) divided by height in meters squared
      return weight / (heightInMeters * heightInMeters);
    }

    //Our bmi + the health condition
    private void displayResult(double bmi){
        //Decimal formatter to hundreth's place
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        //We must convert our bmi to a string so it can display to user to the hundreth place
        String bmiTxtResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if (bmi < 18.5){
            //Display Underweight
            fullResultString = bmiTxtResult + " - You are underweight";
        }else if(bmi > 25){
            //Display overweight
            fullResultString = bmiTxtResult + " - You are overweight";
        }else{
            //Display healthy
            fullResultString = bmiTxtResult + " - You are healthy";
        }

        resultText.setText(fullResultString);

    }

    //This will trigger only if our user is younger than 18
    private void displayGuidance(double bmi) {
        DecimalFormat myDecimalFormatter = new DecimalFormat("0.00");
        String bmiTxtResult = myDecimalFormatter.format(bmi);

        String fullResultString;
        if(btnMale.isChecked()){
            //Display male guidance
            fullResultString = bmiTxtResult + " - As you are under 18, please consult with your doctor for the healthy range for boys";
        }else if (btnFemale.isChecked()){
            //Display female guidance
            fullResultString = bmiTxtResult + " - As you are under 18, please consult with your doctor for the healthy range for boys";
        }else{
            //Display general guidance
            fullResultString = bmiTxtResult + " - As you are under 18, please consult with your doctor for the healthy range";
        }
        resultText.setText(fullResultString);
    }
}