package com.todoapp.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ToDoActivity extends Activity {

	private ArrayAdapter<String> atodoItems;
	private ArrayList<String> todoItems;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        lvItems = (ListView)findViewById(R.id.lvItems);
        etNewItem = (EditText)findViewById(R.id.etNewItem);
        readItems();
        atodoItems = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(atodoItems);
        setupListViewListener();
    }


	private void setupListViewListener() {
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
				todoItems.remove(pos);
				atodoItems.notifyDataSetChanged();
				writeItems();
				return true;
			}		
		});	
		
		lvItems.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
				  Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
				  // Add "extras" into the bundle for access text position and data
				  i.putExtra("todo_item", todoItems.get(pos)); 
				  i.putExtra("position", pos); 

				  // brings up the second activity
				  startActivityForResult(i, REQUEST_CODE); 	
			}
			
		});
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     // Extract name value from result extras
	     String name = data.getExtras().getString("todo_item");
	     int pos = data.getExtras().getInt("position");
	     // Toast the name to display temporarily on screen
	     
	     try{
	    	 todoItems.remove(pos);
	    	 todoItems.add(pos, name);
	     }catch(Exception e){

	     }
	     atodoItems.notifyDataSetChanged();
	     writeItems();
	  }
	} 
	



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }
	
	public void onAddedItem(View v){
		String itemText = etNewItem.getText().toString();
		atodoItems.add(itemText);
		etNewItem.setText("");
		writeItems();
	}
	
	private void readItems(){
		File filesDir = getFilesDir();	
		File todoFile = new File(filesDir, "todo.txt");
		try{
			todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
		} catch(IOException e){
			todoItems = new ArrayList<String>();
		}
	}
	
	private void writeItems(){
		File filesDir = getFilesDir();	
		File todoFile = new File(filesDir, "todo.txt");
		try{
			FileUtils.writeLines(todoFile, todoItems);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
    
}
