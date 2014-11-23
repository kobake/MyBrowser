package com.example.mybrowser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends Activity {

	private WebView mWebView;
	private EditText mEditText;
	
	private static final String INITIAL_WEBSITE = "http://www.google.co.jp";
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mEditText = (EditText)findViewById(R.id.editText1);
		
		mWebView = (WebView)findViewById(R.id.webView1);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.loadUrl(INITIAL_WEBSITE);
		
	}
	
	public void showWebsite(View button){
		String url = mEditText.getText().toString().trim();
		mWebView.loadUrl(url);		
	}
	
	public void clearUrl(View v){
		mEditText.setText("");
	}

	@Override
	public void onBackPressed() {
		if(mWebView.canGoBack()){
			mWebView.goBack();
			return;
		}
		else{
			super.onBackPressed();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWebView.stopLoading();
		mWebView.destroy();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
