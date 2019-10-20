package edu.kit.informatik;

import java.util.TreeMap;

/**
 * This class represents a assembly for the material requirement system.
 * 
 * @author  
 * @version 1.0
 */
public class Assembly {
    
    private String name;
    private TreeMap<String, Integer> parts;
    
    /**
     * This constructor defines a new assembly and assign it with a name and its parts.
     * 
     * @param name the name of the assembly
     * @param parts the parts of the assembly
     */
    public Assembly(String name, TreeMap<String, Integer> parts) {
        this.name = name;
        this.parts = parts;   
    }

    /**
     * To get the name of the assembly.
     * 
     * @return the name of the assembly
     */
    public String getName() {
        return name;
    }

    /**
     * To get the parts of the assembly.
     * 
     * @return the parts of the assembly
     */
    public TreeMap<String, Integer> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        return "Assembly [name=" + name + ", parts=" + parts + "]";
    }
}
