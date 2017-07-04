import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.List;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

//It's common naming practice to call the template for your root route index
    get("/", (request,response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      //retrieving the task from the session, and placing it in model under the key "task".
      model.put("tasks", request.session().attribute("tasks"));
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      //create our HashMap named model:
      Map<String, Object> model = new HashMap<String, Object>();

      ArrayList<Task> tasks = request.session().attribute("tasks");
      //if conditional that attempts to retrieve an ArrayList from the session saved under the key "tasks". If that ArrayList does not exist yet, we create a new one and add it to the session:
      if (tasks == null) {
        tasks = new ArrayList<Task>();
        request.session().attribute("tasks", tasks);
      }

      // fetch the user-inputted task description from the form and save it into a String with the line String description = request.queryParams("description");.
      String description = request.queryParams("description");

      //we create our Task object and add it into the tasks ArrayList with: tasks.add(newTask)
      Task newTask = new Task(description);
      tasks.add(newTask);

      //we render a success page that informs our user they've successfully added a new task to their list.
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
