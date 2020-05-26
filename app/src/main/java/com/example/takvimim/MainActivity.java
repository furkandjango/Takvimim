package com.example.takvimim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogControl.DialogControlListener {
    EditText t1;
    CalendarView calendarView;
    String nameup,namead,detup,detadd,calup,caladd;
    int flag=0;
    static AppDatabase appDatabase;
    ListView listView;
    String date;

    TextView tx;
    Table table1,table2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tx=(TextView)findViewById(R.id.textView);


        appDatabase= Room.databaseBuilder(this,AppDatabase.class,"myd").allowMainThreadQueries().build();
        calendarView=(CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date=(month+1)+"-"+dayOfMonth+"-"+year;
                tx.setText("Seçilen Tarih : "+date);
                retrieveDay(view);
            }


        });



        //t1=findViewById(R.id.name);
        listView=findViewById(R.id.listdata);
        registerForContextMenu(listView);
/*
        List<Table> timetable= appDatabase.tableDao().getAll();
        Toast.makeText(getApplicationContext(),timetable.get(0).getTime(),Toast.LENGTH_LONG).show();
        for (int i=0;i<timetable.size();i++){
            String a=timetable.get(i).getTime();
            String[] hm = a.split(":|\\ ");
            for (int j=0;j<hm.length;j++){
                if(hm[j]==null || hm[j].equals("PM") || hm[j].equals("AM"))
                    j++;
                else{
                    Calendar c = Calendar.getInstance();
                   /* System.out.println("j:");
                    System.out.println(Integer.parseInt(hm[j]));
                    System.out.println("j+1:");
                    System.out.println(Integer.parseInt(hm[j+1]));

                    c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hm[j]));
                    c.set(Calendar.MINUTE, Integer.parseInt(hm[j+1]));
                    c.set(Calendar.SECOND, 0);
                    j++;
                }
            }


        }*/




        List<String> list = new ArrayList<String>();
        list.add("Bir etkinlik oluşturmak için + butonuna tıklayın." +
                "\nDaha önce oluşturulmuş etkinlikleri görmek için bir tarih seçin.");
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                add(view);

            }
        });


    }


    public boolean valid() {

       /* name=t1.getText().toString();
        if(TextUtils.isEmpty(date)){
            t1.setError("Enter ");
            t1.requestFocus();
            return false;
        }
        else if(TextUtils.isEmpty(name)){
            t1.setError("Enterr ");
            t1.requestFocus();
            return false;
        }
        else{*/
            return true;
        //}
    }
    public void add(View v){
        DialogControl dialogControl=new DialogControl();
        dialogControl.show(getSupportFragmentManager(),"adding");
    }



    public void delete(View v){
        if(valid()){
            Table table = appDatabase.tableDao().getTable(date);
            if(table!= null){
                appDatabase.tableDao().Deleter(table);
            }
            else{
                Toast.makeText(this,"DEL BOŞ",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void update(View v){
        if(valid()){
            Table table = appDatabase.tableDao().getTable(date);
            if(table!= null){
                table.setMessage(nameup);
                appDatabase.tableDao().Updater(table);
            }
            else{
                Toast.makeText(this,"UP BOŞ",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void retrieveAll(View v){
        List<Table> tb= appDatabase.tableDao().getAll();

        List<String> list = new ArrayList<String>();
        list.add("Oluşturulmuş bir etkinlik yok");


        Table table=new Table();
        table.toString();

        if(tb.size()>0){
            listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
            tx.setText("Tüm Etkinlikler");
        }
        else{
            listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
            //Toast.makeText(this,"GÖRME",Toast.LENGTH_SHORT).show();
        }
    }
    public void retrieveDay(View v){
        List<Table> tb= appDatabase.tableDao().getAllDay(date);
        List<String> list = new ArrayList<String>();
        list.add("Oluşturulmuş bir etkinlik yok");
        if(tb.size()>0){
            listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
            //Toast.makeText(this,"GÖR",Toast.LENGTH_SHORT).show();
        }
        else{
            listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
           // Toast.makeText(this,"GÖRME",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id==R.id.action_seeall){
            List<Table> tb= appDatabase.tableDao().getAll();
            List<String> list = new ArrayList<String>();
            list.add("Oluşturulmuş bir etkinlik yok");
            Table table=new Table();
            table.toString();

            if(tb.size()>0){
                listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
                tx.setText("Tüm Etkinlikler");
//                Toast.makeText(this,"GÖR",Toast.LENGTH_SHORT).show();
            }
            else{
                listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
                //Toast.makeText(this,"GÖRME",Toast.LENGTH_SHORT).show();
            }

        }
        if(id==R.id.action_delall){
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(MainActivity.this);
            alertDialog2.setTitle("Tümünü Sil");
            alertDialog2.setMessage("Tüm etkinlikler silinecek. Emin misiniz?");
            alertDialog2.setIcon(R.drawable.ic_delete_black_24dp);
            alertDialog2.setPositiveButton("EVET",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog

                            appDatabase.tableDao().delAll();
                            View v=new View(getApplicationContext());
                            retrieveAll(v);
                            Toast.makeText(getApplicationContext(),
                                    "Tüm etkinlikler silindi", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
            alertDialog2.setNegativeButton("HAYIR",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            dialog.cancel();
                        }
                    });
                    alertDialog2.show();

        }
        if (id == R.id.action_settings) {

            Intent intentSetting=new Intent(MainActivity.this,SettingActivity.class);
            //intentSetting.putExtra("objPP",userlist);
            //intentSetting.putExtra("ob",id);
            startActivity(intentSetting);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listdata){
            ListView lv=(ListView) v;
            AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Seçiminiz:");
            menu.add(Menu.NONE,0,Menu.NONE,"UPDATE");
            menu.add(Menu.NONE,1,Menu.NONE,"DELETE");
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        List<Table> table=appDatabase.tableDao().getAll();
        List<Table> tab=appDatabase.tableDao().getAllDay(date);

        table1=table.get(info.position);

        if (tab.size()!=0){
            table2=tab.get(info.position);
        }

        switch (item.getItemId()){
            case 0:{
                flag=2;
                DialogControl dialogControl=new DialogControl();
                dialogControl.show(getSupportFragmentManager(),"updating");
            }
            break;
            case 1:{
               // Table tab = appDatabase.tableDao().getTable(date);
                if(tx.getText().equals("Tüm Etkinlikler")){
                    if(table1!= null){
                        appDatabase.tableDao().Deleter(table1);
                        cancelAlarm();
                        List<Table> tb= appDatabase.tableDao().getAllDay(table1.getRemindDate());
                        List<String> list = new ArrayList<String>();
                        list.add("Oluşturulmuş bir etkinlik yok");
                        if(tb.size()>0){
                            listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
                            //Toast.makeText(this,"GÖR",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
                            // Toast.makeText(this,"GÖRME",Toast.LENGTH_SHORT).show();
                        }


                    }
                    else{
                        Toast.makeText(this,"DEL BOŞ",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    if(table2!= null){
                        appDatabase.tableDao().Deleter(table2);
                        cancelAlarm();
                        List<Table> tb= appDatabase.tableDao().getAllDay(table2.getRemindDate());
                        List<String> list = new ArrayList<String>();
                        list.add("Oluşturulmuş bir etkinlik yok");
                        if(tb.size()>0){
                            listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
                            //Toast.makeText(this,"GÖR",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list));
                            // Toast.makeText(this,"GÖRME",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            break;
        }
        return true;
    }

    @Override
    public void applyTexts(String name,String detail,String cal,String time,String adr) {
        if (flag==1){
            Table table = new Table();
            table.setMessage(name);
            table.setAdr(adr);
            table.setTime(time);
            //System.out.println("Değerimiz:"+cal);
            table.setRemindDate(cal);
            table.setDet(detail);
           /* timer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timepicker = new TimePickerFragment();
                    timepicker.show(getSupportFragmentManager(),"time picker");
                    Calendar c = Calendar.getInstance();
                    int hourOfDay=22;
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    int minute=50;
                    c.set(Calendar.MINUTE, minute);
                    c.set(Calendar.SECOND, 0);
                    updateTimeText(c);
                    startAlarm(c);
                }
            });*/
            appDatabase.tableDao().Inserter(table);
            Toast.makeText(this,"EKLENDI",Toast.LENGTH_LONG).show();
            List<Table> tb= appDatabase.tableDao().getAllDay(cal);
            if(tb.size()>0){
                listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
            }
            tx.setText("Eklenen Tarih : "+cal);

        }
        if (flag==2){

            if(tx.getText().equals("Tüm Etkinlikler")){

                if(table1!= null){

                    table1.setMessage(name);
                    table1.setRemindDate(cal);
                    table1.setDet(detail);
                    table1.setTime(time);
                    table1.setAdr(adr);
                    appDatabase.tableDao().Updater(table1);
                    Toast.makeText(this,"GÜNCELLENDİ",Toast.LENGTH_LONG).show();
                    List<Table> tb= appDatabase.tableDao().getAllDay(cal);
                    if(tb.size()>0){
                        listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
                    }
                    tx.setText("Güncellenen Tarih : "+cal);
                }
            }
           else{
                System.out.println("Değerrrrr: mmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
                if(table2!= null){
                    table2.setMessage(name);
                    table2.setRemindDate(cal);
                    table2.setDet(detail);
                    table2.setAdr(adr);
                    table2.setTime(time);
                    appDatabase.tableDao().Updater(table2);
                    Toast.makeText(this,"GÜNCELLENDİ",Toast.LENGTH_LONG).show();
                    List<Table> tb= appDatabase.tableDao().getAllDay(cal);
                    if(tb.size()>0){
                        listView.setAdapter(new ArrayAdapter<Table>(this,android.R.layout.simple_list_item_1,tb));
                    }
                    tx.setText("Güncellenen Tarih : "+cal);
                }
            }

        }

    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmR.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);

    }

  /*  @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        Toast.makeText(this,timeText,Toast.LENGTH_LONG).show();
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmR.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }*/
}
