package docuSign;

import java.util.*;

public class Command {
	private String hotRes;
    private String coldRes;
    private Set<Command> prerequisites;
    private final int cmdIndex;
    private String description;
    private boolean isDone;
    private boolean isValid;
    
    private Command(Builder builder) {
        isDone = false;
        isValid = true;
        hotRes = builder.hotRes;
        coldRes = builder.coldRes;
        prerequisites = builder.prerequisites;
        cmdIndex = builder.cmdIndex;
        description = builder.description;
    }
    
    public void printCMD() {
    	System.out.println(cmdIndex + ", " + description + ", " + hotRes + ", " + coldRes + ", " + isDone + ", " + isValid + ", ");
    	System.out.print("prerequisites: ");
    	for (Command cmd : prerequisites) {
    		System.out.print(cmd.getCmdIndex() + " ");
    	}
    }
    
    public Set<Command> getPrerequisite() {
    	return prerequisites;
    }
    
    /**
     * Add another Command to the list of prerequisites of the current Command.
     * @param cmd
     */
    public void addPrerequisite(Command cmd) {
    	if (this != cmd && !prerequisites.contains(cmd)) {
    		prerequisites.add(cmd);
            this.setIsValid(false);
    	}        
    }
    
    /**
     * Remove parameter Command from the list of prerequisites of the current Command.
     * @param cmd
     */
    public void removePrerequisite(Command cmd) {
    	if (!prerequisites.contains(cmd)) return;
    	prerequisites.remove(cmd);
    	if (prerequisites.size() == 0) {
    		this.setIsValid(true);
    	}
    }
    
    /**
     * Clear all Commands in current prerequisites list.
     */
    public void clearPrerequisites() {
    	prerequisites.clear();
    }
    
    /**
     * Check if all prerequisites have been done.
     * @return True if isDone is true for every Command in prerequisites list, false otherwise.
     */
    public boolean checkPrerequisite() {
        for (Command ptr : prerequisites) {
        	if (ptr == null) {
        		prerequisites.remove(ptr);
        	}
        	if (prerequisites.size() == 0) return true;
            if (ptr.getIsDone() == false) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * @return Index number of current Command.
     */
    public int getCmdIndex() {
        return cmdIndex;
    }
    
    /**
     * 
     * @return Description of current Command.
     */
    public String getDescription() {
    	return description;
    }
    
    /**
     * Set description of current Command.
     * @param s description string.
     */
    public void setDescription(String s) {
    	description = s;
    }
    
    /**
     * Get boolean isValid value of current Command.
     * @return True if it is valid, False otherwise.
     */
    public boolean getIsValid() {
        return isValid;
    }
    
    /**
     * Set boolean value of isValid of current Command.
     * @param b Boolean value.
     */
    public void setIsValid(boolean b) {
        isValid = b;
    }
    
    /**
     * Get the boolean isDone value of current Command.
     * @return Boolean value of isDone.
     */
    public boolean getIsDone() {
        return isDone;
    }
    
    /**
     * Set boolean value of isDone of current Command.
     * @param b Boolean value
     */
    public void setIsDone(boolean b) {
        isDone = b;
    }
    
    /**
     * Get value of Hot Response of current Command.
     * @return Hot Response string.
     */
    public String getHotRes() {
        return hotRes;
    }
    
    /**
     * Get value of Cold Response of current Command.
     * @return Cold Response string.
     */
    public String getColdRes() {
        return coldRes;
    }
    
    /**
     * Set value of Hot Response of current Command.
     * @param s String value to be set.
     */
    public void setHotRes(String s) {
    	hotRes = s;
    }
    
    /**
     * Set value of Cold Response of current Command.
     * @param s String value to be set.
     */
    public void setcoldRes(String s) {
    	coldRes = s;
    }
    
    /**
     *  Using Builder pattern in order to set hotRes and coldRes separately as needed.
     * @author sun
     */
    public static class Builder {
        private boolean isValid;
        private String hotRes;
        private String coldRes;
        private Set<Command> prerequisites;
        private final int cmdIndex;
        private String description;        
        
        public Builder(int cmdIndex, String description) { 
            hotRes = "";
            coldRes = "";
            isValid = true;
            prerequisites = new HashSet<>();
            this.cmdIndex = cmdIndex;
            this.description = description;
        }
        
        public Builder setHotRes(String s) {
            hotRes = s;
            return this;
        }
        
        public Builder setColdRes(String s) {
            coldRes = s;
            return this;
        }                  
        
        public Builder setIsValid(boolean b) {
            isValid = b;
            return this;
        }
        
        public Command build() {
            return new Command(this);
        }
    }
}
