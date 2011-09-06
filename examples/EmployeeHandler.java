/*
 * Created on Feb 21, 2005
 * 
 * To change the template for this generated file go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and
 * Comments
 */
package examples;

import com.blackbear.flatworm.MatchedRecord;

/**
 * @author e50633
 * 
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code
 * and Comments
 */
public class EmployeeHandler
{

    public void handleNewhire(MatchedRecord results)
    {
        Employee employee = (Employee) results.getBean("employee");

        System.out.println("Handling Employee\n - " + employee);
    }

    public void handleException(String exception, String lastLine)
    {

        System.out.println("HandlingException\n - " + exception + "\n - " + lastLine);
    }

}
