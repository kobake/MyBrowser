package com.example.mybrowser;

import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.util.Patterns;
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
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				ActionBar action = getActionBar();
				action.setSubtitle(mWebView.getTitle());
				mEditText.setText(url);
			}
		});
		mWebView.loadUrl(INITIAL_WEBSITE);
		
	}
	
	public void showWebsite(View button){
		String url = mEditText.getText().toString().trim();
		if(!Patterns.WEB_URL.matcher(url).matches()){
			mEditText.setError("Not a valid URL!");
		}
		else{
			// http://dotinstall.com -> OK
			// dotinstall.com -> OK
			if(!url.startsWith("http://")){
				url = "http://" + url;
			}
			mWebView.loadUrl(url);
		}
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
		int id = item.getItemId();
		if (id == R.id.action_reload) {
			mWebView.reload();
			return true;
		}
		if (id == R.id.action_forward) {
			mWebView.goForward();
			return true;
		}
		if (id == R.id.action_backward) {
			mWebView.goBack();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// メニューの有効無効を切り替える
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem forwardItem = menu.findItem(R.id.action_forward);
		MenuItem backwardItem = menu.findItem(R.id.action_backward);
		forwardItem.setEnabled(mWebView.canGoForward());
		backwardItem.setEnabled(mWebView.canGoBack());
		return super.onPrepareOptionsMenu(menu);
	}
	
}
