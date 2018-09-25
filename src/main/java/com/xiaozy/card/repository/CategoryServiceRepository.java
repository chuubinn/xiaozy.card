package com.xiaozy.card.repository;

import com.xiaozy.card.dataobject.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryServiceRepository extends JpaRepository<Category,String> {

}
