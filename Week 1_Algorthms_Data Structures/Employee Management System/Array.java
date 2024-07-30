public class Array
{
    private Employee[] employees;
    private int size;
    public Array(int capacity)
    {
        employees=new Employee[capacity];
        size=0;
    }
    public void addEmployee(Employee employee)
    {
        if (size==employees.length)
        {
            System.out.println("Array is full. Cannot add more employees.");
            return;
        }
        employees[size++]=employee;
    }
    public Employee searchEmployee(String employeeId)
    {
        for(int i=0;i<size;i++)
        {
            if (employees[i].getEmployeeId().equals(employeeId))
                return employees[i];
        }
        return null;
    }
    public void traverseEmployees()
    {
        for(int i=0;i<size;i++)
            System.out.println(employees[i]);
    }
    public void deleteEmployee(String employeeId)
    {
        int index=-1;
        for(int i=0;i<size;i++)
        {
            if(employees[i].getEmployeeId().equals(employeeId))
            {
                index=i;
                break;
            }
        }
        if(index==-1)
        {
            System.out.println("Employee not found.");
            return;
        }
        for(int i=index;i<size-1;i++)
            employees[i]=employees[i+1];
        employees[--size]=null;
    }
    public static void main(String[] args)
    {
        Array arr=new Array(5);
        Employee emp1=new Employee("1", "Alice", "Manager", 75000);
        Employee emp2=new Employee("2", "Bob", "Developer", 60000);
        Employee emp3=new Employee("3", "Charlie", "Analyst", 50000);
        arr.addEmployee(emp1);
        arr.addEmployee(emp2);
        arr.addEmployee(emp3);
        System.out.println("All Employees:");
        arr.traverseEmployees();
        System.out.println("\nSearching for Employee with ID 2:");
        Employee searchedEmployee=arr.searchEmployee("2");
        System.out.println(searchedEmployee!=null?searchedEmployee:"Employee not found");
        System.out.println("\nDeleting Employee with ID 2:");
        arr.deleteEmployee("2");
        System.out.println("\nAll Employees after deletion:");
        arr.traverseEmployees();
    }
}