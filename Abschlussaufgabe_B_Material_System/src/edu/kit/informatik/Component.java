package edu.kit.informatik;

/**
 * This class represents a part tuple (with name and amount).
 * It is used to get a sorted structure for the quantity bill.
 * 
 * @author 
 * @version 1.0
 */
public class Component implements Comparable<Component> {
    
    private String name;
    private long amount;
    
    /**
     * To create a new Component object.
     * 
     * @param name of the component
     * @param amount the component
     */
    public Component(String name, long amount) {
        this.name = name;
        this.amount = amount;
    }
    
    /**
     * To get the name of a component.
     * 
     * @return the name of a component
     */
    public String getName() {
        return name;
    }

    /**
     * To get the amount of a component.
     * 
     * @return the amount of a component
     */
    public long getAmount() {
        return amount;
    }

    /**
     * To set the amount of a component.
     * 
     * @param amount of a component
     */
    public void setAmount(long amount) {
        this.amount = amount;
    }

    // To sort firstly after the amount and secondly after the name.
    @Override
    public int compareTo(Component quant) {
        
        if (this.getAmount() < quant.getAmount()) {         
            return 1;
        } else if (this.getAmount() == quant.getAmount()) { 
            if (this.getName().compareTo(quant.getName()) > 0) {
                return 1;
            } else {
                return -1;
            } 
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Quantity [name=" + name + ", amount=" + amount + "]";
    }   
}