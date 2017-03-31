package com.bvrith.snapdirect;

import com.bvrith.snapdirect.common.Constants;
import com.bvrith.snapdirect.utils.FileUtils;
import com.soundcloud.android.crop.Crop;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends ActionBarActivity {
    private static String LOG_TAG = "RegisterActivity";
	
	EditText nameEditText;
	EditText emailEditText;
    EditText phoneEditText;
    TextView smsTextView;
    EditText smsEditText;
    TextView eulaTextView;
    TextView privacyPolcyTextView;
    CircleImageView avatarImage;
	
	RegisterActivityHandler handler;

    Boolean boolAvatarSelected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		nameEditText = (EditText) findViewById(R.id.name);
		emailEditText = (EditText) findViewById(R.id.email);
        phoneEditText = (EditText) findViewById(R.id.phone);
        smsTextView = (TextView) findViewById(R.id.text_sms_code);
        smsEditText = (EditText) findViewById(R.id.sms_code);
        eulaTextView = (TextView) findViewById(R.id.text_eula);
        privacyPolcyTextView = (TextView) findViewById(R.id.text_privacy_policy);
		avatarImage = (CircleImageView) findViewById(R.id.imageViewAvatar);
		
		avatarImage.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	doAvatarPick();
	        }
	    });
		
		handler = new RegisterActivityHandler(this);
        handler.populateView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void doRegister(View view) {
		handler.doRegister();
	}

    public void startDemo(View view) {
        handler.startDemo();
    }
	
	public void doAvatarPick(){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, 
                getResources().getString(R.string.alert_select_image)), 
                Constants.INTENT_PICK_IMAGE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if(requestCode == Constants.INTENT_PICK_IMAGE && data != null && data.getData() != null) {
	        Uri _uri = data.getData();

	        //User had pick an image.
	        Cursor cursor = getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
	        cursor.moveToFirst();

            File tmpFile = new File(getCacheDir(), Constants.FILENAME_AVATAR);
            FileUtils.copyFileFromUri(new File(FileUtils.getRealPathFromURI(this, _uri)), tmpFile);
            cursor.close();
            File dstFile = new File(getFilesDir(), Constants.FILENAME_AVATAR);
            new Crop(Uri.fromFile(tmpFile)).output(Uri.fromFile(dstFile)).asSquare().start(this);
	    } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            avatarImage.setImageResource(0);
            avatarImage.setImageURI(Crop.getOutput(data));
            boolAvatarSelected = true;
            Log.d(LOG_TAG, "Setting avatar URI to " + Crop.getOutput(data));
        }
	    super.onActivityResult(requestCode, resultCode, data);
	}
}
