/**
This a solution to ship problem - similar to zebra
 */

package ExamplesJaCoP;

import java.util.ArrayList;

import JaCoP.constraints.Alldifferent;
import JaCoP.constraints.Eq;
import JaCoP.constraints.Not;
import JaCoP.constraints.Or;
import JaCoP.constraints.Reified;
import JaCoP.constraints.XeqC;
import JaCoP.constraints.XeqY;
import JaCoP.constraints.XneqY;
import JaCoP.constraints.XplusCeqZ;
import JaCoP.constraints.XplusYeqZ;
import JaCoP.core.IntVar;
import JaCoP.core.Store;
import JaCoP.core.Var;

/**
 * 
 * It models and solves Zebra logic puzzle.
 * 
 * @author 
 
 * Conditions
 *

 */

public class Ships extends Example {

	
	@Override
	public void model() {

		store = new Store();
		vars = new ArrayList<Var>();
		
		System.out.println("Program to solve Ships problem ");
                
		String[] nationalityNames = { "Greek", "Engish", "French", "Belgian", "Spanish" };
		int igr = 0, ieng = 1, ifr = 2, iblg = 3, isp = 4;
                
		String[] destinationNames = { "Marseille", "Manila", "Genoa",
				"Hamburg", "Port Said" };
		int iMar = 0, iMan = 1, iGeno = 2, iHam = 3, iPS = 4;

		String[] departureNames = { "six", "nine", "five", "seven", "eight" };
		int i6 = 0, i9 = 1, i5 = 2, i7 = 3 , i8 = 4 ; // TODO ustalic zakomentowanie
		
                String[] chimneyNames = { "black", "blue", "red",
				"white", "green" };
		int iblack = 0, iblue = 1, ired = 2, iwhite = 3, igreen = 4;
		
                String[] payloadNames = { "coffee", "cocoa", "corn", "rice", "tea" };
		int icoffee = 0, icocoa = 1, icorn = 2, irice = 3  , itea = 4 ; // TODO ustalic zakomentowanie

		IntVar nationality[] = new IntVar[5];
                IntVar destination[] = new IntVar[5];
		IntVar departure[] = new IntVar[5];
		IntVar chimney[] = new IntVar[5];
		IntVar payload[] = new IntVar[5];
		
		for (int i = 0; i < 5; i++) {
                    nationality[i] = new IntVar(store, nationalityNames[i], 1, 5);
                    destination[i] = new IntVar(store, destinationNames[i], 1, 5);
                    departure[i] = new IntVar(store, departureNames[i], 1, 5);
                    chimney[i] = new IntVar(store, chimneyNames[i], 1, 5);
                    payload[i] = new IntVar(store, payloadNames[i], 1, 5);

                    vars.add(nationality[i]); 
                    vars.add(destination[i]); 
                    vars.add(departure[i]);
                    vars.add(chimney[i]); 
                    vars.add(payload[i]);
		}

		store.impose(new Alldifferent(nationality));
		store.impose(new Alldifferent(destination));
		store.impose(new Alldifferent(departure));
		store.impose(new Alldifferent(chimney));
		store.impose(new Alldifferent(payload));

                // S1
                store.impose(new XeqY(nationality[igr], departure[i6]));
		store.impose(new XeqY(nationality[igr], payload[icoffee]));
                
                // S2
                store.impose(new XeqC(chimney[iblack], 3));
                
                // S3
                store.impose(new XeqY(nationality[ieng], departure[i9]));
                
                // S4
                store.impose(new XeqY(nationality[ifr], chimney[iblue]));
                store.impose(new XplusCeqZ(nationality[ifr], 1, payload[icoffee])); //TODO przemyslec w 1 war
                
                // S5
                store.impose(new XplusCeqZ(payload[icocoa], 1, destination[iMar]));

                // S6
                store.impose(new XeqY(nationality[iblg], destination[iMan]));
                
                // S7
                store.impose(new Or(new XplusCeqZ(payload[irice], 1, chimney[igreen]),
				new XplusCeqZ(chimney[igreen], 1, payload[irice])));
                
                // S8
                store.impose(new XeqY(departure[i5], destination[iGeno]));
                
                // S9
                store.impose(new XeqY(nationality[isp], departure[i7]));
                store.impose(new XplusCeqZ(destination[iMar], 1, nationality[isp]));
                
                // S10
                store.impose(new XeqY(chimney[ired], destination[iHam]));
                
                // S11
                store.impose(new Or(new XplusCeqZ(departure[i7], 1, chimney[iwhite]),
				new XplusCeqZ(chimney[iwhite], 1, departure[i7])));

                // S12 ???
                store.impose(new Or(new XeqC(payload[icorn], 1),
				new XeqC(payload[icorn], 5)));
                
                // S13
                store.impose(new XeqY(chimney[iblack], departure[i8]));
                
                // S14
                store.impose(new Or(new XplusCeqZ(payload[irice], 1, payload[icorn]),
				new XplusCeqZ(payload[icorn], 1, payload[irice])));
                
                // S15
                store.impose(new XeqY(destination[iHam], departure[i6]));
	}
	
	
	/**
	 * It executes the program to solve this simple logic puzzle.
	 * 
	 * @param args no argument is used.
	 */
	public static void main(String args[]) {

		Ships example = new Ships();
		
		example.model();

		if (example.searchMostConstrainedStatic())
                    System.out.println("Solution(s) found");
		
	}				
}
