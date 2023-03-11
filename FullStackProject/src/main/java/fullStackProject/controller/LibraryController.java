package fullStackProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fullStackProject.exception.ResourceNotFoundException;
import fullStackProject.model.Library;
import fullStackProject.repository.LibraryRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
/*@ResponseBody-tells a controller that the object returned is automatically 
 * serialized into JSON and passed back into the HttpResponse object.
 * @Controller->The dispatcher will scan such annotated classes for mapped methods.*/
@RequestMapping("/FullStackproject")
//@RequestMapping Annotation which is used to map HTTP requests to handler methods REST controllers
public class LibraryController {

	@Autowired
	/*is used for automatic dependency injection. 
	If we mention @Autowired over the attribute,no need to write the setter methods for injection*/
	private LibraryRepository libraryRepository;

	
	//CREATE RECORD REST API
	@PostMapping("/saveLibraryRecords")
	/*@PostMapping annotation maps HTTP POST requests onto specific handler methods.
	Shortcut for-->@RequestMapping(method = RequestMethod.POST)*/
	public Library createLibraryRecords(@RequestBody Library library) {
		return libraryRepository.save(library);
	}

	//GET RECORDS REST API
	@GetMapping("/libraryRecords")
	//@GetMapping annotated methods handle the HTTP GET requests matched with the given URI expression.
	public List<Library> getRecordsFromDb(){
		return libraryRepository.findAll();
	}	
	
	//GET RECORD BY BOOKID REST API
	@GetMapping("/libraryRecords/{BookId}")
	//@GetMapping annotated methods handle the HTTP GET requests matched with the given URI expression
	/*@PathVariable-->annotation which indicates that a method parameter should be bound to a URI template variable.
	  If we want to extract particular record by its ID,then we can use @PathVariable*/
	public ResponseEntity<Library> searchById(@PathVariable int BookId) {
		Library library = libraryRepository.findById(BookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id :" + BookId));
		return ResponseEntity.ok(library);
	}

	//UPDATE RECORDS BY BOOKID
	@PutMapping("/updateRecords/{BookId}")
	//@PutMapping annotated methods handle the HTTP PUT requests matched with the given URI expression
	/*@RequestBody used to convert JSON to Java Objects
	 Spring automatically deserializes the JSON into a Java type*/
	public ResponseEntity<Library> updateBookDetails(@PathVariable int BookId, @RequestBody Library libraryRecordDetails){
		Library library = libraryRepository.findById(BookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id :" + BookId));

		library.setNoOfCopies(libraryRecordDetails.getNoOfCopies());

		Library updatedRecords = libraryRepository.save(library);
		return ResponseEntity.ok(updatedRecords);
	}

	//DELETE RECORD BY BOOK ID
	@DeleteMapping("/deleteRecords/{BookId}")
	//@DeleteMapping annotated methods handle the HTTP DELETE requests matched with the given URI expression
	//@ResponseEntity represents the whole HTTP response: status code.we can use it to fully configure the HTTP response.
	public ResponseEntity<Map<String, Boolean>> deleteRecord(@PathVariable int BookId){
		Library library = libraryRepository.findById(BookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id :" + BookId));

		libraryRepository.delete(library);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}


}





