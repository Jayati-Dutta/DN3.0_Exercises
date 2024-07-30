public class Linked_list
{
    private class Node
    {
        Task task;
        Node next;
        Node(Task task)
        {
            this.task=task;
            this.next=null;
        }
    }
    private Node head;
    public Linked_list()
    {
        this.head=null;
    }
    void addTask(Task task)
    {
        Node newNode=new Node(task);
        if(head==null)
            head = newNode;
        else
        {
            Node current=head;
            while(current.next!=null)
                current=current.next;
            current.next=newNode;
        }
    }
    public Task searchTask(String taskId)
    {
        Node current=head;
        while(current!=null)
        {
            if(current.task.getTaskId().equals(taskId))
                return current.task;
            current=current.next;
        }
        return null;
    }
    public void traverseTasks()
    {
        Node current=head;
        while(current!=null)
        {
            System.out.println(current.task);
            current=current.next;
        }
    }
    public void deleteTask(String taskId)
    {
        if(head==null)
        {
            System.out.println("List is empty.");
            return;
        }
        if(head.task.getTaskId().equals(taskId))
        {
            head=head.next;
            return;
        }
        Node current=head;
        while(current.next!=null&&!current.next.task.getTaskId().equals(taskId))
            current=current.next;
        if(current.next==null)
            System.out.println("Task not found.");
            else
                current.next=current.next.next;
    }
    public static void main(String[] args)
    {
        Linked_list ll=new Linked_list();
        Task t1=new Task("1", "Design system architecture", "Pending");
        Task t2=new Task("2", "Implement login feature", "In Progress");
        Task t3=new Task("3", "Test application", "Pending");
        ll.addTask(t1);
        ll.addTask(t2);
        ll.addTask(t3);
        System.out.println("All Tasks:");
        ll.traverseTasks();
        System.out.println("\nSearching for Task with ID 2:");
        Task searchedTask=ll.searchTask("2");
        System.out.println(searchedTask!=null?searchedTask:"Task not found");
        System.out.println("\nDeleting Task with ID 2:");
        ll.deleteTask("2");
        System.out.println("\nAll Tasks after deletion:");
        ll.traverseTasks();
    }
}