package org.example.bookstore.dao;

import org.example.bookstore.entity.Category;

import java.util.List;
import java.util.Optional;

public class CategoryDaoTest {

    public static void main(String[] args) {
        CategoryDao dao = new CategoryDao();

        List<Category> all = dao.findAll();
        System.out.println(all);

        Optional<Category> category = dao.findById(1L);
        System.out.println(category);

        if (category.isPresent()) {
            System.out.println("Found category with id 1");
        }else{
            System.out.println("Not Found");
        }
    }
}
