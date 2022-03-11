package com.maxtrain.bootcamp.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;









@CrossOrigin
@RestController
@RequestMapping("/api/invoices")

public class InvoiceController {
	
	@Autowired
	private InvoiceRepository invRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Invoice>> getInvoices() {
		var inv = invRepo.findAll();
		return new ResponseEntity<Iterable<Invoice>>(inv, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Invoice> getOrders(@PathVariable int id){
		var inv = invRepo.findById(id);
		if(inv.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Invoice>(inv.get(), HttpStatus.OK);
	}
	
	//GET ALL ORDERS WHERE STATUS IS "REVIEW"
		@GetMapping("reviews")
		public ResponseEntity<Iterable<Invoice>>getInvoicesInReview(){
			var inv = invRepo.findByStatus("REVIEW");
			return new ResponseEntity<Iterable<Invoice>>(inv, HttpStatus.OK);
		}
	
	@PostMapping
	public ResponseEntity<Invoice> postInvoice(@RequestBody Invoice invoice){
		if(invoice == null || invoice.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var inv = invRepo.save(invoice);
		return new ResponseEntity<Invoice>(HttpStatus.CREATED);	
	}
	
	// Sets status to approved if total is under 50 or review if it is over 50
		@SuppressWarnings("rawtypes")
		@PutMapping("review/{id}")
		public ResponseEntity reviewInvoice(@PathVariable int id, @RequestBody Invoice invoice) {
			var statusValue= (invoice.getTotal() <= 50) ? "APPROVED" : "REVIEW";
			invoice.setStatus(statusValue);
			return putInvoice(id, invoice);
		}
		
		// APPROVING AN INVOICE
		@SuppressWarnings("rawtypes")
		@PutMapping("approve/{id}")
		public ResponseEntity approveInvoice(@PathVariable int id, @RequestBody Invoice invoice) {
			invoice.setStatus("APPROVED");
			return putInvoice(id, invoice);
		}
		//REJECTING AN INVOICE
		@SuppressWarnings("rawtypes")
		@PutMapping("reject/{id}")
		public ResponseEntity rejectInvoice(@PathVariable int id, @RequestBody Invoice invoice) {
			invoice.setStatus("REJECTED");
			return putInvoice(id, invoice);
		}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity putInvoice(@PathVariable int id, @RequestBody Invoice invoice) {
		if(invoice == null || invoice.getId()== 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var inv = invRepo.findById(invoice.getId());
		if(inv.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		invRepo.save(invoice);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity deleteInvoice(@PathVariable int id) {
		var inv = invRepo.findById(id);
		if(inv.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);			
		}
		invRepo.delete(inv.get());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);		
	}
	

}
