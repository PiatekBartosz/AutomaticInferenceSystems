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
 *
 * @author
 * Gabriel Malanowski, Bartosz Piątek
 */

public class clp_sched {

    
    protected Store store = new Store();
    protected DepthFirstSearch search = new DepthFirstSearch<IntVar>();
    protected IntVar [] integers;
    protected ArrayList<IntVar> vars = new ArrayList<IntVar>();

    
    	public void sched_newspapers() {
		IntVar[] c_A = new IntVar[5];
		IntVar[] c_B = new IntVar[5];
		IntVar[] c_C = new IntVar[5];

		IntVar[] red = new IntVar[3];
		red[0] = new IntVar(store, "durationARed", 15, 15);
		red[1] = new IntVar(store, "durationBRed", 15, 15);
		red[2] = new IntVar(store, "durationCRed", 14, 14);
		IntVar[] green = new IntVar[3];
		green[0] = new IntVar(store, "durationAGreen", 10, 10);
		green[1] = new IntVar(store, "durationBGreen", 30, 30);
		green[2] = new IntVar(store, "durationCGreen", 22, 22);
		IntVar[] oven = new IntVar[3];
		oven[0] = new IntVar(store, "durationAOver", 15, 15);
		oven[1] = new IntVar(store, "durationBOven", 8, 8);
		oven[2] = new IntVar(store, "durationCOven", 15, 15);
		IntVar[] lacquer = new IntVar[3];
		lacquer[0] = new IntVar(store, "durationALaquer", 10, 10);
		lacquer[1] = new IntVar(store, "durationBLaquer", 10, 10);
		lacquer[2] = new IntVar(store, "durationCLaquer", 8, 8);
                IntVar[] enamel = new IntVar[3];
		enamel[0] = new IntVar(store, "durationAEnamel", 5, 5);
		enamel[1] = new IntVar(store, "durationBEnamel", 15, 15);
		enamel[2] = new IntVar(store, "durationCEnamel", 3, 3);

		IntVar[][] durations = new IntVar[5][];
		durations[0] = red;
		durations[1] = green;
		durations[2] = oven;
		durations[3] = lacquer;
                durations[4] = enamel;

		for (int i = 0; i < 5; i++) {
			c_A[i] = new IntVar(store, "A[" + i + "]", 0, 1000);
			
			c_B[i] = new IntVar(store, "B[" + i + "]", 0, 1000);
                        
                        c_C[i] = new IntVar(store, "C[" + i + "]", 0, 1000);
			
			vars.add(c_A[i]); vars.add(c_B[i]); vars.add(c_C[i]);
		}

		IntVar one = new IntVar(store, "one", 1, 1);
		IntVar[] three = new IntVar[3];
		IntVar[] threeOnes = { one, one, one };

		three[0] = c_A[0];
		three[1] = c_B[0];
		three[2] = c_C[0];
		store.impose(new Cumulative(three, red, threeOnes, one));

		three[0] = c_A[1];
		three[1] = c_B[1];
		three[2] = c_C[1];
		store.impose(new Cumulative(three, green, threeOnes, one));

		three[0] = c_A[2];
		three[1] = c_B[2];
		three[2] = c_C[2];
		store.impose(new Cumulative(three, oven, threeOnes, one));

		three[0] = c_A[3];
		three[1] = c_B[3];
		three[2] = c_C[3];
		store.impose(new Cumulative(three, lacquer, threeOnes, one));
                
                three[0] = c_A[4];
		three[1] = c_B[4];
		three[2] = c_C[4];
		store.impose(new Cumulative(three, enamel, threeOnes, one));

		IntVar makespan = new IntVar(store, "makespan", 0, 1000);

		int[] APrecedence = { 1, 2, 3, 4, 5 };
		for (int i = 0; i < 4; i++)
			store.impose(new XplusYlteqZ(c_A[APrecedence[i] - 1],
					durations[APrecedence[i] - 1][0],
					c_A[APrecedence[i + 1] - 1]));
		store.impose(new XplusYlteqZ(c_A[4], enamel[0], makespan));

		int[] BPrecedence = { 2, 1, 3, 5, 4 };
		for (int i = 0; i < 4; i++)
			store.impose(new XplusYlteqZ(c_B[BPrecedence[i] - 1],
					durations[BPrecedence[i] - 1][1],
					c_B[BPrecedence[i + 1] - 1]));
		store.impose(new XplusYlteqZ(c_B[4], lacquer[1], makespan));

		int[] CPrecedence = { 2, 1, 3, 4, 5 };
		for (int i = 0; i < 4; i++)
			store.impose(new XplusYlteqZ(c_C[CPrecedence[i] - 1],
					durations[CPrecedence[i] - 1][2],
					c_C[CPrecedence[i + 1] - 1]));
		store.impose(new XplusYlteqZ(c_C[4], enamel[2], makespan));
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
     		for (int i = 0; i < 5; i++)  System.out.println("A: " + c_A[i]);
     		for (int i = 0; i < 5; i++)  System.out.println("B: " + c_B[i]);
     		for (int i = 0; i < 5; i++)  System.out.println("C: " + c_C[i]);
        }
        


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        clp_sched sched = new clp_sched(); 
        

             sched.sched_newspapers();
    }
    
}
        
        
        
