package docuSign;

import static org.junit.Assert.*;

import org.junit.Test;

public class OperationTest {

	@Test
	public void test() {
		
		System.out.println("Test: basic validate().");
		Operation opt = Operation.createDefaultOpt("HOT", "Removing PJs", "leaving house", "fail");
		int[] optList = new int[]{8, 6, 4, 2, 1, 7};
		String[] res = new String[]{"Removing PJs", "shorts", "t-shirt", "sun visor", "sandals", "leaving house"};		
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		System.out.println("Extra Test: initMode(), addPrerequisite(), removePrerequisite(), clearPrerequisites()");
		//opt = new Operation("COLD", "Removing PJs", "leaving house");
		opt.initMode("COLD");		
		opt.removeAllPrerequisites();
		opt.getCommand(1).addPrerequisite(opt.getCommand(3));
		opt.getCommand(1).addPrerequisite(opt.getCommand(6));
		opt.getCommand(5).addPrerequisite(opt.getCommand(4));
				
		optList = new int[]{8, 6, 3, 4, 2, 5, 1, 7};
		res = new String[]{"Removing PJs", "pants", "socks", "shirt", "hat", "jacket", "boots", "leaving house"};
		for (int i = 0; i < optList.length; i++) {			
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
				
		//opt = new Operation("HOT", "Removing PJs", "leaving house", "fail");
		opt.initMode("HOT");
		opt.getCommand(1).removePrerequisite(opt.getCommand(3));
		optList = new int[]{8, 6, 6};
		res = new String[]{"Removing PJs", "shorts", "fail"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		opt = Operation.createDefaultOpt("HOT", "Removing PJs", "leaving house", "fail");
		optList = new int[]{8, 6, 3};
		res = new String[]{"Removing PJs", "shorts", "fail"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		opt = Operation.createDefaultOpt("COLD", "Removing PJs", "leaving house", "fail");
		opt.addCommand(2, "Put on headwear", "sun visor", "hat");
		optList = new int[]{8, 6, 3, 4, 2, 5, 7};
		res = new String[]{"Removing PJs", "pants", "socks", "shirt", "hat", "jacket", "fail"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		opt = Operation.createDefaultOpt("COLD", "Removing PJs", "leaving house", "fail");
		optList = new int[]{6};
		res = new String[]{"fail"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		System.out.println("Test: removeCommand().");
		assertNotNull(opt.getCommand(2));
		opt.removeCommand(2);
		assertNull(opt.getCommand(2));
		
		System.out.println("Test: addCommand().");
		boolean b = opt.addCommand(2, "Put on headwear", "sun visor", "hat");
		assertEquals(b, true);
		b = opt.addCommand(2, "description", "hotRes", "coldRes");
		assertEquals(b, false);
		assertEquals(opt.getCommand(2).getDescription(), "Put on headwear");
		
		System.out.println("Extra tests.");
		opt = Operation.createDefaultOpt("COLD", "Removing PJs", "leaving house", "fail");
		opt.addCommand(0, "Put on underwear", "jockstrap", "boxer");
		opt.getCommand(1).addPrerequisite(opt.getCommand(0));
		opt.getCommand(6).addPrerequisite(opt.getCommand(0));		
		optList = new int[]{8, 0, 6, 4, 5, 3, 2, 1, 7};
		res = new String[]{"Removing PJs", "boxer", "pants", "shirt", "jacket", "socks", "hat", "boots", "leaving house"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		opt.initMode("COLD");
		optList = new int[]{8, 3, 2, 6, 0, 4, 5, 1, 7};
		res = new String[]{"Removing PJs", "socks", "hat", "fail"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			if ("fail".equals(ret)) break;
			assertEquals(res[i], ret);
		}
		
		opt.initMode("COLD");
		opt.removeCommand(0);	
		optList = new int[]{8, 6, 4, 5, 3, 2, 1, 7};
		res = new String[]{"Removing PJs", "pants", "shirt", "jacket", "socks", "hat", "boots", "leaving house"};
		for (int i = 0; i < optList.length; i++) {
			String ret = opt.validate(i, 8, 7, optList[i], "fail");
			assertEquals(res[i], ret);
		}
		
		
	}

}
