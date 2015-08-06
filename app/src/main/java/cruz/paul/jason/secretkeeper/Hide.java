package cruz.paul.jason.secretkeeper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cruz.paul.jason.secretkeeper.DFragment.EditNameDialogListener;

public class Hide extends FragmentActivity implements EditNameDialogListener {
	
	Button bt_BrowseOrigFile, bt_BrowseSecretFile, bt_Hide;
	EditText et_OrigFile, et_SecretFile;
	static TextView tv_TextHex;
	private static final int REQUEST_PATH = 1;
	private static final int REQUEST_PATH2 = 2;
	String origFileName, origPath, origContent, origComplete;
	String secretFileName, secretPath, secretContent, secretComplete;
	android.support.v4.app.FragmentManager fm;
	StringBuilder sbResult;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hide);
		fm = getSupportFragmentManager();
		bt_BrowseOrigFile = (Button)findViewById(R.id.bt_BrowseOrigFile);
		bt_BrowseSecretFile = (Button)findViewById(R.id.bt_BrowseSecretFile);
		bt_Hide = (Button)findViewById(R.id.bt_Hide);
		et_OrigFile = (EditText)findViewById(R.id.et_OrigFile);
		et_SecretFile = (EditText)findViewById(R.id.et_SecretFile);
		tv_TextHex = (TextView)findViewById(R.id.textHex);

//		Deprecated!
//		ActionBar actionBar = getActionBar();
//
//        // Enabling Up / Back navigation
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Hide");
//        actionBar.setIcon(R.drawable.directory_up);
        
		bt_BrowseOrigFile.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
			     Intent intent1 = new Intent("cruz.jason.file_browser");
			     startActivityForResult(intent1,REQUEST_PATH);
			}
		});
		
		bt_BrowseSecretFile.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
			     Intent intent2 = new Intent("cruz.jason.file_browser");
			     startActivityForResult(intent2,REQUEST_PATH2);
			}
		});
		
		bt_Hide.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
				Bundle args = new Bundle();
				String extension = origFileName.substring(origFileName.lastIndexOf("."));
				args.putString("type", "hide");
				args.putString ("file_extension", extension);
				DFragment editNameDialog = new DFragment();
				editNameDialog.setArguments(args);
		    	editNameDialog.show(fm, "file_saver");
			}
		});
	}
		
	public void onFinishEditDialog(String inputText, String argument_type) {
		//---SD Card Storage---
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File (sdCard.getAbsolutePath() +	"/MySecretKeeper");
		directory.mkdirs();
		File file3 = new File(directory, inputText);
		tv_TextHex.setText(argument_type);
		
			File file1 = new File(origPath + "/" + origFileName);
			File file2 = new File(secretPath + "/" + secretFileName);
			// convert File to byte[]

			try {
				  FileInputStream inStream = new FileInputStream(file1);
				  FileInputStream inStream2 = new FileInputStream(file2);
				    FileOutputStream outStream = new FileOutputStream(file3);
				    BufferedOutputStream bos = null;
				    FileChannel inChannel = inStream.getChannel();
				    FileChannel inChannel2 = inStream2.getChannel();
				    FileChannel outChannel = outStream.getChannel();
				    inChannel.transferTo(0, inChannel.size(), outChannel);
				    inChannel2.transferTo(0, inChannel2.size(), outChannel);
				    
				    inStream.close();
				    inStream2.close();
				    outStream.close();
				    
				    byte[] b = new byte[4];
				    for (int i = 0; i < 4; i++) {
				        int offset = (b.length - 1 - i) * 8;
				        b[i] = (byte) ((file2.length() >>> offset) & 0xFF);
				    }
				    
				    FileOutputStream fos = new FileOutputStream(file3, true);
				    bos = new BufferedOutputStream(fos); 
				    bos.write(b);
			          bos.flush();
			          bos.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     Toast.makeText(getBaseContext(), "File saved successfully!",
		     Toast.LENGTH_LONG).show();
	 }

	
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data){
			     if (requestCode == REQUEST_PATH){
		             if (resultCode == RESULT_OK) {
		                 origFileName = data.getStringExtra("GetFileName");
		            //     origContent = data.getStringExtra("Content");
		                 origPath = data.getStringExtra("GetPath");
		                 origComplete = origFileName;
		                 et_OrigFile.setText(origComplete);
		             }
		     }
		     if (requestCode == REQUEST_PATH2){
	             if (resultCode == RESULT_OK) {
	                 secretFileName = data.getStringExtra("GetFileName");
	                // secretContent = data.getStringExtra("Content");
	                 secretPath = data.getStringExtra("GetPath");
	                 secretComplete = secretFileName;
	                 et_SecretFile.setText(secretComplete);
	             }
		     } 
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hide, menu);
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
		    }
		    return true;
	}
}
