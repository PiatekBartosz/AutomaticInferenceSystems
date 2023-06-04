/*
 * Example from Jacop Guide: 1.1 Getting started, model and search separately, 
 * variables in collection ArrayList
 */

package clp_getting_started;

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;

import java.util.ArrayList;


public class CLP_getting_started_vars {

    Store store = new Store(); // define FD store
    ArrayList<IntVar> vars = new ArrayList<IntVar>(); // define FD variables list
    
    public void model() {
                    
        int size = 4;
             
        // define finite domain variables
        IntVar[] v = new IntVar[size];
        for (int i=0; i<size; i++) {
            v[i] = new IntVar(store, "v"+i, 1, size);
            vars.add( v[i] ); //
        }
        //System.out.println("Domains: " + vars.toString());
        
        // define constraints
        store.impose( new XneqY(v[0], v[1]) );
        store.impose( new XneqY(v[0], v[2]) );
        store.impose( new XneqY(v[1], v[2]) );
        store.impose( new XneqY(v[1], v[3]) );
        store.impose( new XneqY(v[2], v[3]) );
        
    }
    
    public boolean search() {
        
        // search for a solution and print results
        Search<IntVar> search = new DepthFirstSearch<IntVar>();
        SelectChoicePoint<IntVar> select =
                    new InputOrderSelect<IntVar>(store, vars.toArray(new IntVar[1]),
                        new IndomainMin<IntVar>());
        boolean result = search.labeling(store, select);
              
        return result;
    } 
    
    public void solutionPrint() {
        System.out.println("Solution: " + vars.toString());
    } 
    
    
    public static void main(String[] args) {
        
        CLP_getting_started_vars example = new CLP_getting_started_vars ();
        
        example.model();
        
        boolean result = example.search();
        
        if ( result ) {
            System.out.println("Solution(s) found");
            example.solutionPrint();
        }
        else
            System.out.println("No solution");   
    }
           
}
