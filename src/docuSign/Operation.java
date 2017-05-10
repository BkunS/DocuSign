package docuSign;

import java.util.*;

public class Operation {
	private Map<Integer, Command> cmdMap;
    private String mode;
    private final String initRes;
    private final String endRes;
    private String failMsg;
    
    
    /** 
     * Factory Method for creating Operations with default commands.
     * @param mode "HOT" or "COLD"
     * @param initRes The initial state (first mandatory action) in the house which is "Removing PJs".
     * @param endRes The final state which is "leaving the house".
     * @param failMsg The string indicating failure of the command response.
     * @return new Operation with default commands.
     */
    public static Operation createDefaultOpt(String mode, String initRes, String endRes, String failMsg) {
    	Operation ret = new Operation(mode, initRes, endRes, failMsg);
    	
    	// Adding default list of commands and their rules.
        Command cmd1 = new Command.Builder(1, "Put on footwear").setHotRes("sandals").setColdRes("boots").build();
        Command cmd2 = new Command.Builder(2, "Put on headwear").setHotRes("sun visor").setColdRes("hat").build();
        Command cmd3 = new Command.Builder(3, "Put on socks").setHotRes("fail").setColdRes("socks").build();
        Command cmd4 = new Command.Builder(4, "Put on shirt").setHotRes("t-shirt").setColdRes("shirt").build();
        Command cmd5 = new Command.Builder(5, "Put on jacket").setHotRes("fail").setColdRes("jacket").build();
        Command cmd6 = new Command.Builder(6, "Put on pants").setHotRes("shorts").setColdRes("pants").build();
        
        if ("COLD".equals(mode)) cmd1.addPrerequisite(cmd3);
        cmd1.addPrerequisite(cmd6);
        cmd5.addPrerequisite(cmd4);
        
        ret.getCmdMap().put(cmd1.getCmdIndex(), cmd1);
        ret.getCmdMap().put(cmd2.getCmdIndex(), cmd2);
        ret.getCmdMap().put(cmd3.getCmdIndex(), cmd3);
        ret.getCmdMap().put(cmd4.getCmdIndex(), cmd4);
        ret.getCmdMap().put(cmd5.getCmdIndex(), cmd5);
        ret.getCmdMap().put(cmd6.getCmdIndex(), cmd6);
        
        if ("HOT".equals(mode)) {
            cmd3.setIsDone(true);
            cmd5.setIsDone(true);
        }
        
        return ret;
    }
    
    /**
     * Constructor of the Operations, describing the entire scenario of the problem.
     * @param mode "HOT" or "COLD"
     * @param initRes The initial state (first mandatory action) in the house which is "Removing PJs".
     * @param endRes The final state which is "leaving the house".
     * @param failMsg The string indicating failure of the command response.
     */
    public Operation(String mode, String initRes, String endRes, String failMsg) {
        this.mode = mode;
        this.initRes = initRes;
        this.endRes = endRes;
        this.failMsg = failMsg;        
        cmdMap = new HashMap<Integer, Command>();                       
    }
    
    /**
     * Get current mode.
     * @return current mode in String format
     */
    public String getMode() {    	
    	return this.mode;
    }
    
    /**
     * This method is used to switch mode from one to the other, and will restart current mode with fresh states.
     * It makes Operation object re-usable by resetting the conditions (isDone/isValid) to their initial states,
     * so that it will be unnecessary to create new Operation objects for new inputs every time.
     * @param mode
     * @return True if initMode successfully, False otherwise.
     */
    public boolean initMode(String mode) {
    	if (!"HOT".equals(mode.toUpperCase()) && !"COLD".equals(mode.toUpperCase())) return false;
    	this.mode = mode;
    	for (Command tmp : cmdMap.values()) {
    		switch(mode) {
    			case "COLD": 
    				if (tmp.getColdRes() != null && tmp.getColdRes() != failMsg) tmp.setIsDone(false);
    				else tmp.setIsDone(true);
    				break;
    			case "HOT":
    				if (tmp.getHotRes() != null && tmp.getHotRes() != failMsg) tmp.setIsDone(false);
    				else tmp.setIsDone(true);
    				break;
    		}    		
    	}
    	for (Command tmp : cmdMap.values()) {
    		tmp.setIsValid(tmp.checkPrerequisite());
    	}
    	return true;
    }    
    
    /**
     * Get cmdMap of the Operation.
     * @return HashMap<Integer, Command> of current Operation.
     */
    public Map<Integer, Command> getCmdMap() {
    	return this.cmdMap;
    }
    
    /**
     * Get Command by their index number.
     * @param cmdIndex Index number of the Command.
     * @return Command object
     */
    public Command getCommand(int cmdIndex) {
    	if (!cmdMap.containsKey(cmdIndex)) return null;
    	return cmdMap.get(cmdIndex);
    }
    
    /**
     * Add a new Command to Operation.
     * @param cmdIndex Index number of the Command
     * @param description Description of the Command
     * @param hotRes Hot response
     * @param coldRes Cold response
     * @return True when created successfully; false when otherwise.
     */
    public boolean addCommand(int cmdIndex, String description, String hotRes, String coldRes) {
    	if (cmdMap.containsKey(cmdIndex)) {
    		System.out.println("Failed to add new Command. Command with same Index number already exists.");
    		return false;
    	}
    	Command cmd = new Command.Builder(cmdIndex, description).setHotRes(hotRes).setColdRes(coldRes).build();
    	cmdMap.put(cmdIndex, cmd);
    	return true;
    }
    
    /**
     * Remove Command by its Index number.
     * @param cmdIndex Index number of the Command is about to be removed
     */
    public void removeCommand(int cmdIndex) {
    	if (!cmdMap.containsKey(cmdIndex)) return;
    	Command cmd = cmdMap.get(cmdIndex);
    	for (Command tmp : cmdMap.values()) {
    		tmp.removePrerequisite(cmd);
    	}
    	cmdMap.remove(cmdIndex);
    }
    
    /**
     * Removes all prerequisites of all Commands in current Operation.
     */
    public void removeAllPrerequisites() {
    	for (Command tmp : cmdMap.values()) {
    		tmp.clearPrerequisites();
    	}
    }
    
    /**
     * Validate input Command by its Index number.
     * @param i Order of the Command.
     * @param initIndex The input number of initial state.
     * @param endIndex The input number of finishing state.
     * @param num Input number.
     * @param failMsg The string indicating failure of the command response.
     * @return Response of the Command when the input number is valid, failMsg otherwise.
     */
    public String validate(int i, int initIndex, int endIndex, int num, String failMsg) {    	
    	if (i == 0 && num != initIndex) {
    		return failMsg;
    	}
    	
    	if (i == 0 && num == initIndex) {
    		return initRes;
    	} else {
    		if(num == 7) {
                for (Command tmp : cmdMap.values()) {
                    if (tmp.getIsDone() == false) {
                        return failMsg;
                    }
                }
                return endRes;
            } else {
                Command cmd;
                if (!cmdMap.containsKey(num)) {
                    return failMsg;
                }
                cmd = cmdMap.get(num);
                if (cmd.checkPrerequisite() == true && cmd.getIsDone() == false) {
                    cmd.setIsValid(false);
                    cmd.setIsDone(true);

                    switch(mode) {
                        case "HOT":
                        	if (cmd.getHotRes() == null) return failMsg;
                            return cmd.getHotRes();
                        case "COLD": 
                        	if (cmd.getColdRes() == null) {
                        		return failMsg;
                        	}
                            return cmd.getColdRes();
                    }
                } else {
                    return failMsg;
                }                
            }
            return failMsg;
    	}              
    }
}
