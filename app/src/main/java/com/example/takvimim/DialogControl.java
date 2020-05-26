package com.example.takvimim;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


public class DialogControl extends AppCompatDialogFragment {
    private EditText edtname,edtdet;
    private Button adrbt,timebtn;
    TextView t2;
    CalendarView calendar;
    Calendar c;
    String cal,time,adr;
    private DialogControlListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        final View view=layoutInflater.inflate(R.layout.dialog,null);
        builder.setView(view)
                .setTitle("Gir")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s1=edtname.getText().toString();
                        String s2=edtdet.getText().toString();
                        c = Calendar.getInstance();

                        listener.applyTexts(s1,s2,cal,time,adr);
                    }
                });
        t2=(TextView) view.findViewById(R.id.textView2);
        edtname=view.findViewById(R.id.edtname);
        edtdet=view.findViewById(R.id.edtdet);
        adrbt=view.findViewById(R.id.adrbtn);
        timebtn=view.findViewById(R.id.btntime);
        calendar=(CalendarView)view.findViewById(R.id.calendarView2);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cal=(month+1)+"-"+dayOfMonth+"-"+year;
                t2.setText(cal);
            }
        });

        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity().getApplicationContext(),"BASSS",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getApplicationContext(),Timex.class);
                intent.putExtra("time",time);
                startActivityForResult(intent,1);
            }
        });

        adrbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getActivity().getApplicationContext(),"ADRRR",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity().getApplicationContext(),MapsActivity.class);
                intent.putExtra("adr",adr);
                startActivityForResult(intent,2);


            }
        });

        return builder.create();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = (String)data.getSerializableExtra("result");
                Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_LONG).show();
                time=result;
            }

        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String result = (String)data.getSerializableExtra("res");
                Toast.makeText(getActivity().getApplicationContext(),result,Toast.LENGTH_LONG).show();
                adr=result;

            }

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(DialogControlListener) context;
    }

    public interface DialogControlListener{
        void applyTexts(String name,String detail,String cal,String time,String adr);

    }
}
