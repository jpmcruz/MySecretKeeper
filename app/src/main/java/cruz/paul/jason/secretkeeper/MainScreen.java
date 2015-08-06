package cruz.paul.jason.secretkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainScreen extends AppCompatActivity {

	Button bt_Hide, bt_Extract;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bt_Hide = (Button)findViewById(R.id.bt_Hide);
		bt_Extract = (Button)findViewById(R.id.bt_Extract);

		bt_Hide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			     Intent intent1 = new Intent("cruz.jason.hide");
			     startActivity(intent1);
			}
		});
		
		bt_Extract.setOnClickListener(new View.OnClickListener() {		
			@Override
			public void onClick(View v) {
			     Intent intent2 = new Intent("cruz.jason.extract");
			     startActivity(intent2);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		    case R.id.action_instructions:
				AlertDialog alertInstructions = new AlertDialog.Builder(MainScreen.this).create();
				alertInstructions.setTitle("Instructions");
				alertInstructions.setMessage("Choose Between Hide and Extract Options");
				alertInstructions.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
				alertInstructions.show();
		      break;

		    case R.id.action_about:
		        View messageView = getLayoutInflater().inflate(R.layout.dialog_about, null, false);
		        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
		        int defaultColor = textView.getTextColors().getDefaultColor();
		        textView.setTextColor(defaultColor);
		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setIcon(R.drawable.ic_action_about);
		        builder.setTitle("About This App");
		        builder.setView(messageView);
		        builder.create();
		        builder.show();
			      break;
			    default:
			      break;
		    }
		    return true;
	}
}
