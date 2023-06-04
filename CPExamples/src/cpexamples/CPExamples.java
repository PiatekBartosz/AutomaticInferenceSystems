
package clp_combi;

import org.jacop.core.*; 
import org.jacop.constraints.*; 
import org.jacop.search.*; 
import java.util.ArrayList;

/**
 *
 * @authors:
 * 
 * 
 * 
 */

/**
 * 
 * It models and solves Zebra logic puzzle. * 
 * @author 
 * 
 */

public class clp_combi {
    
    public void combi_zebra() {

        Store store = new Store();
        ArrayList<IntVar> vars = new ArrayList();
        DepthFirstSearch<IntVar> search = new DepthFirstSearch();
    
	System.out.println("Program to solve Zebra problem");
        
    // MODEL

    String[] studentNames = {"Alice", "Barbara", "Charles", "Daniel", "Edward"};
    int iAlice = 0, iBarbara = 1, iCharles = 2, iDaniel = 3, iEdward = 4;
	
    String[] lastNames = { "Fox","Green", "Harris", "Ivory", "Jones"};
	int iFox = 0, iGreen = 1, iHarris = 2, iIvory = 3, iJones = 4;

	String[] daysNames = { "Monaday", "Tuesday", "Wednesday", "Thursday", "Friday" };
	int iMonday = 0, iTuesday = 1, iWednesday = 2, iThursday = 3 , iFriday = 4;
	
	String[] lectureNames = { "job", "hygiene", "art", "nutrition","learning" };
	int ijob = 0, ihygiene = 1, iart = 2, inutrition = 3 , ilearning = 4 ;

	IntVar student[] = new IntVar[5];
	IntVar last[] = new IntVar[5];
	IntVar day[] = new IntVar[5];
	IntVar lecture[] = new IntVar[5];

	for (int i = 0; i < 5; i++) {
        student[i] = new IntVar(store, studentNames[i], 1, 5);
        last[i] = new IntVar(store, lastNames[i], 1, 5);
        day[i] = new IntVar(store, daysNames[i], 1, 5);
        lecture[i] = new IntVar(store, lectureNames[i], 1, 5);

        vars.add(student[i]); vars.add(last[i]); vars.add(day[i]);
        vars.add(lecture[i]);
	}

	store.impose(new Alldifferent(student));
	store.impose(new Alldifferent(last));
    store.impose(new Alldifferent(day));
	store.impose(new Alldifferent(lecture));
           
    //Days
    store.impose(new XeqC(day[iMonday], 1));
    store.impose(new XeqC(day[iTuesday], 2));
    store.impose(new XeqC(day[iWednesday], 3));
    store.impose(new XeqC(day[iThursday], 4));
    store.impose(new XeqC(day[iFriday], 5));

	// S1 to S7
    // 1. Alice had a lecture on Monday.
	store.impose(new XeqY(student[iAlice], day[iMonday]));
	
	// 2. Charles did not give his lecture (about hygiene) on Friday.
	store.impose(new XneqY(student[iCharles], day[iFriday]));
	store.impose(new XeqY(student[iCharles], lecture[ihygiene]));
	
	//3. Dietician Ivory talked about nutrition
    store.impose(new XeqY(last[iIvory], lecture[inutrition]));
    
    //4. Modern art was presented by a man.
    store.impose(new XneqY(student[iAlice], lecture[iart]));
    store.impose(new XneqY(student[iBarbara], lecture[iart]));
    
    //5. Mrs Jones and the lecturer for learning methods had their 
    //presentations on neighbouring days (in
    //this or in reverse order).
    store.impose(new XneqY(last[iJones], lecture[ilearning]));
   	store.impose(new Or(new XplusCeqZ(last[iJones], 1, lecture[ilearning]),
                 new XplusCeqZ(last[iJones], -1, lecture[ilearning])));
    store.impose(new XneqY(student[iCharles], last[iJones]));
    store.impose(new XneqY(student[iDaniel], last[iJones]));
    store.impose(new XneqY(student[iEdward], last[iJones]));
    
    //6. Harris lectured some time after Edward.
	store.impose(new XgtY(last[iHarris],student[iEdward]));
    store.impose(new XneqY(last[iHarris],student[iEdward]));
        
    //7. Daniel Fox's lecture was some time before the one about modern art.
    store.impose(new XneqY(student[iDaniel], lecture[iart]));
    store.impose(new XeqY(student[iDaniel], last[iFox]));
	store.impose(new XltY(student[iDaniel], lecture[iart]));				

    // MODEL END-

        // SEARCH
        

    
        SelectChoicePoint<IntVar> select = new SimpleSelect<>( vars.toArray(new IntVar[1]), 
                                      new SmallestDomain<>(), 
                                      new MostConstrainedStatic<>(),
                                      new IndomainMin<>() );
        boolean result = search.labeling(store, select);
        
        System.out.println("Search result is: " + result);
        
        // SEARCH END

        System.out.println("Name: " + student[0] + " " + student[1] + " " + student[2] + " " + student[3] + " " + student[4]);
        System.out.println("Last name: " + last[0] + " " + last[1] + " " + last[2] + " " + last[3] + " " + last[4]);
        System.out.println("Day: " + day[0] + " " + day[1] + " " + day[2] + " " + day[3] + " " + day[4]);        
        System.out.println("Lecture: " + lecture[0] + " " + lecture[1] + " " + lecture[2] + " " + lecture[3] + " " + lecture[4]);

        }


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        clp_combi combi = new clp_combi(); 
        
        combi.combi_zebra();
    }
    
}
