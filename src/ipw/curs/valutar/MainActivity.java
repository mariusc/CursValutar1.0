package ipw.curs.valutar;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
/*
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, CursActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost

		spec = tabHost.newTabSpec("Curs Valutar").setIndicator("Curs Valutar")
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, ConvertorActivity.class);

		spec = tabHost.newTabSpec("Convertor valutar")
				.setIndicator("Convertor valutar").setContent(intent);
		tabHost.addTab(spec);

		// setez tabul default (cu care porneste aplicatia) pe 0 - SkinScan
		tabHost.setCurrentTab(0);
*/
		TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab
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
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null, false);
        TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
        tv.setText(tabText);
        return view;
    }
}
