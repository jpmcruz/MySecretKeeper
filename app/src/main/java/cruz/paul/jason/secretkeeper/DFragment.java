package cruz.paul.jason.secretkeeper;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class DFragment extends DialogFragment implements OnEditorActionListener {

public interface EditNameDialogListener {
	void onFinishEditDialog(String inputText, String string_type);
}

private EditText mEditText;
private Button save;
String argument_type, argument_fileExtension;

public DFragment() {
// Empty constructor required for DialogFragment
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
{
		View view = inflater.inflate(R.layout.dialogfragment, container);
		mEditText = (EditText) view.findViewById(R.id.editText1);
		save = (Button)view.findViewById(R.id.bt_saveChallenge);
		getDialog().setTitle("Save File As...");
		Bundle mArgs = getArguments();
		argument_type = mArgs.getString("type");
		argument_fileExtension = mArgs.getString("file_extension");
		mEditText.setHint(argument_fileExtension);
		// Show soft keyboard automatically
		mEditText.requestFocus();
		mEditText.setOnEditorActionListener(this);

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mEditText.getText().toString().equals(""))
				{
					Toast.makeText(getActivity(), "Enter File Name", Toast.LENGTH_LONG).show();
				}
				else
				{
					if (argument_type.equals("hide"))
					{
						EditNameDialogListener activity = (EditNameDialogListener) getActivity();
					    activity.onFinishEditDialog(mEditText.getText().toString() + argument_fileExtension, "hide");
					    dismiss();
					}
					else
					{
						EditNameDialogListener activity = (EditNameDialogListener) getActivity();
					    activity.onFinishEditDialog(mEditText.getText().toString(), "extract");
					    dismiss();
					}
				}
			}
		});
		return view;
}

@Override
public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
		    // Return input text to activity
		    EditNameDialogListener activity = (EditNameDialogListener) getActivity();
		    activity.onFinishEditDialog(mEditText.getText().toString(), argument_type);
		    this.dismiss();
		    return true;
		}
		return false;
		}
}