package docuSign;

import java.util.Scanner;

public class Problem {

	public static void main(String[] args) {
		System.out.println("Input: ");
		String input = "";

		// Define essential parameters for the problem scenario.
        int initIndex = 8;
        int endIndex = 7;        
        String initRes = "Removing PJs";
        String endRes = "leaving house";
        String failMsg = "fail";
        String cond1 = "HOT";
        String cond2 = "COLD";
        
        // Scan input.
        Scanner scan = new Scanner(System.in);
        input = scan.nextLine();
        scan.close();
                
        int ptr = input.indexOf(" ");
        String mode = null;
        if (ptr >= 0) {
        	mode = input.substring(0, ptr).toUpperCase();  
        }
        if (!cond1.equals(mode) && !cond2.equals(mode)) {
            System.out.println("fail");
            return;
        }
        
        // Create Operation object describing problem scenario.
        Operation opt = Operation.createDefaultOpt(mode, initRes, endRes, failMsg);
        String[] list = input.substring(input.indexOf(" ") + 1).split(",");  
        
        for (int i = 0; i < list.length; i++) {
            int cmdIndex = Integer.valueOf(list[i].trim());
            String ret = opt.validate(i, initIndex, endIndex, cmdIndex, failMsg);
            System.out.print(ret);
            if (failMsg.equals(ret) || endRes.equals(ret)) {
                System.out.println();
                return;
            } else {
                System.out.print(", ");
            }
        }
	}

}
