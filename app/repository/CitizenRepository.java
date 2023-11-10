package repository;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.concurrent.CompletableFuture;
import io.ebean.DB;
import jakarta.inject.Inject;
import models.CitizenIDModel;


public class CitizenRepository {

	private final DatabaseExecutionContext databaseExecutionContext;

	@Inject
	public CitizenRepository(DatabaseExecutionContext databaseExecutionContext) {
		super();
		this.databaseExecutionContext = databaseExecutionContext;
	}
	
	 public CompletableFuture<CitizenIDModel> insert(CitizenIDModel citizen) {
	        return supplyAsync(() -> {
	   
	             DB.insert(citizen);
	             return citizen;
	        }, databaseExecutionContext)
	        		;
	    }
	
	
}
