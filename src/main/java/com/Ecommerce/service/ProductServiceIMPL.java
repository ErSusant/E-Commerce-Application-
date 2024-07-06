package com.Ecommerce.service;

import com.Ecommerce.dto.ProductDto;
import com.Ecommerce.entity.Product;
import com.Ecommerce.exception.ResourceNotFound;
import com.Ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProductServiceIMPL implements ProductService{
    private ProductRepository productRepository;

    public ProductServiceIMPL(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product dtoToEntity(ProductDto dto){
        Product product=new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        product.setPrice(dto.getPrice());
        product.setCreatedAt(dto.getCreatedAt());
        product.setUpdatedAt(dto.getUpdatedAt());
        return product;
    }
    public ProductDto entityToDto(Product product){
        ProductDto dto1=new ProductDto();
        dto1.setId(product.getId());
        dto1.setName(product.getName());
        dto1.setDescription(product.getDescription());
        dto1.setPrice(product.getPrice());
        dto1.setStock(product.getStock());
        dto1.setCreatedAt(product.getCreatedAt());
        dto1.setUpdatedAt(product.getUpdatedAt());
        return dto1;
    }

    @Override
    public ProductDto addProduct(ProductDto dto) {
        Product product = dtoToEntity(dto);
        Product save = productRepository.save(product);
        ProductDto productDto = entityToDto(save);
        return productDto;
    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);

    }

    @Override
    public ProductDto updateProduct(long productId, ProductDto productDto) {
        Optional<Product> byId = productRepository.findById(productId);
        if(byId.isPresent()){
            Product product = byId.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setStock(productDto.getStock());
            product.setPrice(productDto.getPrice());
            product.setCreatedAt(productDto.getCreatedAt());
            product.setUpdatedAt(productDto.getUpdatedAt());
            Product save = productRepository.save(product);
            ProductDto productDto1 = entityToDto(save);
            return productDto1;
        }else{
            throw new ResourceNotFound("ProductId Not Found With: "+productId);
        }
    }

    @Override
    public List<ProductDto> ListOfProducts(int pageSize,int pageNo,String sortBy,String sortDir) {
        Pageable pageable=null;
        if(sortDir.equalsIgnoreCase("asc")){
              pageable = PageRequest.of(pageSize, pageNo, Sort.by(sortBy).ascending());
        }
        if(sortDir.equalsIgnoreCase("desc")){
            pageable = PageRequest.of(pageSize, pageNo, Sort.by(sortBy).descending());
        }
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> content = page.getContent();
        List<ProductDto> collect = content.stream().map(c -> entityToDto(c)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public ProductDto getById(long productId) {
        Optional<Product> byId = productRepository.findById(productId);
        if(byId.isPresent()){
            Product product = byId.get();
            ProductDto productDto = entityToDto(product);
            return productDto;
        }
        else{
            throw new ResourceNotFound("ProductId Not Found: "+productId);
        }
    }
}
