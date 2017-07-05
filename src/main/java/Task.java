import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {
  private String mDescription;
  private boolean mCompleted;
  private LocalDateTime mCreatedAt;
  //Create a private static variable ArrayList named instances. This will eventually contain every Task object we create.
  private static List<Task> instances = new ArrayList<Task>();
  private int mId;

  public Task(String description) {
    mDescription = description;
    mCompleted = false;
    mCreatedAt = LocalDateTime.now();
    //Add every new Task object to our static instances ArrayList with the following line:
    instances.add(this);
    mId = instances.size();
  }

  public String getDescription() {
    return mDescription;
  }

  public boolean isCompleted() {
    return mCompleted;
  }

  public LocalDateTime getCreatedAt() {
    return mCreatedAt;
  }

  //Add a public static method to retrieve the list of all Tasks. Because this method is static, it must be called upon the Task class itself; not a particular instance. Like this: Task.all()
  public static List<Task> all() {
    return instances;
  }

  //a static clear() method to empty the instances ArrayList:
  //clear() is static because clearing all Task objects will occur on the class-level, not the instance-level.
  public static void clear() {
    instances.clear();
  }

  public int getId() {
    return mId;
  }

  //another static method to locate a Task using its mId property. This method is static because it must sift through all Task objects to find the specific one we're seeking. And, because it is static, we will call it on the entire class, like this: Task.find()
  public static Task find(int id) {
    return instances.get(id - 1);
  }
}
