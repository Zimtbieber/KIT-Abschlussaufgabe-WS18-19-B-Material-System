package edu.kit.informatik;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the product structure and checks if a part 
 * or assembly creates a cycle in the system when its added.
 * 
 * @author 
 * @version 1.0
 */
public class ProductStructure {
    
    private BillOfMaterial bom;
    private HashMap<Integer, String> levelAsm = new HashMap<>();
    private boolean cycleFree;
    private int level;
    private String cycle;
    
    /**
     * Requires the BillOfMaterial object for access of the assemblies.
     * 
     * @param bom the BillOfMaterial object 
     */
    public ProductStructure(BillOfMaterial bom) {
        this.bom = bom;
    }
   
    /**
     * Checks if the added part or assembly causes a cycle. To do that it starts the checkCycleFree()
     * method which is defined recursive to check if any branch create a cycle.
     * 
     * @return - a empty String if there is no cycle or
     *         - the cycle it would create
     */
    public String checkForCycle() {
                  
        cycle = "";
        cycleFree = true;
        levelAsm.clear();
        
        for (Assembly assembly: bom.getBom()) {
            
            level = 0;
            levelAsm.put(level, assembly.getName());
            level++;
            
            for (String component: assembly.getParts().keySet()) {
                
                levelAsm.put(level, component);
                
                if (cycle.isEmpty()) {
                    checkCycleFree(component);
                } else {
                    return cycle;
                }
            }
        }
        return "";
    }
    
    /* each time the method is called it saves the current level with its assembly/part in a HashMap
     * if the parameter is a assembly the method is called again and the level goes deeper.
     */
    private void checkCycleFree(String nameComponent) {
                        
        levelAsm.put(level, nameComponent);
            
        if (bom.asmExists(nameComponent)) {
                        
            for (String subComponent: bom.fetchAsm(nameComponent).getParts().keySet()) {
                    
                level++;
                levelAsm.put(level, subComponent);
                   
                if (containsDuplicate(getCycle()) && cycleFree) {
                    cycle = getCycle();
                    cycleFree = false;

                } else if (cycleFree) {
                    checkCycleFree(subComponent);
                    level--;
                }
            }
        } 
        clearLevel(level);
    }
        
    private String getCycle() {
        String cycle = "";
        
        for (String nameComponent: levelAsm.values()) {
            cycle += nameComponent + "-";
        }
        return cycle.substring(0, cycle.length() - 1);
    }
    
    // Clears the level, so if one branch is checked the loop goes back (n level(s)) to the node above
    private void clearLevel(int ebene) {
        
        for (int i = ebene; levelAsm.containsKey(i); i++) {
            levelAsm.remove(i);
        }
    }  
    
    private boolean containsDuplicate(String asmBranch) {
        String[] componentNames = asmBranch.split("-");
        ArrayList<String> placeHolder = new ArrayList<>();
        
        for (int i = 0; i < componentNames.length; i++) {
            
            if (placeHolder.contains(componentNames[i])) {
                return true;
            } else {
                placeHolder.add(componentNames[i]);
            }
        }
        return false;
    }
}