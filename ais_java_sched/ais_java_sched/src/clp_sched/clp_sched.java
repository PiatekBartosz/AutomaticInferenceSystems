/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clp_sched;

import org.jacop.core.*; 
import org.jacop.constraints.*; 
import org.jacop.search.*; 
import java.util.ArrayList;

/**
 * @author
 * Bartosz Piatek
 */

public class clp_sched {
    
    protected Store store = new Store();
    protected DepthFirstSearch search = new DepthFirstSearch<IntVar>();
    protected IntVar [] integers;
    protected ArrayList<IntVar> vars = new ArrayList<IntVar>();

    
    	public void sched_newspapers() {

		IntVar[] A = new IntVar[5];
		IntVar[] B = new IntVar[5];
		IntVar[] C = new IntVar[5];

		IntVar[] red = new IntVar[3];
		red[0] = new IntVar(store, "AdurationRed", 15, 15);
		red[1] = new IntVar(store, "BdurationRed", 15, 15);
		red[2] = new IntVar(store, "CdurationRed", 14, 14);
		
                IntVar[] green = new IntVar[3];
		green[0] = new IntVar(store, "AdurationGreen", 10, 10);
		green[1] = new IntVar(store, "BdurationGreen", 30, 30);
		green[2] = new IntVar(store, "CdurationGreen", 22, 22);
		
                IntVar[] oven = new IntVar[3];
		oven[0] = new IntVar(store, "AdurationOven", 15, 15);
		oven[1] = new IntVar(store, "AdurationOven", 8, 8);
		oven[2] = new IntVar(store, "AdurationOven", 15, 15);
		
                IntVar[] lacquer = new IntVar[3];
		lacquer[0] = new IntVar(store, "AdurationLacquer", 10, 10);
		lacquer[1] = new IntVar(store, "BdurationLacquer", 10, 10);
		lacquer[2] = new IntVar(store, "CdurationLacquer", 8, 8);
                
                IntVar[] enamel = new IntVar[3];
		enamel[0] = new IntVar(store, "durationAlgySun", 5, 5);
		enamel[1] = new IntVar(store, "durationBertieSun", 15, 15);
		enamel[2] = new IntVar(store, "durationCharlieSun", 3, 3);
		
		IntVar[][] durations = new IntVar[5][];
		durations[0] = red;
		durations[1] = green;
		durations[2] = oven;
		durations[3] = lacquer;
                durations[4] = enamel;

		for (int i = 0; i < 5; i++) {

			A[i] = new IntVar(store, "A[" + i + "]", 0, 100);

			B[i] = new IntVar(store, "B[" + i + "]", 0, 100);

			C[i] = new IntVar(store, "C[" + i + "]", 0, 100);
	
			vars.add(A[i]); vars.add(B[i]); vars.add(C[i]);
		}

		IntVar one = new IntVar(store, "one", 1, 1);
		IntVar[] three = new IntVar[3];
		IntVar[] threeOnes = { one, one, one};

		three[0] = A[0];
		three[1] = B[0];
		three[2] = C[0];
                
		store.impose(new Cumulative(three, red, threeOnes, one));

		three[0] = A[1];
		three[1] = B[1];
		three[2] = C[1];

		store.impose(new Cumulative(three, green, threeOnes, one));

		three[0] = A[2];
		three[1] = B[2];
		three[2] = C[2];

		store.impose(new Cumulative(three, oven, threeOnes, one));

		three[0] = A[3];
		three[1] = B[3];
		three[2] = C[3];

		store.impose(new Cumulative(three, lacquer, threeOnes, one));
                
                three[0] = A[4];
		three[1] = B[4];
		three[2] = C[4];

		store.impose(new Cumulative(three, enamel, threeOnes, one));
                
		IntVar makespan = new IntVar(store, "makespan", 0, 1000);

		int[] APrecedence = {1, 2, 3, 4, 5};

		for (int i = 0; i < 4; i++)
			store.impose(new XplusYlteqZ(A[APrecedence[i] - 1],
					durations[APrecedence[i] - 1][0],
					A[APrecedence[i + 1] - 1]));

		store.impose(new XplusYlteqZ(A[4], enamel[0], makespan));

		int[] BPrecedence = {2, 1, 3, 5, 4};

		for (int i = 0; i < 4; i++)
			store.impose(new XplusYlteqZ(B[BPrecedence[i] - 1],
					durations[BPrecedence[i] - 1][1],
					B[BPrecedence[i + 1] - 1]));

		store.impose(new XplusYlteqZ(B[4], enamel[1], makespan));

		int[] charliePrecedence = {2, 1, 3, 4, 5};

		for (int i = 0; i < 4; i++)
			store.impose(new XplusYlteqZ(C[charliePrecedence[i] - 1],
					durations[charliePrecedence[i] - 1][2],
					C[charliePrecedence[i + 1] - 1]));

		store.impose(new XplusYlteqZ(C[4], enamel[2], makespan));

        // MODEL END

        // SEARCH
        
		System.out.println("Scheduling search.");
    
        SelectChoicePoint<IntVar> select = new SimpleSelect<IntVar>( vars.toArray(new IntVar[1]), 
                                      new SmallestDomain<IntVar>(), 
                                      new MostConstrainedStatic<IntVar>(),
                                      new IndomainMin<IntVar>() );
        boolean result = search.labeling(store, select);
        
        System.out.println("Search result is: " + result);
        
        // SEARCH END
        
     		for (int i = 0; i < 5; i++)  System.out.println("A: " + A[i]);
     		for (int i = 0; i < 5; i++)  System.out.println("B: " + B[i]);
     		for (int i = 0; i < 5; i++)  System.out.println("C: " + C[i]);
        }
        
    public static void main(String[] args) {
        
        clp_sched sched = new clp_sched(); 

             sched.sched_newspapers();
    }
    
}
        
        
        
