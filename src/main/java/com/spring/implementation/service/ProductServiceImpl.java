package com.spring.implementation.service;



import com.spring.implementation.domain.model.*;
import com.spring.implementation.domain.repository.ProductRepository;
import com.spring.implementation.domain.service.*;
import com.spring.implementation.dto.ProductWithQuantityDto;
import com.spring.implementation.dto.RequestRestarProductoInventarioDto;
import com.spring.implementation.dto.domain.CategoryDto;
import com.spring.implementation.dto.domain.ProductDto;
import com.spring.implementation.dto.save.SaveProductDto;
import com.spring.implementation.dto.update.UpdateInventoryDto;
import com.spring.implementation.dto.update.UpdateProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    static final String PRODUCT_NOT_FOUND = "No se encontró un producto con ID:  ";
    private ProductRepository productRepository;
    private CategoryService categoryService;
    private InventoryService inventoryService;
    private UnitService unitService;
    private ProductsPurchaseService productsPurchaseService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService,
                              InventoryService inventoryService,
                              UnitService unitService,
                              ProductsPurchaseService productsPurchaseService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
        this.unitService = unitService;
        this.productsPurchaseService = productsPurchaseService;
    }

    public ProductServiceImpl() {
    }

    @Override
    public ProductDto createProduct(SaveProductDto product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());

        Category category = categoryService.getCategoryById(product.getCategoryId());

        newProduct.setCategory(category);

        Inventory inventory = new Inventory();
        inventory.setCreationDate(new Date());
        inventory.setModificationDate(new Date());
        inventory.setTotalInventory(0);
        inventory.setQuantity(0);
        inventory.setProduct(newProduct);

        Unit unit = unitService.getUnitById(product.getUnitId());
        inventory.setUnit(unit);

        Product saveProduct = productRepository.save(newProduct);
        inventoryService.createInventory(inventory);

        return ProductDto.builder()
                .name(saveProduct.getName())
                .category(CategoryDto.builder()
                        .name(saveProduct.getCategory().getName())
                        .build())
                .build();
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Integer productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND + productId));
        Inventory inventory = inventoryService.findInventoryByProductId(productId);
        inventoryService.deleteInventory(inventory.getId());
        productRepository.delete(product);
        return ResponseEntity.ok().build();
    }

    @Override
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException(PRODUCT_NOT_FOUND + productId));
    }

    @Override
    public Product updateProduct(Integer productId, UpdateProductDto productRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_NOT_FOUND+ productId));

        product.setName(productRequest.getName());

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Void> substractProduct(RequestRestarProductoInventarioDto request) {

        int cantidadARestar = request.getCantidad();

        Inventory inventory = inventoryService.findInventoryByProductId(request.getIdProducto());

        List<ProductsPurchase> productsPurchaseList = productsPurchaseService.findAllByProductId(request.getIdProducto());

        //Order by purchase date
        productsPurchaseList.sort(Comparator.comparing(ProductsPurchase::getPurchaseDate));

        float valorTotalARestar = 0;

        for (ProductsPurchase productsPurchase : productsPurchaseList) {
            if (productsPurchase.getPurchaseStock() != 0) {
                if (productsPurchase.getAmount() >= request.getCantidad()) {
                    valorTotalARestar += productsPurchase.getUnitCost() * request.getCantidad();
                    productsPurchase.setPurchaseStock(productsPurchase.getPurchaseStock() - request.getCantidad());
                    productsPurchaseService.updateProductsPurchase(productsPurchase.getId(), productsPurchase);
                    break;
                } else{
                    valorTotalARestar += productsPurchase.getUnitCost() * productsPurchase.getPurchaseStock();
                    request.setCantidad(request.getCantidad() - productsPurchase.getPurchaseStock());
                    productsPurchase.setPurchaseStock(0);
                    productsPurchaseService.updateProductsPurchase(productsPurchase.getId(), productsPurchase);
                }
            }
        }
        UpdateInventoryDto inventoryUpdate = new UpdateInventoryDto();
        inventoryUpdate.setQuantity(inventory.getQuantity() - cantidadARestar);
        inventoryUpdate.setTotalInventory(inventory.getTotalInventory() - valorTotalARestar);
        inventoryService.updateInventory(inventory.getId(), inventoryUpdate);

        return ResponseEntity.ok().build();
    }

    @Override
    public List<ProductWithQuantityDto> getProductsWithQuantity() {
        List<ProductWithQuantityDto> listProductWithQuantityDto = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        for (Product product : products) {
            ProductWithQuantityDto productWithQuantityDto = new ProductWithQuantityDto();
            productWithQuantityDto.setProductName(product.getName());
            productWithQuantityDto.setProductId(product.getId());
            Inventory inventory = inventoryService.findInventoryByProductId(product.getId());
            productWithQuantityDto.setQuantity(inventory.getQuantity());
            productWithQuantityDto.setTotalInventory(inventory.getTotalInventory());
            listProductWithQuantityDto.add(productWithQuantityDto);
        }
        return listProductWithQuantityDto;
    }
}
