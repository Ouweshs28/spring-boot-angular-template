package com.project.template.persistence.repository;

import com.project.template.persistence.entity.Article;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends AbstractRepository<Article> {
}
