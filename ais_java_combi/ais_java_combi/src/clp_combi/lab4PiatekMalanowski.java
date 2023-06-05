/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clp_combi;

import org.jacop.core.*; 
import org.jacop.constraints.*; 
import org.jacop.search.*; 
import java.util.ArrayList;

/**
 *
 * @author
 */

/**
 * 
 * It models and solves Zebra logic puzzle.
 * 
 * @author Radoslaw Szymanek
 * 
 * It was given at The German Institute of Logical Thinking in Berlin, 1981. And 98% FAILED.
 *
 * Conditions
 *
    1. The white house is directly between Frank's house and the web developer's house.

    2. The maintenance technician lives at one end of the street, next door to the yellow house.

    3. The robotics engineer�s residence is one of those to the left of Gavin's house.

    4. The green house is not at the extreme right.

    5. Paul lives next door to one of the two corner houses.

    6. The web developer lives next door and to the right of the yellow house.

    7. The blue house is next door to Damian's residence.

    8. Paul lives in one of the houses to the right of the yellow house.

    9. The middle house on the block is not painted green.

    10. The PLC programmer lives next door to Paul.
 * Q. Who owns a Zebra, and who drinks water?
 * 
 * They sometimes smoke different brands of cigarettes too, 
 * but that's apparently no longer politically correct, so they all quit. 
 */

public class lab4PiatekMalanowski {
    
    public void combi_zebra() {

        Store store = new Store();
        ArrayList<IntVar> vars = new ArrayList();
        DepthFirstSearch<IntVar> search = new DepthFirstSearch();
    
	System.out.println("Program to solve Zebra problem");
        
        // MODEL
        
        System.out.println("Combi model.");

        String[] colorNames = { "yellow", "green", "blue", "red", "white" };
        int iyellow = 0, igreen = 1, iblue = 2, ired = 3, iwhite = 4;
        
        String[] Names = { "Damian", "Frank", "Gavin",
            "Paul", "Victor" };
        int iDamian = 0, iFrank = 1, iGavin = 2, iPaul = 3, iVictor = 4;

        String[] professionNames = { "control engineer", "maintenance technician", "PLC programmer",
            "robotics engineer", "web developer" };
        int icontrol = 0, itechnician = 1, iPLC = 2, irobotics = 3, iweb = 4;

        IntVar color[] = new IntVar[5];
        IntVar names[] = new IntVar[5];
        IntVar profession[] = new IntVar[5];

        for (int i = 0; i < 5; i++) {
            color[i] = new IntVar(store, colorNames[i], 1, 5);
            names[i] = new IntVar(store, Names[i], 1, 5);
            profession[i] = new IntVar(store, professionNames[i], 1, 5);
            vars.add(color[i]); vars.add(names[i]); vars.add(profession[i]);
        }

        store.impose(new Alldifferent(color));
        store.impose(new Alldifferent(names));
        store.impose(new Alldifferent(profession));

         // 1. The white house is directly between Frank's house and the web developer's house.
	store.impose(new Or(new And(new XplusCeqZ(color[iwhite],1,names[iFrank]), 
                       new XplusCeqZ(color[iwhite],-1,profession[iweb])),
                        new And(new XplusCeqZ(color[iwhite],-1,names[iFrank]), 
                                new XplusCeqZ(color[iwhite],1,profession[iweb]))));
        // 2. The maintenance technician lives at one end of the street, next door to the yellow house.
        store.impose(new Or(new And(new XeqC(profession[itechnician],1), new XeqC(color[iyellow],2)), 
                            new And(new XeqC(profession[itechnician],5), new XeqC(color[iyellow],4))));
        // 3. The robotics engineer�s residence is one of those to the left of Gavin's house.
        store.impose(new XltY(profession[irobotics],names[iGavin]));
        // 4. The green house is not at the extreme right.
	store.impose(new XneqC(color[igreen], 5));
        // 5. Paul lives next door to one of the two corner houses.
        store.impose(new Or(new XeqC(names[iPaul],2), new XeqC(names[iPaul],4)));
        // 6. The web developer lives next door and to the right of the yellow house.
        store.impose(new XplusCeqZ(color[iyellow],1,profession[iweb]));
        // 7. The blue house is next door to Damian's residence.
        store.impose(new Or(new XplusCeqZ(color[iblue],1,names[iDamian]),
                            new XplusCeqZ(color[iblue],-1,names[iDamian])));
        // 8. Paul lives in one of the houses to the right of the yellow house.
        store.impose(new XgtY(names[iPaul],color[iyellow]));
        // 9. The middle house on the block is not painted green.
        store.impose(new XneqC(color[igreen],3));
        // 10. The PLC programmer lives next door to Paul.
        store.impose(new Or(new XplusCeqZ(profession[iPLC],1,names[iPaul]),
                            new XplusCeqZ(profession[iPLC],-1,names[iPaul])));

        // MODEL END

        // SEARCH
        
        //System.out.println("Combi search.");
    
        SelectChoicePoint<IntVar> select = new SimpleSelect<>( vars.toArray(new IntVar[1]), 
                                      new SmallestDomain<>(), 
                                      new MostConstrainedStatic<>(),
                                      new IndomainMin<>() );
        boolean result = search.labeling(store, select);
        
        System.out.println("Search result is: " + result);
        
        // SEARCH END

        System.out.println("Name: " + names[0] + " " + names[1] + " " + names[2] + " " + names[3] + " " + names[4]);
        System.out.println("Profession: " + profession[0] + " " + profession[1] + " " + profession[2] + " " + profession[3] + " " + profession[4]);
        System.out.println("Color: " + color[0] + " " + color[1] + " " + color[2] + " " + color[3] + " " + color[4]);
        }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        lab4PiatekMalanowski combi = new lab4PiatekMalanowski(); 
        
        combi.combi_zebra();
    }
    
}
