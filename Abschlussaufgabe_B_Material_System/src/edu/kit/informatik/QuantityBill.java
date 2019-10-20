package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents the quantity bill. It is used to get the actual 
 * amount of direct and indirect assembled components of an assembly.
 * 
 * @author 
 * @version 1.0
 */
public class QuantityBill {
    
    private BillOfMaterial bom;
    private int level;
    private long multiplier;
    private HashMap<Integer, Long> levelMultiplicator = new HashMap<>();
    private List<Component> componentList = new ArrayList<>();
    
    /**
     * Requires the BillOfMaterial object for access of the assemblies.
     * 
     * @param bom the BillOfMaterial object 
     */
    public QuantityBill(BillOfMaterial bom) {        
        this.bom = bom;
    }
    
    /**
     * Methode to get the quantity bills components or assemblies.
     * Both methods (installedParts/Asm) are defined similar recursive.
     * 
     * @param nameAsm of the assembly
     * @param asm if the assemblies are requested -true, if parts -false
     * @return the quantity bill of nameAsm
     */
    public String getQuantityBill(String nameAsm, boolean asm) {
        
        componentList.clear();
        
        for (String asmComponent: bom.fetchAsm(nameAsm).getParts().keySet()) {
            
            multiplier = 1;
            level = 0;
            levelMultiplicator.put(level, multiplier);
     
            if (asm) { 
                installedAsms(nameAsm, asmComponent); 
            } else {
                installedPrts(nameAsm, asmComponent); 
            }
        }
        return getQuantityBill();
    }
    
    /* each time the method is called it saves the current level and the multiplier in a HashMap
     * with this HashMap the total amount could be calculated
     */
    private void installedPrts(String nameAsm, String nameComponent) {
        level++;
        long amountComponent = bom.fetchAsm(nameAsm).getParts().get(nameComponent);
        long amountLevelAbove = levelMultiplicator.get(level - 1);
        
        if (bom.asmExists(nameComponent)) {
     
            for (String subComponent: bom.fetchAsm(nameComponent).getParts().keySet()) {
   
                levelMultiplicator.put(level, amountComponent * amountLevelAbove);
   
                installedPrts(nameComponent, subComponent);
                
                level--;
            }
        } else {
            
           levelMultiplicator.put(level, amountComponent * amountLevelAbove);
           addToList(nameComponent);   
        }
    }
    
    private void installedAsms(String nameAsm, String nameComponent) {   
        level++;
        
        if (bom.asmExists(nameComponent)) {
            
           long amountComponent = bom.fetchAsm(nameAsm).getParts().get(nameComponent);
           long amountLevelAbove = levelMultiplicator.get(level - 1);
            
           levelMultiplicator.put(level, amountComponent * amountLevelAbove);
                
           addToList(nameComponent);
            
            for (String subComponent: bom.fetchAsm(nameComponent).getParts().keySet()) {
                                              
                if (bom.asmExists(subComponent)) {
                    installedAsms(nameComponent, subComponent);
                }   
            }   
            level--;
        }
    }
    
    private Component getComponent(String name) {
        for (Component component: componentList) {
            if (component.getName().equals(name)) {
                return component;
            }
        }
        return null;
    }
    
    private String getQuantityBill() {
        String quantityBill = "";
        
        Collections.sort(componentList);
        
        for (Component comp : componentList) {
            quantityBill += comp.getName() + ":" + comp.getAmount() + ";";
        }
        
        if (quantityBill.isEmpty()) {
            return quantityBill;
        } else {
            return quantityBill.substring(0, quantityBill.length() - 1);
        }
    }
    
    private void addToList(String asmUnder) {
        
        if (componentList.contains(getComponent(asmUnder))) {
            long newAmount = getComponent(asmUnder).getAmount() + levelMultiplicator.get(level);
            getComponent(asmUnder).setAmount(newAmount);
        } else {
            Component component = new Component(asmUnder, levelMultiplicator.get(level));
            componentList.add(component);
        }
    }
}
 