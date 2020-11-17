package exam.service;

import exam.model.entity.Category;
import exam.model.entity.CategoryName;

import java.util.List;

public interface CategoryService {

    void initCategories();

    Category findCategory(CategoryName categoryName);

    List<Category> findAllCategories();
}
