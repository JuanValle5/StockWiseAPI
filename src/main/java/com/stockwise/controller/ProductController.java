package com.stockwise.controller;

import com.stockwise.dto.ProductDTO;
import com.stockwise.service.IProductService;
import jakarta.servlet.ServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<ProductDTO> optionalProductDTO = productService.findById(id);
        if (optionalProductDTO.isPresent()) {
            return ResponseEntity.ok(optionalProductDTO);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/findByLowStock")
    public ResponseEntity<?> finByLowStock(){
        return ResponseEntity.ok(productService.findProductsByLowStock());
    }

    @GetMapping("/findByCategory/{id}")
    public ResponseEntity<?> findByCategory(@PathVariable Long id){
        return ResponseEntity.ok(productService.findProductsByCategory(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createdProduct(@RequestBody ProductDTO productDTO){

        ProductDTO productDTO1 = productService.save(productDTO);

        return ResponseEntity
                .created(URI.create("/api/product/create" + productDTO1.getId()))
                .body(productDTO1);

    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(id,productDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
