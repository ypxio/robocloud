package com.robocloud.project;

import java.io.FileInputStream;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

public class Uploader extends Activity {
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    setContentView(R.layout.main);
    Intent i = getIntent();
    // getting attached intent data
    String path = i.getStringExtra("path_file");
    uploads(path);
    
    Intent j = new Intent(getApplicationContext(), Dashboard.class);
    startActivity(j);
}

private void uploads(String path)
{
	HttpURLConnection connection = null;
	DataOutputStream outputStream = null;
	DataInputStream inputStream = null;

	String pathToOurFile = path;
	String urlServer = "http://172.21.0.75/project/robocloud/upload.php";
	String lineEnd = "\r\n";
	String twoHyphens = "--";
	String boundary =  "*****";

	int bytesRead, bytesAvailable, bufferSize;
	byte[] buffer;
	int maxBufferSize = 1*1024*1024;

	try
	{
	FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );

	URL url = new URL(urlServer);
	connection = (HttpURLConnection) url.openConnection();

	// Allow Inputs & Outputs
	connection.setDoInput(true);
	connection.setDoOutput(true);
	connection.setUseCaches(false);

	// Enable POST method
	connection.setRequestMethod("POST");

	connection.setRequestProperty("Connection", "Keep-Alive");
	connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

	outputStream = new DataOutputStream( connection.getOutputStream() );
	outputStream.writeBytes(twoHyphens + boundary + lineEnd);
	outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
	outputStream.writeBytes(lineEnd);

	bytesAvailable = fileInputStream.available();
	bufferSize = Math.min(bytesAvailable, maxBufferSize);
	buffer = new byte[bufferSize];

	// Read file
	bytesRead = fileInputStream.read(buffer, 0, bufferSize);

	while (bytesRead > 0)
	{
	outputStream.write(buffer, 0, bufferSize);
	bytesAvailable = fileInputStream.available();
	bufferSize = Math.min(bytesAvailable, maxBufferSize);
	bytesRead = fileInputStream.read(buffer, 0, bufferSize);
	}

	outputStream.writeBytes(lineEnd);
	outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

	// Responses from the server (code and message)
	int serverResponseCode = connection.getResponseCode();
	String serverResponseMessage = connection.getResponseMessage();

	fileInputStream.close();
	outputStream.flush();
	outputStream.close();
	}
	catch (Exception ex)
	{
	//Exception handling
	}
}
private void doFileUpload(){
HttpURLConnection conn =    null;
DataOutputStream dos = null;
DataInputStream inStream = null;    
String exsistingFileName = "/sdcard/def.jpg";

// Is this the place are you doing something wrong.
String lineEnd = "rn";
String twoHyphens = "--";
String boundary =  "*****";

int bytesRead, bytesAvailable, bufferSize;
byte[] buffer;
int maxBufferSize = 1*1024*1024;
String responseFromServer = "";
String urlString = "http://192.168.1.6/index.php";

try
    {
        //------------------ CLIENT REQUEST 
        Log.e("MediaPlayer","Inside second Method");
        FileInputStream fileInputStream = new FileInputStream(new    File(exsistingFileName) );

                                        // open a URL connection to the Servlet
                                        URL url = new URL(urlString);

                                        // Open a HTTP connection to the URL
                                        conn = (HttpURLConnection) url.openConnection();

                                        // Allow Inputs
                                        conn.setDoInput(true);

                                        // Allow Outputs
                                        conn.setDoOutput(true);

                                        // Don't use a cached copy.
                                        conn.setUseCaches(false);

                                        // Use a post method.
                                        conn.setRequestMethod("POST");
                                        conn.setRequestProperty("Connection", "Keep-Alive");
                                        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                                        dos = new DataOutputStream( conn.getOutputStream() );
                                        dos.writeBytes(twoHyphens + boundary + lineEnd);
                                        dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                                                            + exsistingFileName + "\"" + lineEnd);
                                        dos.writeBytes(lineEnd);
                                        Log.e("MediaPlayer","Headers are written");

                                        // create a buffer of maximum size
                                        bytesAvailable = fileInputStream.available();
                                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                        buffer = new byte[bufferSize];

                                        // read file and write it into form...
                                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                                        while (bytesRead > 0){
                                                                dos.write(buffer, 0, bufferSize);
                                                                bytesAvailable = fileInputStream.available();
                                                                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                                                                bytesRead = fileInputStream.read(buffer, 0, bufferSize);                                                
                                        }

                                        // send multipart form data necesssary after file data...
                                        dos.writeBytes(lineEnd);
                                        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                                        // close streams
                                        Log.e("MediaPlayer","File is written");
                                        fileInputStream.close();
                                        dos.flush();
                                        dos.close();

                    }

      catch (MalformedURLException ex)

     {

           Log.e("MediaPlayer", "error: " + ex.getMessage(), ex);

      }



      catch (IOException ioe)

      {

           Log.e("MediaPlayer", "error: " + ioe.getMessage(), ioe);

      }

      //------------------ read the SERVER RESPONSE
      try {
            inStream = new DataInputStream ( conn.getInputStream() );
            String str;

            while (( str = inStream.readLine()) != null)
            {
                 Log.e("MediaPlayer","Server Response"+str);
            }

            inStream.close();
      }

      catch (IOException ioex){
           Log.e("MediaPlayer", "error: " + ioex.getMessage(), ioex);
      }

    }
    }