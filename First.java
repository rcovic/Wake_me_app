package com.example.rcovi.wake_me_app;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class First extends MainActivity {
	Button button_add;
	String str;
	public static SQLiteDatabase db,db1;
	public ArrayList<String> al= new ArrayList<String>();
	ArrayAdapter<String> array_adapter;
	ListView l;
	final int EDIT=1;
	final int DELETE=2;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		button_add=(Button) findViewById(R.id.button1);
		l=(ListView)findViewById(R.id.list);

		try
		{
			
			First.db=openOrCreateDatabase("AlarmDB",Context.MODE_PRIVATE,null);
		    First.db.execSQL("CREATE TABLE IF NOT EXISTS alarm"+"(id VARCHAR,name VARCHAR,time VARCHAR,mainid VARCHAR,rep VARCHAR,vib VARCHAR,musicc VARCHAR);");
			
		    First.db1=openOrCreateDatabase("DeletedAlarmDB",Context.MODE_PRIVATE,null);
		    First.db1.execSQL("CREATE TABLE IF NOT EXISTS del (delid VARCHAR);");
		    Cursor c=db.rawQuery("SELECT * FROM alarm", null);
			if(c.moveToFirst())
			{
				do
				{
					String id=c.getString(1)+" "+c.getString(2)+"   "+c.getString(4);
					al.add(id);
				}while(c.moveToNext());
			}
			array_adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.dat,R.id.textView1,al);
			
			l.setAdapter(array_adapter);
			registerForContextMenu(l);
			
		}
		
		catch(NullPointerException ex)
		{
			Toast.makeText(getApplicationContext(), "Null pointer"+str,Toast.LENGTH_LONG).show();
		}
		button_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i=new Intent(First.this,MainActivity.class);
			startActivity(i);
			
			}
		});

l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Intent editing=new Intent(First.this,Edit.class);
		String p=String.valueOf(i);
		editing.putExtra("loh",p);
		startActivity(editing);
	}
});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.list){
			AdapterView.AdapterContextMenuInfo info=(AdapterContextMenuInfo) menuInfo;
			menu.setHeaderTitle("Alarm Options");
			menu.add(Menu.NONE,EDIT,Menu.NONE,"Edit");
			menu.add(Menu.NONE,DELETE,Menu.NONE,"Delete");
			
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		AdapterView.AdapterContextMenuInfo info=(AdapterContextMenuInfo) item.getMenuInfo();
		
		switch(item.getItemId()){
		case EDIT:Intent jay=new Intent(First.this,Edit.class);
					jay.putExtra("loh",String.valueOf(info.position));
					startActivity(jay);
				   break;
				   
		case DELETE:
			
					Cursor cs=db.rawQuery("SELECT * FROM alarm", null);
					String deleted=l.getItemAtPosition(info.position).toString();
					int mmm=0;
					if(cs.moveToFirst()){
						do{
							String str=cs.getString(1)+" "+cs.getString(2)+"   "+cs.getString(4);
							String ttr=cs.getString(3);
							String check=cs.getString(2);
							if(str.equals(deleted)&&mmm==info.position){

								delete(info.position);
								db.execSQL("DELETE FROM alarm WHERE time='"+check+"' AND mainid='"+ttr+"'");
								db1.execSQL("INSERT INTO del VALUES('"+ttr+"')");

								Toast.makeText(getApplicationContext(), "Alarm scheduled at "+check+" is deleted.", Toast.LENGTH_LONG).show();
								break;
							}mmm++;
						}while(cs.moveToNext());
					}
					
					
		   		   break;
		}
		return super.onContextItemSelected(item);
	}

	private void delete(int pos) {
		// TODO Auto-generated method stub
		al.remove(pos);

		l.invalidate();
		l.setAdapter(array_adapter);
		
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		final AlertDialog dialog = new AlertDialog.Builder(First.this).create();
		dialog.setTitle("Thank You for using Wake Me App!");
		dialog.setMessage("Would you like to exit now?");
		dialog.setButton("YES", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				Intent homeIntent = new Intent(Intent.ACTION_MAIN);
				homeIntent.addCategory( Intent.CATEGORY_HOME );
				homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(homeIntent);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		db.close();
		super.onDestroy();
	}
	
}
