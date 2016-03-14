public class Rekord2 
{
	int numer;
	int dl_fazy; // dlugosc fazy procesora
	int moment;	// moment zgloszenia sie procesu
	int czas;	// czas oczekiwania
	
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
}
