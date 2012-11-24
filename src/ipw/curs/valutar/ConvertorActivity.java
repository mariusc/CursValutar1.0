package ipw.curs.valutar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertorActivity extends Activity {

	double sumaDeTransformat;
	double rezultat;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.convertor);
		
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.valuta_array, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
		
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.valuta_array, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
		spinner2.setOnItemSelectedListener(new MyOnItemSelectedListener());
	
		//CursActivity.cursuri.get(0).nume

	}
	
	public void convert(View button)
	{
		System.out.println("sunt selectate " + MyOnItemSelectedListener.selected1 + " si " + MyOnItemSelectedListener.selected2);
		EditText et = (EditText) findViewById(R.id.sumaInitiala);
		float sumaInitiala = 0;
		try
		{
			sumaInitiala = Float.parseFloat(et.getText().toString());
		}
		catch (NumberFormatException nfe)
		{
			Toast.makeText(this, "Introdu o suma valida pentru conversie", Toast.LENGTH_SHORT).show();
			return;
		}
		System.out.println(sumaInitiala);
		System.out.println("Am de transformat " + sumaInitiala + " " + MyOnItemSelectedListener.selected1 + " in " + MyOnItemSelectedListener.selected2);
		
		//impart cursul 1 la cursul 2 si inmultesc cu suma de transformat
		Valuta valuta1 = null, valuta2 = null;
		for (Valuta v : CursActivity.cursuri)
		{
			if (v.nume.equalsIgnoreCase(MyOnItemSelectedListener.selected1))
				valuta1 = v;
			if (v.nume.equalsIgnoreCase(MyOnItemSelectedListener.selected2))
				valuta2 = v;
		}
		System.out.println("valuta 1: " + valuta1);
		System.out.println("valuta 2: " + valuta2);
		float rezultat = sumaInitiala * valuta1.nr_curs / valuta2.nr_curs; 
		TextView tv = (TextView)findViewById(R.id.rezultat);
		tv.setText(rezultat + "");
//		EditText tv = (EditText)findViewById(R.id.rezultat);
//		tv.setKeyListener(null);
//		tv.setText(rezultat + "");
	}
	
}

class MyOnItemSelectedListener implements OnItemSelectedListener {
	
	static String selected1, selected2;
	
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		Spinner spinner = (Spinner) parent;
		if (spinner.getId() == R.id.spinner1)
		{
			selected1 = (String) parent.getItemAtPosition(pos);
		}
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
		else if (spinner.getId() == R.id.spinner2)
		{
			selected2 = (String) parent.getItemAtPosition(pos);
		}
    }

	public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
	
	
	

}
