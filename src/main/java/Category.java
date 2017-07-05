import java.util.List;
import java.util.ArrayList;

//Here, we added a member property mTasks. It is an ArrayList that will eventually hold Task objects belong to each instance of Category. For instance, a Category named "Epicodus" might contain a Task object with an mDescription property reading "Complete objects within objects Java homework.".
public class Category {
  private String mName;
  private static List<Category> instances = new ArrayList<Category>();
  private int mId;
  private List<Task> mTasks;

//We also initialize an empty List<Task> in the Category constructor, since every single Category must contain an attribute to hold corresponding Tasks.
  public Category(String name) {
    mName = name;
    instances.add(this);
    mId = instances.size();
    mTasks = new ArrayList<Task>();
  }

  public String getName() {
    return mName;
  }

  public static List<Category> all() {
    return instances;
  }

  public static void clear() {
    instances.clear();
  }

  public int getId() {
    return mId;
  }

  public static Category find(int id) {
    try {
      return instances.get(id - 1);
    } catch (IndexOutOfBoundsException exception) {
      return null;
    }
  }

  //since our mTasks list is private, we need public getter methods to retrieve it. We've added a getTasks() method that returns a Category's list of Tasks.
  public List<Task> getTasks() {
    return mTasks;
  }

  public void addTask(Task task) {
    mTasks.add(task);
  }
}
