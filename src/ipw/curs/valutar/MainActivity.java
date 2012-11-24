package ipw.curs.valutar;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
	static boolean useOlderData = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		if (!isOnline()) {
			System.out.println("NU e net");
			useOlderData = true;
			displayAlert();
		}
		// The activity TabHost
		TabHost tabHost = getTabHost(); 
		// Resusable TabSpec for each tab
		TabHost.TabSpec spec; 
		// Reusable Intent for each tab
		Intent intent; 
		
		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, CursActivity.class);
		// Create our custom view.
		View tabView = createTabView(this, "Curs Valutar");
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost.newTabSpec("Curs Valutar").setIndicator(tabView)
				.setContent(intent);
		tabHost.addTab(spec);
		
		// Do the same for the other tabs
		tabView = createTabView(this, "Convertor valutar");
		intent = new Intent().setClass(this, ConvertorActivity.class);
		spec = tabHost.newTabSpec("Convertor valutar").setIndicator(tabView)
				.setContent(intent);
		tabHost.addTab(spec);

		
	}

	private static View createTabView(Context context, String tabText) {
		View view = LayoutInflater.from(context).inflate(R.layout.custom_tab,
				null, false);
		TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		tv.setText(tabText);
		return view;
	}

	//function that checks if the device is connected to the internet
	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	//function that displays an alert saying that it can not connect to the internet
	public void displayAlert() {
		new AlertDialog.Builder(this)
				.setMessage(
						"Nu esti conectat la internet. Folosim datele de la ultima actualizare")
				.setTitle("Eroare de conexiue").setCancelable(true)
				.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						useOlderData = true;
					}
				}).show();
	}
}
