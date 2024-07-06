package com.Ecommerce.controller;

import com.Ecommerce.dto.ProductDto;
import com.Ecommerce.service.ProductServiceIMPL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Product")
public class ProductController {
    private ProductServiceIMPL productServiceIMPL;

    public ProductController(ProductServiceIMPL productServiceIMPL) {
        this.productServiceIMPL = productServiceIMPL;
    }
    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto>addProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = productServiceIMPL.addProduct(productDto);
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }
    @DeleteMapping
    public ResponseEntity<String>deleteProduct(@RequestParam long productId){
        productServiceIMPL.deleteProduct(productId);
        return new ResponseEntity<>("Record Deleted",HttpStatus.OK);
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto>updateProduct(
            @PathVariable long productId,@RequestBody ProductDto productDto){
        ProductDto productDto1 = productServiceIMPL.updateProduct(productId, productDto);
        return new ResponseEntity<>(productDto1,HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDto>>listOfProducts(
   @RequestParam(name="pageSize",defaultValue = "5",required = false) int pageSize,
   @RequestParam(name="pageNo",defaultValue = "5",required = false) int pageNo,
   @RequestParam(name="sortBy",defaultValue = "id",required = false) String sortBy,
   @RequestParam(name="sortDir",defaultValue = "id",required = false) String sortDir){
        List<ProductDto> productDtos = productServiceIMPL.ListOfProducts(pageSize, pageNo, sortBy, sortDir);
        return new ResponseEntity<>(productDtos,HttpStatus.OK);
    }
    @GetMapping("/getById")
    public ResponseEntity<ProductDto>getById(@RequestParam long productId){
        ProductDto byId = productServiceIMPL.getById(productId);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
}
