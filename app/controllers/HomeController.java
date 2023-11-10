package controllers;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.inject.Inject;
import models.CitizenIDModel;
import models.StudentModel;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.libs.concurrent.Futures;
import play.mvc.*;
import play.twirl.api.Content;
import repository.CitizenRepository;
import repository.StudentRepository;
import responses.ResponseHandler;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	
	 
	  private final StudentRepository studentRepository;
	  private final ClassLoaderExecutionContext classLoaderExecutionContext;
	  private final CitizenRepository citizenRepository;

	/**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok(views.html.index.render());
    }
    
    @Inject 
    public HomeController(FormFactory formFactory, StudentRepository studentRepository,
			ClassLoaderExecutionContext classLoaderExecutionContext, MessagesApi messagesApi, Futures futures, CitizenRepository citizenRepository) {
		
		
		this.studentRepository = studentRepository;
		this.classLoaderExecutionContext = classLoaderExecutionContext;
		this.citizenRepository = citizenRepository;
		
	}
    @BodyParser.Of(BodyParser.Json.class)
	public CompletionStage<Result> save(Http.Request request) {
        JsonNode json= request.body().asJson();
        if (json.isEmpty()  || json.isNull()) {
  
            return studentRepository.options().thenApplyAsync(companies -> {
                // This is the HTTP rendering thread context
                return badRequest(views.html.index.render());
            }, classLoaderExecutionContext.current());
        }
        CitizenIDModel citizenIDModel=new CitizenIDModel();
        citizenIDModel.setId(json.findPath("citizen").findPath("id").asLong());
        citizenIDModel.setAge(json.findPath("citizen").findPath("age").asInt());
        citizenIDModel.setMobileno(json.findPath("citizen").findPath("mobileno").asLong());
        citizenIDModel.setName(json.findPath("citizen").findPath("name").asText());
        citizenIDModel.setAddress(json.findPath("citizen").findPath("address").asText());
        
        StudentModel studentModel = new StudentModel();
        studentModel.setName(json.findPath("name").textValue());
        studentModel.setAge(json.findPath("age").intValue());
        studentModel.setCitizen(citizenIDModel);        
   citizenIDModel.setStudentmodel(studentModel);
        // Run insert db operation
        return studentRepository.insert(studentModel).thenApplyAsync(data -> {
            // This is the HTTP rendering thread context
            return Results.ok(ResponseHandler.sendResponse("Data Saved Successfully!", data, Http.Status.OK, true));
                
        }, classLoaderExecutionContext.current());
    }
	
	public CompletableFuture<Result> update( Http.Request request) {
		 JsonNode json= request.body().asJson();
		if (json.isEmpty() || json.isNull()){
			
			return studentRepository.error();
		}
		
		StudentModel studentModelData = new StudentModel();
		studentModelData.setName(json.findPath("name").textValue());
		studentModelData.setAge(json.findPath("age").intValue());
		studentModelData.setId(json.findPath("id").asLong());
		return studentRepository.update(studentModelData.getId(), studentModelData).thenApply((data) -> {
			if (data!=null) {
				return Results.ok(ResponseHandler.sendResponse("Data Updated Successfully!", data, Http.Status.OK, true));
			}
			return Results.badRequest(ResponseHandler.sendResponse("Data ID Not Found!", data, Http.Status.BAD_REQUEST, false));
		});
	
		
	}
	
	public CompletableFuture<Result> delete( Long id) {
	
		
		
		return studentRepository.delete(id).thenApply((data) -> {
			if (data!=null) {
				return Results.ok(ResponseHandler.sendResponse("Data Deleted Successfully!", null, Http.Status.OK, true));
			}
			return Results.badRequest(ResponseHandler.sendResponse("Data ID Not Found!", data, Http.Status.BAD_REQUEST, false));
		});
	
		
	}
public Result listData() throws InterruptedException, ExecutionException {

		
		CompletableFuture<Result> f=
		 studentRepository.listAll().thenApply((data) -> {
			if (data!=null) {
                 System.out.println("Data Listed!");
               
				return Results.ok( ResponseHandler.sendResponse("Data Listed Successfully!", data, Http.Status.OK, true));
			}
			return Results.badRequest(ResponseHandler.sendResponse("Empty DB", data, Http.Status.BAD_REQUEST, false));
		}).orTimeout(1L, TimeUnit.MILLISECONDS);
	return f.get();
		 
		
	}
@BodyParser.Of(BodyParser.Json.class)
	public CompletionStage<Result> saveFromChild(Http.Request request) {
       JsonNode json= request.body().asJson();
       if (json.isEmpty()  || json.isNull()) {
 
           return studentRepository.options().thenApplyAsync(companies -> {
               // This is the HTTP rendering thread context
               return badRequest(views.html.index.render());
           }, classLoaderExecutionContext.current());
       }
       CitizenIDModel citizenIDModel=new CitizenIDModel();
       citizenIDModel.setId(json.findPath("id").asLong());
       citizenIDModel.setAge(json.findPath("age").asInt());
       citizenIDModel.setMobileno(json.findPath("mobileno").asLong());
       citizenIDModel.setName(json.findPath("name").asText());
       citizenIDModel.setAddress(json.findPath("address").asText());
       
       StudentModel studentModel = new StudentModel();
       studentModel.setName(json.findPath("student").findPath("name").textValue());
       studentModel.setAge(json.findPath("student").findPath("age").intValue());
       studentModel.setCitizen(citizenIDModel);        

       citizenIDModel.setStudentmodel(studentModel);

       // Run insert db operation
       return citizenRepository.insert(citizenIDModel).thenApplyAsync(data -> {
           // This is the HTTP rendering thread context
           return Results.ok(ResponseHandler.sendResponse("Data Saved Successfully!", data, Http.Status.OK, true));
               
       }, classLoaderExecutionContext.current());
   }

}
