/**
 * Created by IntelliJ IDEA. User: james Date: Sep 18, 2003 Time: 2:56:46 AM To change this template use Options | File
 * Templates.
 */
public class Employee
{
    private String firstName;

    private String lastName;

    private String ssn;

    private double salary;

    private String gender;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getSsn()
    {
        return ssn;
    }

    public void setSsn(String ssn)
    {
        this.ssn = ssn;
    }

    public double getSalary()
    {
        return salary;
    }

    public void setSalary(double salary)
    {
        this.salary = salary;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String toString()
    {
        return super.toString() + "[" + this.lastName + ", " + this.firstName + ", " + this.ssn + ", " + this.gender
                + ", " + this.salary + "]";
    }
}
