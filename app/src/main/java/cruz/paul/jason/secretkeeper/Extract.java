package cruz.paul.jason.secretkeeper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cruz.paul.jason.secretkeeper.DFragment.EditNameDialogListener;

public class Extract extends FragmentActivity implements EditNameDialogListener {
	

	FragmentManager fm;
	StringBuilder sbResult;
	
	//Extract
	Button bt_BrowseStegoFile, bt_Extract;
	EditText et_StegoFile;
	private static final int REQUEST_PATH3 = 3;
	String stegoFileName, stegoPath, stegoContent, stegoComplete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_extract);
		
//		ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Hide");
//        actionBar.setIcon(R.drawable.directory_icon);
		fm = getSupportFragmentManager();
	
		//Extract
		bt_BrowseStegoFile = (Button)findViewById(R.id.bt_BrowseStegoFile);
		et_StegoFile = (EditText)findViewById(R.id.et_StegoFile);
		bt_Extract = (Button)findViewById(R.id.bt_Extract);
		
		bt_BrowseStegoFile.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				 Intent intent3 = new Intent("cruz.jason.file_browser");
			     startActivityForResult(intent3,REQUEST_PATH3);
			}
		});
		
		bt_Extract.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Bundle args = new Bundle();
				
			//	String extension = stegoFileName.substring(origFileName.lastIndexOf("."));
				
				args.putString("type", "extract");
				DFragment editNameDialog = new DFragment();
				editNameDialog.setArguments(args);
		    	editNameDialog.show(fm, "file_saver");
			}
		});
	}
		
	public void onFinishEditDialog(String inputText, String argument_type) {
		//---SD Card Storage---
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File (sdCard.getAbsolutePath() +
		"/MySecretKeeper");
		directory.mkdirs();
		File file3 = new File(directory, inputText);
	
			
			try {
				RandomAccessFile file = new RandomAccessFile(stegoPath + "/" + stegoFileName, "rw");
				file.seek(file.length() - 4);
				long pointer = file.getFilePointer();
				BufferedOutputStream bos = null;
				//---display file saved message---

			    int bytesRead = 0;
			    int totalRead = 0;
			    int bytesSecretRead = 0;
			    int secretRead = 0;
			    byte[] buffer = new byte[4]; // 128k buffer 
			    while(totalRead < 4) { // go on reading while total bytes read is
			                                        // less than 1mb
			         bytesRead = file.read(buffer);
			         totalRead += bytesRead;
			         
			       }
			    int secret_size = buffer[0] << 24 | (buffer[1] & 0xff) << 16 | (buffer[2] & 0xff) << 8
			            | (buffer[3] & 0xff);
				
			    file.seek(file.length() - 4 - Long.valueOf(secret_size));
			    pointer = file.getFilePointer();
			    
			    FileOutputStream out = new FileOutputStream(file3);
			    byte[] secretbuffer = new byte[secret_size];
			    while(totalRead < secret_size) { // go on reading while total bytes read is
                    // less than 1mb
			    	bytesSecretRead = file.read(secretbuffer);
			    	totalRead += bytesSecretRead;
			    	out.write(secretbuffer, 0, bytesSecretRead);
			    }

			    file.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
    
		
		     Toast.makeText(getBaseContext(), "File saved successfully!",
		     Toast.LENGTH_LONG).show();
	 }

	
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data){
		     // See which child activity is calling us back.
		  
			 if (requestCode == REQUEST_PATH3){
		         if (resultCode == RESULT_OK) {
	                 stegoFileName = data.getStringExtra("GetFileName");
	               //  stegoContent = data.getStringExtra("Content");
	                 stegoPath = data.getStringExtra("GetPath");
	                 stegoComplete = stegoFileName;
	                 et_StegoFile.setText(stegoComplete);
	             }	             
			 }     
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.extract, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		    // action with ID action_refresh was selected
		    case R.id.action_instructions:
		      Toast.makeText(this, "Instructions selected", Toast.LENGTH_SHORT)
		          .show();
		      break;
		    // action with ID action_settings was selected
		    case R.id.action_settings:
		      Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT)
		          .show();
		      break;

		    case R.id.action_about:
			      Toast.makeText(this, "About selected", Toast.LENGTH_SHORT)
			          .show();
			      break;
			      
	        case android.R.id.home:
	        	finish();
	            return true;
	            
			    default:
			      break;
		    }

		    return true;
	}
}
