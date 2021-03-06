package exam.service.impl;

import exam.model.entity.Product;
import exam.model.service.ProductServiceModel;
import exam.model.view.ProductViewModel;
import exam.repository.ProductRepository;
import exam.service.CategoryService;
import exam.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void addProduct(ProductServiceModel productServiceModel) {

        Product product = this.modelMapper
                .map(productServiceModel, Product.class);

        product.setCategory(this.categoryService
                .findCategory(productServiceModel.getCategory().getCategoryName()));

        this.productRepository.saveAndFlush(product);
    }

    @Override
    public List<ProductViewModel> findAllProducts() {

        return this.productRepository
                .findAll()
                .stream()
                .map(product -> {
                    ProductViewModel productViewModel = this.modelMapper
                            .map(product, ProductViewModel.class);

                    productViewModel.setCategory(product.getCategory().getCategoryName().name());

                    productViewModel.setImgUrl(String.format("/img/%s.png",
                            product.getCategory().getCategoryName().name()));

                    return productViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void buy(String id) {
        this.productRepository
                .deleteById(id);
    }

    @Override
    public void buyAll() {
        this.productRepository.deleteAll();
    }


}
