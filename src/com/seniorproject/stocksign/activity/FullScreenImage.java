package com.seniorproject.stocksign.activity;

import com.seniorproject.stocksign.R;
import com.seniorproject.stocksign.fragment.DownloadImageTask;
import com.seniorproject.stocksign.fragment.MarketSectionFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FullScreenImage extends Activity
{
  protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
   setContentView(R.layout.full_image);
   

   
   Intent intent = getIntent();
   long imageId = (Long) intent.getExtras().get(MarketSectionFragment.class.getName());
   System.out.println(imageId+"<--LOOK imageId fullscreen");
   ImageView imageView = (ImageView) findViewById(R.id.fullImage);

   
   //Display the correct fullscreen graph
   if (MarketSectionFragment.getPeriodTextView().getText().equals("1 Day")){
	   new DownloadImageTask(imageView).execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1d&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else if (MarketSectionFragment.getPeriodTextView().getText().equals("5 Day")){
	   new DownloadImageTask(imageView)
       .execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=5d&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");  
	   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else if (MarketSectionFragment.getPeriodTextView().getText().equals("1 Month")){
	   new DownloadImageTask(imageView).execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1m&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else if (MarketSectionFragment.getPeriodTextView().getText().equals("6 Month")){
	   new DownloadImageTask(imageView).execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=6m&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else if (MarketSectionFragment.getPeriodTextView().getText().equals("1 Year")){
	   new DownloadImageTask(imageView).execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=1y&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else if (MarketSectionFragment.getPeriodTextView().getText().equals("5 Year")){
	   new DownloadImageTask(imageView).execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=5y&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else if (MarketSectionFragment.getPeriodTextView().getText().equals("Max to Date")){
	   new DownloadImageTask(imageView).execute("http://chart.finance.yahoo.com/z?s=%5eGSPC&t=my&q=l&l=on&z=l&c=%5EIXIC,%5EDJI&a=v&p=s&lang=en-US&region=US");
   
	   	imageView.setImageResource(R.id.MarketChart);
	    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
   }
   else
	   System.out.println("No chart");

    
            
    
  
            
   }
 }
