package docuSign;

import static org.junit.Assert.*;

//import org.junit.runners.JUnit4;

public class CommandTest {

	@org.junit.Test
	public void test() {
		System.out.println("Comomand object created.");
		Command cmd = new Command.Builder(1, "This is Description").setHotRes("hot response").setColdRes("cold response").build();
		Command cmd1 = new Command.Builder(2, "yoyoyo").build();
		
		System.out.println("cmd1: " + cmd1.getHotRes());
		
		System.out.println("Test: getCmdIndex().");
		int index = cmd.getCmdIndex();
		assertEquals(index, 1);
		
		System.out.println("Test: getDescription().");
		String ret = cmd.getDescription();
		assertEquals(ret, "This is Description");
		
		System.out.println("Test: getHotRes().");
		String hotRes = cmd.getHotRes();
		assertEquals(hotRes, "hot response");
		hotRes = cmd1.getHotRes();
		assertEquals(hotRes, "");
		
		System.out.println("Test: getColdRes().");
		String coldRes = cmd.getColdRes();
		assertEquals(coldRes, "cold response");
		coldRes = cmd1.getColdRes();
		assertEquals(coldRes, "");
		
		System.out.println("Test: addPrerequisites(Command cmd).");
		assertEquals(false, cmd.getPrerequisite().contains(cmd1));
		cmd.addPrerequisite(cmd1);
		assertEquals(true, cmd.getPrerequisite().contains(cmd1));
		
		System.out.println("Test: checkPrerequisites().");
		assertEquals(false, cmd.checkPrerequisite());
		cmd1.setIsDone(true);
		assertEquals(true, cmd.checkPrerequisite());
		
		System.out.println("Test: removePrerequisites(Command cmd).");
		assertEquals(true, cmd.getPrerequisite().contains(cmd1));
		cmd.removePrerequisite(cmd1);
		assertEquals(false, cmd.getPrerequisite().contains(cmd1));
				
		
	}

}
