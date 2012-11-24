package ipw.curs.valutar;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CursActivity extends Activity {

	public static ArrayList<Valuta> cursuri;
	MyAdapter adapter1, adapter2;
	ListView lv1, lv2;
	TextView tv, valutaTV;
	String data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.curs_landscape);

		adapter1 = new MyAdapter(this);
		adapter2 = new MyAdapter(this);

		lv1 = (ListView) findViewById(R.id.lista_stanga);
		lv2 = (ListView) findViewById(R.id.lista_dreapta);

		lv1.setAdapter(adapter1);
		lv2.setAdapter(adapter2);

		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				System.out.println("click pe " + cursuri.get(position).nume);
				// valutaTV.setText(cursuri.get(position).nume);
			}
		});

		lv2.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long id) {
				System.out.println("click pe elem "
						+ cursuri.get(position + cursuri.size() / 2).nume);
			}
		});
		System.out.println("fac activitatea asta");
		String myXML="";
		if (MainActivity.useOlderData == false)
		{
			System.out.println("incerc sa intru aiurea");
			//download new data
			myXML = download();
			//write it in file
			try
			{
				File sdcardfile = new File(Environment.getExternalStorageDirectory().getPath() + "/older");
				FileWriter writer = new FileWriter(sdcardfile);
				writer.write(myXML);
				writer.close();


				FileReader in = new FileReader(sdcardfile);
				BufferedReader reader = new BufferedReader(in);
				String line = "";
				while ((line = reader.readLine()) != null)
				{
					System.out.println("o linie " + line);
				}
				
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		else
		{
			//read it from file
			//test
			try
			{
				System.out.println("intra aici");
				File sdcardfile = new File(Environment.getExternalStorageDirectory().getPath() + "/older");
				System.out.println("crapa1");
				FileReader in = new FileReader(sdcardfile);
				System.out.println("crapa2");
				BufferedReader reader = new BufferedReader(in);
				System.out.println("crapa3");
				String line = "";
				System.out.println("crapa4");
				while ((line = reader.readLine()) != null)
				{
					System.out.println("o linie " + line);
					myXML = myXML + line + "\n";
				}
				System.out.println("crapa5");
				reader.close();
				in.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		System.out.println("xml-ul este" + myXML);
		// String myXML =
		// "<DataSet xmlns=\"http://www.bnr.ro/xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.bnr.ro/xsd nbrfxrates.xsd\">\n<Header>\n<Publisher>National Bank of Romania</Publisher>\n<PublishingDate>2012-09-14</PublishingDate>\n<MessageType>DR</MessageType>\n</Header>\n<Body>\n<Subject>Reference rates</Subject>\n<OrigCurrency>RON</OrigCurrency>\n<Cube date=\"2012-09-14\">\n<Rate currency=\"AED\">0.9396</Rate>\n<Rate currency=\"AUD\">3.6560</Rate>\n<Rate currency=\"BGN\">2.3030</Rate>\n<Rate currency=\"BRL\">1.7065</Rate>\n<Rate currency=\"CAD\">3.5750</Rate>\n<Rate currency=\"CHF\">3.7009</Rate>\n<Rate currency=\"CNY\">0.5465</Rate>\n<Rate currency=\"CZK\">0.1849</Rate>\n<Rate currency=\"DKK\">0.6042</Rate>\n<Rate currency=\"EGP\">0.5661</Rate>\n<Rate currency=\"EUR\">4.5042</Rate>\n<Rate currency=\"GBP\">5.5956</Rate>\n<Rate currency=\"HUF\" multiplier=\"100\">1.5998</Rate>\n<Rate currency=\"INR\">0.0634</Rate>\n<Rate currency=\"JPY\" multiplier=\"100\">4.4157</Rate>\n<Rate currency=\"KRW\" multiplier=\"100\">0.3089</Rate>\n<Rate currency=\"MDL\">0.2811</Rate>\n<Rate currency=\"MXN\">0.2703</Rate>\n<Rate currency=\"NOK\">0.6065</Rate>\n<Rate currency=\"NZD\">2.8784</Rate>\n<Rate currency=\"PLN\">1.1101</Rate>\n<Rate currency=\"RSD\">0.0387</Rate>\n<Rate currency=\"RUB\">0.1121</Rate>\n<Rate currency=\"SEK\">0.5248</Rate>\n<Rate currency=\"TRY\">1.9212</Rate>\n<Rate currency=\"UAH\">0.4262</Rate>\n<Rate currency=\"USD\">3.4514</Rate>\n<Rate currency=\"XAU\">196.9021</Rate>\n<Rate currency=\"XDR\">5.3385</Rate>\n<Rate currency=\"ZAR\">0.4189</Rate>\n</Cube>\n</Body>\n</DataSet>";

		cursuri = new ArrayList<Valuta>();

		try {
			parse(myXML);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < cursuri.size() / 2; i++) {
			Valuta v = cursuri.get(i);
			adapter1.addCurrency(v.multiplier + " " + v.nume, v.curs + " RON");
		}
		for (int i = cursuri.size() / 2; i < cursuri.size(); i++) {
			Valuta v = cursuri.get(i);
			adapter2.addCurrency(v.multiplier + " " + v.nume, v.curs + " RON");
		}

		cursuri.add(new Valuta("RON", "1", 1.0f, 1));
		tv = (TextView) findViewById(R.id.data);
		tv.setText("Data ultimei actualizari: " + data);
	}

	private void parse(String myXML) throws Exception {

		InputStream is = new ByteArrayInputStream(myXML.getBytes());
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		doc.getDocumentElement().normalize();

		NodeList cubeList = null;
		cubeList = doc.getElementsByTagName("Cube");
		Node cube = cubeList.item(0);

		if (cube.getNodeType() == Node.ELEMENT_NODE) {
			Element dataEl = (Element) cube;
			data = dataEl.getAttribute("date");
		}
		NodeList children = cube.getChildNodes();

		int num = children.getLength();

		// System.out.println("num=" + num);
		for (int i = 0; i < num; i++) {
			if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element) children.item(i);
				String currency = child.getAttribute("currency");
				String multiplier = child.getAttribute("multiplier");
				String value = child.getChildNodes().item(0).getNodeValue()
						.trim();

				// System.out.println("currency " + currency + " multiplier " +
				// multiplier + " value " + value);
				int multi = 1;
				if (multiplier.length() > 0)
					multi = Integer.parseInt(multiplier);
				cursuri.add(new Valuta(currency, value,
						Float.parseFloat(value), multi));
			}

		}

	}

	private String download() {
		// imi descarc xml-ul si-l returnez ca string
		HttpGet getMethod = new HttpGet("http://bnr.ro/nbrfxrates.xml");
		HttpClient client = new DefaultHttpClient();
		try {

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = client.execute(getMethod, responseHandler);
			// responseBody reprezinta continutul fisierului RSS luat de pe
			// Internet

			return responseBody;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

}

class ValutaItem {
	String valuta;
	String valoare;

	public ValutaItem(String valuta, String valoare) {
		super();
		this.valuta = valuta;
		this.valoare = valoare;
	}

}

class MyAdapter extends BaseAdapter {

	ArrayList<ValutaItem> money;
	private Activity context;

	public MyAdapter(Activity _context) {
		this.context = _context;
		money = new ArrayList<ValutaItem>();
	}

	public View getView(int position, View convertView, ViewGroup list) {
		// functia trebuie sa intoarca view-ul de pe pozitia position din lista
		// convertView este un element din lista ce nu mai este vizibil si poate
		// fi convertit
		View element;
		if (convertView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			element = inflater.inflate(R.layout.list_item, null);
		} else
			element = convertView;

		TextView valuta = (TextView) element.findViewById(R.id.valuta);
		TextView valoare = (TextView) element.findViewById(R.id.valoare);

		valuta.setText(money.get(position).valuta);
		valoare.setText(money.get(position).valoare);

		return element;
	}

	public int getCount() {
		// intaorce nr de elemente din lista
		return money.size();
	}

	public Object getItem(int position) {
		// intoarce elementul de pe pozitia position din model
		return money.get(position);
	}

	public long getItemId(int position) {
		// fiecare element din lista poate avea un id, nu este insa obligatoriu
		return 0;
	}

	public void addCurrency(String valuta, String valoare) {
		ValutaItem vi = new ValutaItem(valuta, valoare);
		money.add(vi);
		// acesta functie deterimina adaptorul sa ceara listei sa reafiseze
		// continutul
		this.notifyDataSetChanged();
	}
}
