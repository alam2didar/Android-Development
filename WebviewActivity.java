package edu.usna.cs.presidents;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class WebviewActivity extends Activity {
	
	private int progress;
	private static Handler progressBarHandler;
	public static ProgressBar progressBar;
	WebView webview ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		Intent intent = getIntent();
		String president = intent.getExtras().getString("president");
		president = president.replaceAll(" ", "_");
		
		
		
		webview = (WebView) findViewById (R.id.webview);
		
		String baseURL = "http://en.wikipedia.org/wiki/";
		String fullURL= baseURL + president;
		
		//webview.setWebViewClient(new WebViewClient());
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		initializeWebView();
		
		
		webview.loadUrl(fullURL);
		
		
	}

	private void initializeWebView() {
		
		// enable tracking of webview load progress
		final Activity activity = this;
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				activity.setProgress(progress * 1000);
			}
		});

		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, 
	                    android.graphics.Bitmap favicon) {
				// run progress bar
				runProgressbar();
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				progressBar.setProgress(progressBar.getMax());
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(activity, "Network Error: " + description,
						Toast.LENGTH_SHORT).show();
			}

		});
	}
	
	private void runProgressbar() {
		progress = 0;
		progressBar.setVisibility(0);
		progressBar.setProgress(0);
		progressBarHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (progress >= 100) {
					//progressBar.setVisibility(View.GONE);
				} else {
					// layout progress bar
					progress = webview.getProgress();
					progressBar.setProgress(progress);
					// Log.i("MyTag", "Progress: " + progress);
					progressBarHandler.sendEmptyMessageDelayed(0, 100);
				}
			}
		};
		progressBarHandler.sendEmptyMessage(0);
	}
	
	

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
