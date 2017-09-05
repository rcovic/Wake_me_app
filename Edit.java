package com.example.rcovi.wake_me_app;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class Edit extends First {
    public AlarmManager alarmManager1;
    RadioGroup rg1;
    RadioButton rb1,rb2,rb3;
    TimePicker alarmTimePicker1;
    CheckBox c1;
    Cursor cu1;
    Spinner s1;
    Ringtone e1;
    int j;
    MediaPlayer mp1;
    RingtoneManager manager1;
    public long tym1;
    private int dd1=0;
    ArrayList<String> l1=new ArrayList();
    ArrayList<String> m1=new ArrayList();
    ArrayList<String> u1=new ArrayList();
    Button turnOn1,can1;
    public static int id1=0;
    public PendingIntent pi1;
    Context context;
    int song1;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);
        this.context=this;
         j=Integer.parseInt(getIntent().getExtras().getString("loh"));

        can1=(Button)findViewById(R.id.button2);
        alarmManager1=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmTimePicker1=(TimePicker)findViewById(R.id.timePicker1);
        rg1=(RadioGroup)findViewById(R.id.radioGroup1);
        final Calendar calendar1=Calendar.getInstance();
        final Intent e2a=new Intent(Edit.this,AlarmReceiver.class);
        turnOn1=(Button)findViewById(R.id.button1 );
        //can1=(Button)findViewById(R.id.button2);
        c1=(CheckBox) findViewById(R.id.checkBox1);
        s1=(Spinner) findViewById(R.id.spinner1);

        manager1 = new RingtoneManager(this);
        manager1.setType(RingtoneManager.TYPE_RINGTONE);
        cu1=manager1.getCursor();
        while(cu1.moveToNext()){
            String notificationTitle = cu1.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cu1.getString(RingtoneManager.URI_COLUMN_INDEX);
            String notId=cu1.getString(RingtoneManager.ID_COLUMN_INDEX);
            //Uri uri=RingtoneManager.getDefaultUri(checkbok.getPosition());
            m1.add(notificationUri);
            u1.add(notId);
            l1.add(notificationTitle);
        }
        ArrayAdapter<String> array_adapter =new ArrayAdapter<String>(Edit.this,android.R.layout.simple_spinner_item,l1);
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(array_adapter);
        Cursor tp1=db.rawQuery("SELECT * FROM alarm", null);

        if(tp1.moveToFirst())
        {
            int ct=0;
            do
            {
                if(ct==j)
                {
                    rb1=(RadioButton)findViewById(R.id.radio0);
                    rb2=(RadioButton)findViewById(R.id.radio1);
                    rb3=(RadioButton)findViewById(R.id.radio2);
                    String tw=tp1.getString(2);
                    String cw=tp1.getString(5);
                    if(cw.equals("yes"))
                        c1.setChecked(true);
                    else
                        c1.setChecked(false);
                    int hh=Integer.parseInt(tw.substring(0,2));
                    alarmTimePicker1.setCurrentHour(hh);

                    hh=Integer.parseInt(tw.substring(3,5));
                    alarmTimePicker1.setCurrentMinute(hh);
                    if(tp1.getString(4).equalsIgnoreCase("Once"))
                        rb1.setChecked(true);
                    else if(tp1.getString(4).equalsIgnoreCase("daily"))
                        rb2.setChecked(true);
                    else
                        rb3.setChecked(true);

                    int yyy=Integer.parseInt(tp1.getString(6));
                    s1.setSelection(yyy);
                }
                ct++;
            }while(tp1.moveToNext());
        }

        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                // TODO Auto-generated method stub
                int ind=s1.getSelectedItemPosition();
                String mus=m1.get(ind);
                String ic=u1.get(ind);
                Uri songg=Uri.parse(mus+"/"+ic);
                try{
                    Intent ii=new Intent(Edit.this,SerTest.class);
                    ii.putExtra("ringtone-uri", String.valueOf(songg));
                    stopService(ii);
                }
                catch(Exception exx){

                }
                if(dd1!=0){
                    Intent ii=new Intent(Edit.this,SerTest.class);
                    ii.putExtra("ringtone-uri", String.valueOf(songg));
                    startService(ii);

                }
                else
                    dd1++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        can1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kk=new Intent(Edit.this,SerTest.class);
                kk.putExtra("ringtone-uri", String.valueOf(2));
                stopService(kk);
                Intent b2f=new Intent(Edit.this,First.class);
                startActivity(b2f);
            }
        });
        turnOn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                int ind=s1.getSelectedItemPosition();
                String mus=list_uri.get(ind);
                String ic=list_id.get(ind);
                Uri songg=Uri.parse(mus+"/"+ic);


                Intent ii=new Intent(Edit.this,SerTest.class);
                ii.putExtra("ringtone-uri", String.valueOf(songg));
                stopService(ii);


                int sid=radio_group.getCheckedRadioButtonId();
                rb1=(RadioButton)findViewById(sid);
                String r=rb1.getText().toString();
                tym1=System.currentTimeMillis();
                calendar1.set(Calendar.HOUR_OF_DAY,alarmTimePicker1.getCurrentHour());
                calendar1.set(Calendar.MINUTE,alarmTimePicker1.getCurrentMinute());
                calendar1.set(Calendar.SECOND,0);
                long hl=alarmTimePicker1.getCurrentHour();
                long ml=alarmTimePicker1.getCurrentMinute();
                song1=s1.getSelectedItemPosition();
                String h=String.valueOf(hl);
                String m=String.valueOf(ml);
                if(h.length()==1)
                    h="0"+h;
                if(m.length()==1)
                    m="0"+m;

                e2a.putExtra("Extra","alarm on");
                e2a.putExtra("idd", String.valueOf(tym1));
                e2a.putExtra("play", String.valueOf(song1));
                String vibration="no";
                if(c1.isChecked()){
                    e2a.putExtra("vib","yes");
                    vibration="yes";
                }
                else
                    e2a.putExtra("vib","no");
                pi1=PendingIntent.getBroadcast(Edit.this,(int) tym1,e2a,PendingIntent.FLAG_UPDATE_CURRENT);


                long time=System.currentTimeMillis();
                long t=calendar1.getTimeInMillis();
                if(t>time){
                    if(r.equals("Once"))
                        alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),pi1);
                    else if(r.equals("Daily"))
                        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),60*1000*24*60,pi1);
                    else
                        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),60*1000*24*60*7,pi1);
                    Toast.makeText(getApplicationContext(), "Alarm set at "+h+":"+m+".", Toast.LENGTH_LONG).show();
                }
                else{
                    if(r.equals("Once"))
                        alarmManager1.set(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis()+86400000,pi1);
                    else if(r.equals("Daily"))
                        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis()+86400000,60*1000*24*60,pi1);
                    else
                        alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis()+86400000,60*1000*24*60*7,pi1);
                    Toast.makeText(getApplicationContext(), "Alarm set at "+h+":"+m+"  tomorrow.", Toast.LENGTH_LONG).show();
                }



//                First.db.execSQL("INSERT INTO alarm VALUES"+"('"+id+"','alarm','"+h+":"+m+"','"+time+"','"+r+"','"+vibration+"','"+song+"')");
//                Intent back=new Intent(MainActivity.this,First.class);
//                back.putExtra("S",h+":"+m);
//                startActivity(back);
                Cursor cx=db.rawQuery("SELECT * FROM alarm", null);
                String edited=l.getItemAtPosition(j).toString();
                int count=0;
                if(cx.moveToFirst()){
                    do{
                        String str=cx.getString(1)+" "+cx.getString(2)+"   "+cx.getString(4);
                        String ttr=cx.getString(3);
                        String check=cx.getString(2);
                        String idd=cx.getString(3);
                        if(str.equals(edited)&&count==j){
                            //  First.db.execSQL("CREATE TABLE IF NOT EXISTS alarm"+"(id VARCHAR,name VARCHAR,time VARCHAR,mainid VARCHAR,rep VARCHAR,vib VARCHAR,musicc VARCHAR);");
                            //delete(j);
                           // db.execSQL("U FROM alarm WHERE time='"+check+"' AND mainid='"+ttr+"'");
                            db.execSQL("UPDATE alarm SET time='"+h+":"+m+"',mainid='"+tym1+"',rep='"+r+"',vib='"+vibration+"',musicc='"+song1+"' WHERE time='"+check+"' AND mainid='"+ttr+"'");
                            db1.execSQL("INSERT INTO del VALUES('"+idd+"')");
                            Cursor y=db1.rawQuery("SELECT * FROM del WHERE delid='"+idd+"'", null);


                            Toast.makeText(getApplicationContext(), "Alarm scheduled at "+check+" is deleted.", Toast.LENGTH_LONG).show();
                            break;
                        }count++;
                    }while(cx.moveToNext());
                }

                Intent goback=new Intent(Edit.this,First.class);
                startActivity(goback);

            }});
    }

    @Override
    public void onBackPressed() {
        Intent stopsong=new Intent(Edit.this,SerTest.class);
        stopsong.putExtra("ringtone-uri", String.valueOf(2));
        stopService(stopsong);
        //super.onBackPressed();
        Intent tofirst=new Intent(Edit.this,First.class);
        startActivity(tofirst);
    }
}
