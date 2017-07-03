import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

//It's common naming practice to call the template for your root route index
    get("/", (request,response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      //retrieving the task from the session, and placing it in model under the key "task".
      model.put("task", request.session().attribute("task"));
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      //create our HashMap named model:
      Map<String, Object> model = new HashMap<String, Object>();

      // fetch the user-inputted task description from the form and save it into a String with the line String description = request.queryParams("description");.
      String description = request.queryParams("description");

      //use our Task constructor to create a new Task with the user's provided description:
      Task newTask = new Task(description);

      //save our Task object into the user's session with request.session().attribute("task", newTask);
      request.session().attribute("task", newTask);

      //we render a success page that informs our user they've successfully added a new task to their list.
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
