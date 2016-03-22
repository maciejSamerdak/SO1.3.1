public class Rekord2 
{
	private int numer;
	private int dl_fazy; // dlugosc fazy procesora
	private int moment;	// moment zgloszenia sie procesu
	private int czas;	// czas oczekiwania
	
	Rekord2()
	{
		numer=0;
		dl_fazy=0;
		moment=0;
		czas=0;
	}
	
	Rekord2(int n, int dl, int m, int cz)
	{
		numer=n;
		dl_fazy=dl;
		moment=m;
		czas=cz;
	}
	
	public String toString()
	{
		return numer + " " + dl_fazy + " " + moment + " " + czas;
	}
	
	public void wyswietl()
	{
		System.out.printf("%20d %20d %20d %20d", numer, dl_fazy, moment, czas);
		System.out.println("");
	}
	
	public int getNumer(){
		return numer;
	}
	
	public void setNumer(int n){
		numer=n;
	}
	
	public int getDl_fazy(){
		return dl_fazy;
	}
	
	public void setDl_fazy(int f){
		dl_fazy=f;
	}
	
	public int getMoment(){
		return moment;
	}
	
	public void setMoment(int m){
		moment=m;
	}
	
	public int getCzas(){
		return czas;
	}
	
	public void setCzas(int t){
		czas=t;
	}
}
