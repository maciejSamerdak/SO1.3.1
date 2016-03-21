import java.util.*;
import java.io.*;

public class Main2 
{
	public static void main(String[] args)
	{
		Scanner sc=new Scanner(System.in);
		System.out.print("Podaj N: ");
		int n = sc.nextInt();							// ilosc procesow w ciagu testowym
		System.out.print("\n"+"Podaj kwant czasu dla algorytmu rotacyjnego: ");
		int k = sc.nextInt();
		sc.close();
		System.out.println("");
		int a=2;										//ilosc ciagow testowych
		Rekord2[][] tablica = new Rekord2[a][n];		// tablica glowna - wykorzystanie w alg. FIFO
		Rekord2[][] tab2 = new Rekord2[a][n];			// tablica pomocnicza - algorytm SJF niewywlaszczajacy
		Rekord2[][] tab3 = new Rekord2[a][n];			// tablica pomocnicza - algorytm SJF wywlaszczajacy
		Rekord2[][]tablica2 = null;
		double[] tab4 = new double [n];			// tablica pomocnicza - algorytm RR
		double[] tab5 = new double [n];			// tablica pomocnicza - algorytm RR
		for(int w=0;w<a;w++)
		{
			for(int ww=0;ww<n;ww++)
			{
				tablica[w][ww] = new Rekord2();
				tab2[w][ww] = new Rekord2();
				tab3[w][ww] = new Rekord2();
			}
		}
		Rekord2 r=new Rekord2();
		Random g=new Random();							// generator liczb losowych
		int l;											// l - suma dlugosci wszystkich faz procesow w alg. FIFO
		int m2;											// m2 - zmienna do generowania momentu zgloszenia procesu w alg. FIFO
		double sr;										// sr - suma poczatkow wykonania procesu (potrzebna do wyliczenia sredniej w alg. FIFO)
		int sdl;										// sdl - suma dlugosci faz w algorytmie FIFO
		double ssdl;									// zmienne konieczne do algorytmu SJF niewywlaszczajacego
		int index=0;									
		boolean zmiana;									
		int x;											
		int y;											
		int zm2;	
		int zm3;
		int wyk;
		int pom;
		int sdl2;
		double pom2=0;
		double[][] xp = new double[4][a];
		double pom3=0;
		double total=0;
		
		
		for (int i=0;i<a;i++)
		{
			l=0;
			m2=0;
			sr=0;
			r.czas=0;
			r.moment=0;
			int qq=i+1;
			System.out.printf("Tablica procesow nr " + qq + ":");
			System.out.println("");
			System.out.printf("%20s %20s %20s %20s", "Numer procesu", "Dlugosc fazy", "Moment zgloszenia", "Poczatek wykonania");
			System.out.print("\n");
			r.dl_fazy=g.nextInt(50)+1;
			l=l+r.dl_fazy;
			r.numer=1;
			tab2[i][0].dl_fazy=r.dl_fazy;
			tab2[i][0].numer=r.numer;
			tab2[i][0].moment=r.moment;
			tab2[i][0].czas=r.czas;
			tab3[i][0].dl_fazy=r.dl_fazy;
			tab3[i][0].numer=r.numer;
			tab3[i][0].moment=r.moment;
			tab3[i][0].czas=r.czas;
			r.wyswietl();
			for (int j=1;j<n;j++)
			{
				r.numer=j+1;
				r.dl_fazy=g.nextInt(50)+1;
				m2=g.nextInt(30)+1;
				r.moment=r.moment+m2;
				if(r.moment>l)
				{
					r.czas=r.moment;
					l=r.moment;
				}
				else
				{
					r.czas=l;
				}
				l=l+r.dl_fazy;
				sr=sr+r.czas;
				tablica[i][j].dl_fazy=r.dl_fazy;
				tablica[i][j].numer=r.numer;
				tablica[i][j].moment=r.moment;
				tablica[i][j].czas=r.czas;
				tablica[i][j].wyswietl();
				tab2[i][j]=tablica[i][j];
				tab3[i][j]=tablica[i][j];
			}
			xp[0][i]=sr/n;
			/* System.out.println("Sredni czas dla FCFS: " + sr/n);
			System.out.println(); */
			
			tablica2=tablica.clone();
		}
			
			// Algorytm SJF niewywlaszczajacy
			
			Rekord2 zamiana = new Rekord2();
			Rekord2 min = new Rekord2();
			
			for(x=0;x<a;x++)
			{
				sdl=tab2[x][0].dl_fazy;
				ssdl=0;
				ssdl=ssdl+sdl;
				// System.out.println(sdl);
				for(y=1;y<n;y++)
				{
					min=tab2[x][y];
					zm2=y+1;
					zmiana=false;
					while(zm2<=n-1 && sdl>tab2[x][zm2].moment)
					{
						if(tab2[x][zm2].dl_fazy<min.dl_fazy)
						{
							min=tab2[x][zm2];
							zmiana=true;
							index=zm2;
						}
						zm2++;
					}
					sdl=sdl+min.dl_fazy;
					// System.out.println(sdl);
					ssdl=ssdl+sdl;
					if(zmiana==true)
					{
						zamiana=tab2[x][y];
						tab2[x][y]=tab2[x][index];
						for(;index>y+1;index--)
						{
							tab2[x][index]=tab2[x][index-1];
						}
							tab2[x][y+1]=zamiana;
					}
				}
				xp[1][x]=(ssdl-sdl)/n;
				/*System.out.println("Sredni czas dla SJF niewywlaszczajacego: " + (ssdl-sdl)/n);
				System.out.println();*/
			}
			
			// Algorytm SJF wywlaszczajacy
			
			for(x=0;x<a;x++)
			{
				sdl2=0;
				for(y=1;y<n;y++)
				{
					wyk=tab3[x][y].moment-tab3[x][y-1].moment;
					while(wyk!=0)
					{
						for(zm3=0;zm3<y;zm3++)											// przypisuje min wartosc pierwszemu niezerowemu elementowi
						{
							if(tab3[x][zm3].dl_fazy!=0) {min=tab3[x][zm3]; index=zm3; break;}
						}
						for(zm3=0;zm3<y;zm3++)
						{	
							if(min.dl_fazy>tab3[x][zm3].dl_fazy && tab3[x][zm3].dl_fazy!=0) // szukanie min
							{
								min=tab3[x][zm3];
								index=zm3;
							}
						}
						if(tab3[x][index].dl_fazy==0)
							break;
						if(tab3[x][index].dl_fazy<=wyk)
						{
							pom=wyk-tab3[x][index].dl_fazy;
							tab3[x][index].dl_fazy=0;
							wyk=pom;
							if(tablica[x][index].dl_fazy==tab3[x][index].dl_fazy) { tab3[x][index].czas=sdl2; }
							sdl2=sdl2+(wyk-pom);
							
						}
						else
						{
							if(tablica[x][index].dl_fazy==tab3[x][index].dl_fazy) { tab3[x][index].czas=sdl2; }
							tab3[x][index].dl_fazy=tab3[x][index].dl_fazy-wyk;
							sdl2=sdl2+wyk;
							wyk=0;
						}
					}
				}
				for(int h=0;h<n;h++)
				{
					for(int zm4=0;zm4<n;zm4++)
					{
						if(tab3[x][zm4].dl_fazy!=0) {min=tab3[x][zm4]; index=zm4; break;}
					}
					for(int zm5=0;zm5<n;zm5++)
					{	
						if(min.dl_fazy>tab3[x][zm5].dl_fazy && tab3[x][zm5].dl_fazy!=0) // szukanie min
						{
							min=tab3[x][zm5];
							index=zm5;
						}
					}
					if(tablica[x][index].dl_fazy==tab3[x][index].dl_fazy) { tab3[x][index].czas=sdl2; }
					sdl2=sdl2+tab3[x][index].dl_fazy;
					tab3[x][index].dl_fazy=0;
				}
				for(int zm6=0;zm6<n;zm6++)
				{
					pom2=pom2+tab3[x][zm6].czas;
				}
				xp[2][x]=pom2/n;
				// System.out.println("Sredni czas dla SJF wywlaszczajacego: " + sr);
				pom2=0;
			}
			
			//Algorytm rotacyjny
			
			for(x=0;x<a;x++){
			 	for(y=0; y<n; y++){
						if (tablica2[x][y].dl_fazy>k){
							tablica2[x][y].dl_fazy-=k;
							tab4[y]=pom3-tablica2[x][y].moment;
							pom3+=k;
							tab5[y]=pom3;
							System.out.println(x+" "+y+" "+tablica2[x][y].dl_fazy);
						}
						else{
							tab4[y]=pom3-tablica2[x][y].moment;
							pom3+=tablica2[x][y].dl_fazy;
							tablica2[x][y]=null;					
						}
				}
				
				while(!isFinished(tablica2, x, n)){
				for(y=0; y<n; y++){
					if (tablica2[x][y]!=null){
						if (tablica2[x][y].dl_fazy>k){
							tablica2[x][y].dl_fazy-=k;
							tab4[y]+=pom3-tab5[y];
							pom3+=k;
							tab5[y]=pom3;
							System.out.println(x+" "+y+" "+tablica2[x][y].dl_fazy);
						}
						else{
							tab4[y]+=pom3-tab5[y];
							pom3+=tablica2[x][y].dl_fazy;
							tablica2[x][y]=null;					
						}
					}		
				}
				for (int i=0; i<n; i++)
					total+=tab4[i];
				}
				System.out.println(total);
				xp[3][x]=total/n;
			}
			
			int qqq;
			for (int q=0;q<a;q++)
			{
				qqq=q+1;
				System.out.println("Tabela procesow nr " + qqq + ":");

					System.out.println("Sredni czas dla FCFS: " + xp[0][q]);
					System.out.println("Sredni czas dla SJF niewywlaszczajacego: " + xp[1][q]);
					System.out.println("Sredni czas dla SJF wywlaszczajacego: " + xp[2][q]);
					System.out.println("Œredni czas dla RR dla kwantu czasu = " + k + "ms: " + xp[3][q]);
			}
	}
	
	static boolean isFinished(Rekord2[][] array, int x, int y){
		int check=0;
		for (int i=0; i<y; i++)
			if (array[x][i]==null)
				check++;
		return (check==y);
	}
	
}