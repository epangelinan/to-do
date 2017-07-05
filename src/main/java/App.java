import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    // Our get("/") and get("/tasks/new") routes simply render templates (the welcome page and the new task form, respectively)
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("tasks/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/task-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //In our get("/tasks") route, we use our new method Task.all() to place the ArrayList of all Tasks into the model, thereby making it available to the template rendered in this route (tasks.vtl). In that template we can iterate through each Task and display whatever property we'd like.
    get("/tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", Task.all());
      model.put("template", "templates/tasks.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //Our post("/tasks") route is where the form submits to (because its action and method attributes look like this: <form action="/tasks" method="post">). This route handles gathering info from the form and creating the Task, which automatically adds it to the list of all Tasks.
    post("/tasks", (request,response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String description = request.queryParams("description");
      Task newTask = new Task(description);
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //This route will be executed when someone clicks a link to see a particular Task's detail page. That is, the links being dynamically created in the Velocity loop in the tasks.vtl template
    //The :id portion of this route is a placeholder, because this route will be executed for viewing any Task's detail page. As we discussed, clicking on the first Task in the list will result in a route that looks like "/tasks/1". When that occurs, :id will represent 1. If we clicked on the 23rd Task in the list it would create a link that looked like "/tasks/23". After the user executed that GET request, :id would represent 23.
    //In the line Task task = Task.find(Integer.parseInt(request.params(":id")));, the request.params(":id") portion retrieves the value currently represented by :id. It retrieves this information from the link we clicked to execute this route. So, if we clicked on the fifth Task in our list, the link would be /tasks/5. When this route executed, request.params(":id") would return 5.
    //Since the value retrieved from :id is a String, we need to use Integer.parseInt() to convert it to an int. Then we use our Task.find() method to retrieve the Task whose mId matches the :id. That is, the Task a user is requesting to view. When we locate this object, we assign it to t he variable task, and place it into our model.
    //When the corresponding task.vtl template loads, it will then display the details for that particular Task
    get("/tasks/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Task task = Task.find(Integer.parseInt(request.params(":id")));
      model.put("task", task);
      model.put("template", "templates/task.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
