package ipw.curs.valutar;

public class Valuta {
	
	String nume;
	String curs;
	float nr_curs;
	int multiplier;
	
	
	public Valuta(String nume, String curs, float nr_curs, int multiplier) {
		super();
		this.nume = nume;
		this.curs = curs;
		this.nr_curs = nr_curs;
		this.multiplier = multiplier;
	}


	@Override
	public String toString() {
		return "Valuta [nume=" + nume + ", curs=" + curs + ", nr_curs="
				+ nr_curs + ", multiplier=" + multiplier + "]\n";
	}
	
	

}
