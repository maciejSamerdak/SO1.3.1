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
		Rekord2[][] tablica2 = new Rekord2[a][n];
		for(int w=0;w<a;w++)
		{
			for(int ww=0;ww<n;ww++)
			{
				tablica[w][ww] = new Rekord2();
				tab2[w][ww] = new Rekord2();
				tab3[w][ww] = new Rekord2();
				tablica2[w][ww] = new Rekord2();
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
		int pom3=0;
		double total;
		
		
		for (int i=0;i<a;i++)
		{
			l=0;
			m2=0;
			sr=0;
			r.setCzas(0);
			r.setMoment(0);
			int qq=i+1;
			System.out.printf("Tablica procesow nr " + qq + ":"+"\n");
			//System.out.print("\n");
			System.out.printf("%20s %20s %20s %20s", "Numer procesu", "Dlugosc fazy", "Moment zgloszenia", "Czas oczekiwania"+"\n");
			//System.out.print("\n");
			r.setDl_fazy(g.nextInt(50)+1);
			l=l+r.getDl_fazy();
			r.setNumer(1);
			tab2[i][0].setDl_fazy(r.getDl_fazy());
			tab2[i][0].setNumer(r.getNumer());
			tab2[i][0].setMoment(r.getMoment());
			tab2[i][0].setCzas(r.getCzas());
			tab3[i][0].setDl_fazy(r.getDl_fazy());
			tab3[i][0].setNumer(r.getNumer());
			tab3[i][0].setMoment(r.getMoment());
			tab3[i][0].setCzas(r.getCzas());
			tablica2[i][0].setDl_fazy(r.getDl_fazy());
			tablica2[i][0].setNumer(r.getNumer());
			tablica2[i][0].setMoment(r.getMoment());
			tablica2[i][0].setCzas(r.getCzas());
			r.wyswietl();
			for (int j=1;j<n;j++)
			{
				r.setNumer(j+1);
				r.setDl_fazy(g.nextInt(50)+1);
				m2=g.nextInt(30)+1;
				r.setMoment(r.getMoment()+m2);
				if(r.getMoment()>l)
				{
					r.setCzas(r.getMoment());
					l=r.getMoment();
				}
				else
					r.setCzas(l-r.getMoment()); //nie powinno byæ r.setCzas(l-r.getMoment());
				
				l=l+r.getDl_fazy();
				sr=sr+r.getCzas();
				tablica[i][j].setDl_fazy(r.getDl_fazy());
				tablica[i][j].setNumer(r.getNumer());
				tablica[i][j].setMoment(r.getMoment());
				tablica[i][j].setCzas(r.getCzas());
				tablica[i][j].wyswietl();
				tab2[i][j].setDl_fazy(tablica[i][j].getDl_fazy());
				tab2[i][j].setNumer(tablica[i][j].getNumer());
				tab2[i][j].setMoment(tablica[i][j].getMoment());
				tab2[i][j].setCzas(tablica[i][j].getCzas());
				tab3[i][j].setDl_fazy(tablica[i][j].getDl_fazy());
				tab3[i][j].setNumer(tablica[i][j].getNumer());
				tab3[i][j].setMoment(tablica[i][j].getMoment());
				tab3[i][j].setCzas(tablica[i][j].getCzas());
				tablica2[i][j].setDl_fazy(tablica[i][j].getDl_fazy());
				tablica2[i][j].setNumer(tablica[i][j].getNumer());
				tablica2[i][j].setMoment(tablica[i][j].getMoment());
				tablica2[i][j].setCzas(0);
			}
			xp[0][i]=sr/n;
			/* System.out.println("Sredni czas dla FCFS: " + sr/n);
			System.out.println(); */
			
			//tablica2=(Rekord2[][])tablica.clone();
		}
			
			// Algorytm SJF niewywlaszczajacy
			
			Rekord2 zamiana = new Rekord2();
			Rekord2 min = new Rekord2();
			
			for(x=0;x<a;x++)
			{
				sdl=tab2[x][0].getDl_fazy();
				ssdl=0;
				ssdl=ssdl+sdl;
				// System.out.println(sdl);
				for(y=1;y<n;y++)
				{
					min=tab2[x][y];
					zm2=y+1;
					zmiana=false;
					while(zm2<=n-1 && sdl>tab2[x][zm2].getMoment())
					{
						if(tab2[x][zm2].getDl_fazy()<min.getDl_fazy())
						{
							min=tab2[x][zm2];
							zmiana=true;
							index=zm2;
						}
						zm2++;
					}
					sdl=sdl+min.getDl_fazy();
					// System.out.println(sdl);
					ssdl=ssdl+sdl-min.getMoment();
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
				xp[1][x]=(ssdl-sdl+min.getMoment())/n;
				/*System.out.println("Sredni czas dla SJF niewywlaszczajacego: " + (ssdl-sdl)/n);
				System.out.println();*/
			}
			
			// Algorytm SJF wywlaszczajacy
			
			for(x=0;x<a;x++)
			{
				sdl2=0;
				for(y=1;y<n;y++)
				{
					wyk=tab3[x][y].getMoment()-tab3[x][y-1].getMoment();
					while(wyk!=0)
					{
						for(zm3=0;zm3<y;zm3++)											// przypisuje min wartosc pierwszemu niezerowemu elementowi
						{
							if(tab3[x][zm3].getDl_fazy()!=0)
								min=tab3[x][zm3]; index=zm3; break;
						}
						for(zm3=0;zm3<y;zm3++)
						{	
							if(min.getDl_fazy()>tab3[x][zm3].getDl_fazy() && tab3[x][zm3].getDl_fazy()!=0) // szukanie min
							{
								min=tab3[x][zm3];
								index=zm3;
							}
						}
						if(tab3[x][index].getDl_fazy()==0)
							break;
						if(tab3[x][index].getDl_fazy()<=wyk)
						{
							pom=wyk-tab3[x][index].getDl_fazy();
							tab3[x][index].setDl_fazy(0);
							wyk=pom;
							if(tablica[x][index].getDl_fazy()==tab3[x][index].getDl_fazy())
								tab3[x][index].setCzas(sdl2-tab3[x][index].getMoment());
							sdl2=sdl2+(wyk-pom);
							
						}
						else
						{
							if(tablica[x][index].getDl_fazy()==tab3[x][index].getDl_fazy())
								tab3[x][index].setCzas(sdl2-tab3[x][index].getMoment());
							tab3[x][index].setDl_fazy(tab3[x][index].getDl_fazy()-wyk);
							sdl2=sdl2+wyk;
							wyk=0;
						}
					}
				}
				for(int h=0;h<n;h++)
				{
					for(int zm4=0;zm4<n;zm4++)
					{
						if(tab3[x][zm4].getDl_fazy()!=0){
							min=tab3[x][zm4]; index=zm4;
							break;
							}
					}
					for(int zm5=0;zm5<n;zm5++)
					{	
						if(min.getDl_fazy()>tab3[x][zm5].getDl_fazy() && tab3[x][zm5].getDl_fazy()!=0) // szukanie min
						{
							min=tab3[x][zm5];
							index=zm5;
						}
					}
					if(tablica[x][index].getDl_fazy()==tab3[x][index].getDl_fazy())
						tab3[x][index].setCzas(sdl2-tab3[x][index].getMoment());
					sdl2=sdl2+tab3[x][index].getDl_fazy();
					tab3[x][index].setDl_fazy(0);
				}
				for(int zm6=0;zm6<n;zm6++)
				{
					pom2=pom2+tab3[x][zm6].getCzas();
				}
				xp[2][x]=pom2/n;
				// System.out.println("Sredni czas dla SJF wywlaszczajacego: " + sr);
				pom2=0;
			}
			
			//Algorytm rotacyjny
			
			for(x=0;x<a;x++){

				while(!isFinished(tablica2, x, n)){
				for(y=0; y<n; y++){
					if(tablica2[x][y].getMoment()<=pom3){
					if (tablica2[x][y].getDl_fazy()!=0){
						if (tablica2[x][y].getDl_fazy()>k){
							tablica2[x][y].setDl_fazy(tablica2[x][y].getDl_fazy()-k);
							tablica2[x][y].setCzas(tablica2[x][y].getCzas()+pom3-tablica2[x][y].getMoment());
							pom3+=k;
							tablica2[x][y].setMoment(pom3);
							//System.out.println(x+" "+y+" "+tablica2[x][y].getDl_fazy());
						}
						else{
							tablica2[x][y].setCzas(tablica2[x][y].getCzas()+pom3-tablica2[x][y].getMoment());
							pom3+=tablica2[x][y].getDl_fazy();
							tablica2[x][y].setDl_fazy(0);
							tablica2[x][y].setMoment(0);
						}
					}
					}
					else{
						if(isFinished(tablica2, x, y)){
							pom3+=tablica2[x][y].getMoment();
						}
					}	
				}
				}
				total=0;
				for (int i=0; i<n; i++)
					total+=tablica2[x][i].getCzas();
				//System.out.println(total);
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
					System.out.println("Sredni czas dla RR dla kwantu czasu = " + k + "ms: " + xp[3][q]);
			}
	}
	
	static boolean isFinished(Rekord2[][] array, int x, int y){
		int check=0;
		for (int i=0; i<y; i++)
			if (array[x][i].getDl_fazy()==0)
				check++;
		return (check==y);
	}
	
}