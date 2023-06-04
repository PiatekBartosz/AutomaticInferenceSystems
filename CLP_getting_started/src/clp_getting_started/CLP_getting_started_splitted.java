/*
 * Example from Jacop Guide: 1.1 Getting started, model and search separately 
 */

package clp_getting_started;

import org.jacop.core.*;
import org.jacop.constraints.*;
import org.jacop.search.*;


public class CLP_getting_started_splitted {
    
    Store store = new Store(); // define FD store
    IntVar[] v;
   
    public void model() {
                
        int size = 4;
        
        // define finite domain variables
        this.v = new IntVar[size];
        for (int i=0; i<size; i++)
            v[i] = new IntVar(store, "v"+i, 1, size);
        
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
                    new InputOrderSelect<IntVar>(store, v,
                        new IndomainMin<IntVar>());
        boolean result = search.labeling(store, select);
        
        if ( result )
            System.out.println("Solution: "+v[0]+", "+v[1]+", "+v[2]+", "+v[3]);
        
        return result;
    } 
    
    public static void main(String[] args) {
        
        CLP_getting_started_splitted example = new CLP_getting_started_splitted();
        
        example.model();
        
        boolean result = example.search();
        
        if ( result )
            System.out.println("Solution(s) found");
        else
            System.out.println("No solution");      
    }
   
}
