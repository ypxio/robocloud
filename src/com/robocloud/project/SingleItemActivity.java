package com.robocloud.project;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleItemActivity extends Activity{
	
	// button to show progress dialog
	Button download;
	
	// Progress Dialog
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
//    private Button startBtn;
    private ProgressDialog mProgressDialog;
//	private ProgressDialog pDialog;
	ImageView my_image;
	String filename;
	// Progress dialog type (0 - for Horizontal progress bar)
	public static final int progress_bar_type = 0; 
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.single_item);
        
        TextView item_name = (TextView) findViewById(R.id.item_name);
        download = (Button) findViewById(R.id.download);
        
        Intent i = getIntent();
        // getting attached intent data
        this.filename = i.getStringExtra("name_item");
        final String url = i.getStringExtra("url_item");
        String url_page = i.getStringExtra("urlpage_item");
        // displaying selected product name
        item_name.setText(filename);
        
        TextView tv = (TextView) findViewById(R.id.link);
        tv.setText(Html.fromHtml("<a style=\"text-decoration:none;\" href="+ url +">Download</a>"));
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        
        TextView xt = (TextView) findViewById(R.id.extra);
        xt.setText(Html.fromHtml("or <a style=\"text-decoration:none;\" href="+ url_page +">view</a> in browser"));
        xt.setMovementMethod(LinkMovementMethod.getInstance());
        
        download.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// starting new Async Task
				startDownload(url);
//				Toast.makeText(SingleItemActivity.this, url, Toast.LENGTH_SHORT).show();
			}
		});
        
	}
	
	private void startDownload(String url) {
//        String url = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";
        new DownloadFileAsync().execute(url);
    }
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
		case DIALOG_DOWNLOAD_PROGRESS:
			mProgressDialog = new ProgressDialog(this);
			mProgressDialog.setMessage("Downloading file..");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			return mProgressDialog;
		default:
			return null;
        }
    }

class DownloadFileAsync extends AsyncTask<String, String, String> {
   
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog(DIALOG_DOWNLOAD_PROGRESS);
	}

	@Override
	protected String doInBackground(String... aurl) {
		int count;

	try {

	URL url = new URL(aurl[0]);
	URLConnection conexion = url.openConnection();
	conexion.connect();

	int lenghtOfFile = conexion.getContentLength();
	Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

	InputStream input = new BufferedInputStream(url.openStream());
//	String filename = conexion.
	OutputStream output = new FileOutputStream("/sdcard/"+filename);

	byte data[] = new byte[1024];

	long total = 0;

		while ((count = input.read(data)) != -1) {
			total += count;
			publishProgress(""+(int)((total*100)/lenghtOfFile));
			output.write(data, 0, count);
		}

		output.flush();
		output.close();
		input.close();
	} catch (Exception e) {}
	return null;

	}
	protected void onProgressUpdate(String... progress) {
		 Log.d("ANDRO_ASYNC",progress[0]);
		 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	}

	@Override
	protected void onPostExecute(String unused) {
		dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
	}
}
}
