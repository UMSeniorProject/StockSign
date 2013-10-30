/**
 * 
 */
package com.seniorproject.stocksign.fragment;

import java.io.InputStream;

import com.seniorproject.stocksign.debugging.Debugger;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * @author Sean
 *
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	
	  ImageView bmImage;

	  public DownloadImageTask(ImageView bmImage) {
	      this.bmImage = bmImage;
	  }

	  protected Bitmap doInBackground(String... urls) {
		  
	      String urldisplay = urls[0];
	      
	      Bitmap mIcon11 = null;
	      try {
	        InputStream in = new java.net.URL(urldisplay).openStream();
	        mIcon11 = BitmapFactory.decodeStream(in);
	      } catch (Exception e) {
	          Debugger.error("Error", e.getMessage());
	          e.printStackTrace();
	      }
	      return mIcon11;
	  }

	  protected void onPostExecute(Bitmap result) {
	      bmImage.setImageBitmap(result);
	  }
	}

