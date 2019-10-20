package edu.kit.informatik;

/**
 * This class represents the bill of material and contains all assemblies in the system.
 * 
 * @author 
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class BillOfMaterial {
    
    private List<Assembly> bom = new ArrayList<>();

    /**
     * Defines a new assembly and safes it in the bom-array.
     * 
     * @param nameAsm name of the assembly
     * @param prts parts of the assembly
     */
    public void addAssembly(String nameAsm, TreeMap<String, Integer> prts) {
       
        Assembly asm = new Assembly(nameAsm, prts);
        bom.add(asm);
    }

    /**
     * Removes an assembly from the system.
     * 
     * @param nameAsm name of the assembly
     */
    public void removeAssembly(String nameAsm) {
        
        bom.remove(fetchAsm(nameAsm));      
    }
    
    /**
     * Checks if a assembly exist in the system.
     * 
     * @param asmName name of the assembly
     * @return true if the assembly exists
     */
    public boolean asmExists(String asmName) {
       
        if (fetchAsm(asmName) != null) {
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a part exist in the system.
     * 
     * @param namePrt name of the part
     * @return true if the part exists
     */
    public boolean partExist(String namePrt) {
        for (Assembly assembly: bom) {
            for (String component: assembly.getParts().keySet()) {
                if (component.equals(namePrt)) {
                    return true;
                }
            }
        }
        return false;
    } 
    
    /**
     * Method to print the BOM of a assembly.
     * 
     * @param nameAsm name of the assembly
     * @return the BOM of assembly asmName
     */
    public String printAssembly(String nameAsm) {
        String assembly = "";
        Assembly asm = fetchAsm(nameAsm);
        
        for (String component: asm.getParts().keySet()) {
            
            assembly += component + ":" + asm.getParts().get(component) + ";";
        }
        return assembly.substring(0, assembly.length() - 1);
    }
    
    /**
     * Adds a part in a assembly.
     * 
     * @param nameAsm name of the assembly
     * @param amount of the part
     * @param namePrt name of the part
     */
    public void addPart(String nameAsm, int amount, String namePrt) {
        if (partExistInAssembly(nameAsm, namePrt)) {
            int newAmount = fetchAsm(nameAsm).getParts().get(namePrt) + amount;
            fetchAsm(nameAsm).getParts().put(namePrt, newAmount);
        } else {
            fetchAsm(nameAsm).getParts().put(namePrt, amount);
        }
    }
    
    /**
     * Removes a part in a assembly.
     * 
     * @param nameAsm name of the assembly
     * @param amount of the part
     * @param namePrt name of the part
     */
    public void removePart(String nameAsm, int amount, String namePrt) {
        
        int oldAmount = fetchAsm(nameAsm).getParts().get(namePrt);
        int newAmount = oldAmount - amount;
        
        if (newAmount == 0) {
            fetchAsm(nameAsm).getParts().remove(namePrt);
        } else {
            fetchAsm(nameAsm).getParts().put(namePrt, newAmount);
        }
        
        if (fetchAsm(nameAsm).getParts().size() == 0) {
            removeAssembly(nameAsm);
        }
    }
    
    /**
     * Checks if a assembly contains a certain part.
     * 
     * @param nameAsm name of assembly
     * @param namePrt name of part
     * @return true if namePrt is in nameAsm
     */
    public boolean partExistInAssembly(String nameAsm, String namePrt) {
        if (fetchAsm(nameAsm).getParts().containsKey(namePrt)) {
            return true;
        }
        return false;
    }
    
    /**
     * To get the amount of a part in assembly.
     * 
     * @param nameAsm name of assembly
     * @param namePrt name of part
     * @return the amount of a part
     */
    public int fetchPartAmount(String nameAsm, String namePrt) {
        return fetchAsm(nameAsm).getParts().get(namePrt);
    }
    
    /**
     * Checks if parts could be removed. 
     * 
     * @param nameAsm name of the assembly
     * @param amount of the part
     * @param namePrt name of the part
     * @return true if the amount to remove is as big as or smaller than the existing amount
     */
    public boolean hasEnoughAmount(String nameAsm, int amount, String namePrt) {
        if (fetchAsm(nameAsm).getParts().get(namePrt) >= amount) {
            return true;
        }
        return false;
    }

    /**
     * To get a assembly object by its name.
     * 
     * @param nameAsm name of the assembly
     * @return the assembly object
     */
    public Assembly fetchAsm(String nameAsm) {
        
        for (Assembly assembly: bom) {
            if (assembly.getName().equals(nameAsm)) {
                return assembly;
            }
        }
        return null;
    }   
    
    /**
     * Returns all assemblies. Used in the QantityBill and the ProductStructure classes.
     * @return all assemblies in the system
     */
    public List<Assembly> getBom() {
        return bom;
    }
}