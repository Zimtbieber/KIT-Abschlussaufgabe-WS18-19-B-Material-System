package edu.kit.informatik;

import java.util.TreeMap;

import edu.kit.informatik.exception.SystemInputException;

/**
 * This class is building the connection between the material system and the UI.
 * 
 * @author 
 * @version 1.0
 */
public class Controller {

    private BillOfMaterial bom = new BillOfMaterial();
    private QuantityBill quantityBill = new QuantityBill(bom);
    private ProductStructure productStructure = new ProductStructure(bom);
      
    /**
     * This function adds a assembly to the system.
     * 
     * @param nameAsm name of the assembly
     * @param prts parts of the assembly
     * @throws SystemInputException - if a part appears twice in an assembly
     *                              - if the added BOM would create a cycle structure
     *                              - if the BOM already exists
     */
    public void addAssembly(String nameAsm, TreeMap<String, Integer> prts) throws SystemInputException {
                
        if (!bom.asmExists(nameAsm)) {
            
            String placeHolder = "";
            for (String part: prts.keySet()) {
                
                if (placeHolder.contains(part)) {
                    throw new SystemInputException("the names of the parts in the specified BOM " + nameAsm 
                                                    + " appear twice: " + part + ".");
                }
                placeHolder += part;
            }

            bom.addAssembly(nameAsm, prts);
            
            String cycleCheck = productStructure.checkForCycle();
            
            if (!cycleCheck.isEmpty()) {
                
                bom.removeAssembly(nameAsm);
                throw new SystemInputException("the specified BOM " + nameAsm + " would create a cycle "
                                                + "in the product structure: " + cycleCheck + ".");
            }
        } else {
            throw new SystemInputException("the assembly " + nameAsm + " already exits in the system.");
        }
    }
    
    /**
     * Removes an assembly from the system.
     * 
     * @param nameAsm name of the assembly
     * @throws SystemInputException if the assembly does not exist in the system
     */
    public void removeAssembly(String nameAsm) throws SystemInputException {
        
        if (bom.asmExists(nameAsm)) {
            bom.removeAssembly(nameAsm);
        } else {
            throw new SystemInputException("no BOM exists in the system for the specified name: " + nameAsm + ".");
        }
        
    }
    
    /**
     * Returns the bill of material of an assembly.
     * 
     * @param nameAsm name of the assembly
     * @return - the parts of the assembly or
     *         - COMPONENT if nameAsm was a part's name
     * @throws SystemInputException if none part or assembly exist with nameAsm
     */
    public String printAssembly(String nameAsm) throws SystemInputException {
       
        if (bom.asmExists(nameAsm)) { 
            return bom.printAssembly(nameAsm);
        } else if (bom.partExist(nameAsm)) {
            return "COMPONENT";
        } else {
            throw new SystemInputException("no assembly or part exists in the system " 
                                            + "with the specified name " + nameAsm + ".");
        }
    }
    
    /**
     * Returns all subassemblies of the quantity bill which are directly or 
     * indirectly assembled in the requested assembly.
     * 
     * @param nameAsm name of the assembly
     * @return - the subassemblies of the assembly or
     *         - EMPTY if the assembly contains only parts
     * @throws SystemInputException if no BOM nameAsm exist
     */
    public String getAssemblies(String nameAsm) throws SystemInputException {
        
        if (bom.asmExists(nameAsm)) {
            
            if (quantityBill.getQuantityBill(nameAsm, true).isEmpty()) {
                return "EMPTY";
            } 
            return quantityBill.getQuantityBill(nameAsm, true);            
        } else {
            throw new SystemInputException("no BOM exists in the system for the specified name: " + nameAsm + ".");
        }
    }
    
    /**
     * Returns all parts of the quantity bill which are directly or 
     * indirectly assembled in the requested assembly.
     * 
     * @param nameAsm name of the assembly
     * @return - the parts of the assembly or
     *         - EMPTY if the assembly contains only parts
     * @throws SystemInputException if no BOM nameAsm exist
     */
    public String getComponents(String nameAsm) throws SystemInputException {
        
        if (bom.asmExists(nameAsm)) {
            return quantityBill.getQuantityBill(nameAsm, false);
        }
        throw new SystemInputException("no BOM exists in the system for the specified name: " + nameAsm + "."); 
    }
    
    /**
     * Adds a part to an assembly. If the assembly already contains the part 
     * the amount could be increased up to 1000 pieces.
     *  
     * @param nameAsm name of the assembly
     * @param amount amount to add
     * @param namePrt name if the part
     * @throws SystemInputException - if the existing amount and the to add amount is higher than 1000
     *                              - if the part would create an cycle in the system
     *                              - if no BOM nameAsm exist
     */
    public void addPart(String nameAsm, int amount, String namePrt) throws SystemInputException {  
        
        if (bom.asmExists(nameAsm)) {
            
            if (bom.partExistInAssembly(nameAsm, namePrt) && bom.fetchPartAmount(nameAsm, namePrt) + amount > 1000) {
                throw new SystemInputException("the specified amount " + amount + " is to high.");
            }
            bom.addPart(nameAsm, amount, namePrt);
            String cycle = productStructure.checkForCycle();
                
            if (!cycle.isEmpty()) {
                bom.removePart(nameAsm, amount, namePrt);
                throw new SystemInputException("adding the part " + namePrt + " to the BOM " + nameAsm 
                                                + " would create a cycle in the structure: " + cycle + ".");
            } 
        } else {
            throw new SystemInputException("no BOM exists in the system for the specified name: " + nameAsm);
        }   
    }
    
    /**
     * Removes a part in an assembly.
     * 
     * @param nameAsm name of the assembly
     * @param amount amount to remove
     * @param namePrt name of the part
     * @throws SystemInputException - if the amount to remove is higher than the existing one
     *                              - if the BOM doesn't contain the part
     *                              - if no BOM nameAsm exist
     * 
     */
    public void removePart(String nameAsm, int amount, String namePrt) throws SystemInputException {
        
        if (bom.asmExists(nameAsm)) {
        
            if (bom.partExistInAssembly(nameAsm, namePrt)) {
                
                if (!bom.hasEnoughAmount(nameAsm, amount, namePrt)) {
                    throw new SystemInputException("the BOM " + nameAsm 
                                + " does not contain the specified amount " + amount + ".");
                }
                bom.removePart(nameAsm, amount, namePrt);
                
            } else {
                throw new SystemInputException("the Bom " + nameAsm 
                                + " does not contain the specified part " + namePrt + ".");
            }
        } else {
            throw new SystemInputException("no BOM exists in the system for the specified name: " + nameAsm);
        }
    }
}
