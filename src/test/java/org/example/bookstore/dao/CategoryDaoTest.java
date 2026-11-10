package org.example.bookstore.dao;

import org.example.bookstore.dao.impl.CategoryDaoImpl;
import org.example.bookstore.entity.Category;

import java.util.Optional;

public class CategoryDaoTest {

    public static void main(String[] args) {

        CategoryDao dao = new CategoryDaoImpl();
        Optional<Category> category = dao.findById(1L);
        System.out.println(category);

        if (category.isPresent()) {
            System.out.println("Found category with id 1");
        } else {
            System.out.println("Not Found");
        }
    }
}
