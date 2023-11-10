package repository;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.ebean.DB;
import io.ebean.Transaction;

import static java.util.concurrent.CompletableFuture.supplyAsync;


import jakarta.inject.Inject;
import models.StudentModel;
import play.mvc.Result;
import play.mvc.Results;

public class StudentRepository {
	
	private final DatabaseExecutionContext databaseExecutionContext;

	@Inject
	public StudentRepository(DatabaseExecutionContext databaseExecutionContext) {
		
		this.databaseExecutionContext = databaseExecutionContext;
	}
	
	public CompletionStage<Map<String, String>> options(){
		return supplyAsync(() -> {
			return DB.find(StudentModel.class).findList();
				
		},databaseExecutionContext)
				  .thenApply(list -> {
	                    HashMap<String, String> options = new LinkedHashMap<String, String>();
	                    for (StudentModel c : list) {
	                        options.put(c.getId().toString(), c.getName());
	                    }
	                    return options;
	                });
	}
	 public CompletableFuture<StudentModel> insert(StudentModel studentModel) {
	        return supplyAsync(() -> {
	   
	             DB.insert(studentModel);
	             return studentModel;
	        }, databaseExecutionContext)
	        		;
	    }
	 
	 public CompletableFuture<StudentModel> update(Long id,StudentModel studentModel){
		 
		 return supplyAsync(() -> {
			 Transaction txTransaction=DB.beginTransaction();
			 try {
				StudentModel savedstudentModel=DB.find(StudentModel.class)
						.setId(id)
						.findOne();
				if (savedstudentModel!=null) {
					savedstudentModel.setName(studentModel.getName());
					savedstudentModel.setAge(studentModel.getAge());
					savedstudentModel.update();
					txTransaction.commit();
					System.out.println("Data Updated");
					return savedstudentModel;
				}else {
					System.out.println("ID:"+id+" Not Found");
					return null;
				}
				
			} finally {
				txTransaction.end();
				
			}
			
		}, databaseExecutionContext);
	 }
	 public CompletableFuture<Result> error(){
		 return supplyAsync(() -> {
			 System.out.println("Error Occured!");
			 return Results.badRequest("Error Occured!");
		 });
	 }
	 public CompletableFuture<Long> delete(Long id) {
		
		 return supplyAsync(() -> {
			
			 try {
				StudentModel studentModel =DB.find(StudentModel.class).setId(id)
											.findOne();
				if (studentModel!=null) {
					DB.delete(studentModel);
				}else {
					System.out.println("ID:"+ id+" Not Found!");
					return null;
				}
			} finally {
				DB.endTransaction();
			}
			 return id;
		 }, databaseExecutionContext);
	}
	 
	 public CompletableFuture<List<StudentModel>> listAll(){
		 return supplyAsync(() -> {
			 
			 try {
				List<StudentModel> listOfModels=DB.find(StudentModel.class)
						.findList();
				return listOfModels;
			} catch(Exception e) {
				// TODO: handle finally clause
				System.out.println("Exception Occured:"+e.getMessage());
				return null;
			}
		 }, databaseExecutionContext);
	 }
 }
	
	 

