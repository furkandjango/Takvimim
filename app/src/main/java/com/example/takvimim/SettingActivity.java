package com.example.takvimim;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    TextView voi,mod,rep;
    private Button sav;

    Spinner spinner,spinner2,spinner3;

    ArrayList<Table> list=new ArrayList<Table>();
    int id=0;

    public void init(){
        mod=(TextView)findViewById(R.id.textView8);
        voi=(TextView)findViewById(R.id.textView4);
        rep=(TextView)findViewById(R.id.textView6);
        sav=(Button)findViewById(R.id.savebtn);




        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        //sharedPreferences=this.getSharedPreferences("sharedp",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent i = getIntent();
        //list=(ArrayList<Table>)  i.getSerializableExtra("objPP");
       // id=(int)i.getSerializableExtra("ob");
        init();
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
        final String [] mods={"Dark","Light"};
        String [] voice={"Ringtone","Ringtonium"};
        String [] repeat={"EveryDay","EveryWeek"};


        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,mods);
        spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,voice);
        spinner2.setAdapter(adapter2);

        ArrayAdapter<String> adapter3=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,repeat);
        spinner3.setAdapter(adapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sharedPr();
        sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String mm=spinner.getSelectedItem().toString();

                if (mm.equals(mods[0])){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                     finish();
                }
                //Toast.makeText(getApplicationContext(), mm, Toast.LENGTH_LONG).show();
                editor.putString(getString(R.string.mod),mm);
                editor.commit();


                String vv=spinner2.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), mm, Toast.LENGTH_LONG).show();
                editor.putString(getString(R.string.voice),vv);
                editor.commit();

                String rr=spinner3.getSelectedItem().toString();
                //Toast.makeText(getApplicationContext(), mm, Toast.LENGTH_LONG).show();
                editor.putString(getString(R.string.repeat),rr);
                editor.commit();


                Toast.makeText(getApplicationContext(),"Kaydedildi",Toast.LENGTH_SHORT).show();



            }
        });

    }


    private void sharedPr() {

        String mmm=sharedPreferences.getString(getString(R.string.mod), "DARK");
        String m=sharedPreferences.getString(getString(R.string.voice), "Ringtone");
        String mm=sharedPreferences.getString(getString(R.string.repeat), "EveryWeek");





        mod.setText(mmm);
        voi.setText(m);
        rep.setText(mm);
    }
}