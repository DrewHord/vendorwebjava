package com.maxtrain.bootcamp.product;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>>getProducts(){
		var prod = prodRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(prod, HttpStatus.OK);		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product>getProduct(int id){
		var prod = prodRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(prod.get(), HttpStatus.OK);
	}
	//FIND PRODUCT BY PART NBR
	@GetMapping("partNbr/{partNbr}")
	public ResponseEntity<Product>getByPartNbr(@PathVariable String partNbr){
		var prod = prodRepo.findByPartNbr(partNbr);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(prod.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> postProduct(@RequestBody Product product)	{
		if(product == null || product.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var prod = prodRepo.save(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putProduct(@PathVariable int id, @RequestBody Product product) {
		if(product == null || product.getId()== 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var prod = prodRepo.findById(product.getId());
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prodRepo.save(product);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteProduct(@PathVariable int id) {
		var prod = prodRepo.findById(id);
		if(prod.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		prodRepo.delete(prod.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
	

}
