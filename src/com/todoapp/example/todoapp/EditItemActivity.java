package com.todoapp.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

	private EditText etItemValue;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		
		String itemText = getIntent().getStringExtra("todo_item");
		
		position = getIntent().getIntExtra("position", -1);
	
		etItemValue = (EditText)findViewById(R.id.etItemValue);
		etItemValue.setText(itemText);
		   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void SaveUpdate(View v){
		
		Intent data = new Intent();
		etItemValue = (EditText)findViewById(R.id.etItemValue);
		
		data.putExtra("todo_item", etItemValue.getText().toString());
		data.putExtra("position", position);

		setResult(RESULT_OK, data); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent

	}
	
}
