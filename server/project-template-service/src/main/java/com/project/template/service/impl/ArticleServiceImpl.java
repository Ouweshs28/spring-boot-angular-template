package com.project.template.service.impl;

import com.project.template.dto.CreateUpdateArticleRequest;
import com.project.template.persistence.entity.Article;
import com.project.template.persistence.repository.ArticleRepository;
import com.project.template.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Long createArticle(CreateUpdateArticleRequest createArticleRequest, Locale locale) {
        Article article = new Article();
        article.setPrice(createArticleRequest.getPrice());
        return articleRepository.save(article).getId();
    }

    @Override
    public CreateUpdateArticleRequest getArticle(Long id, Locale locale) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        CreateUpdateArticleRequest request = new CreateUpdateArticleRequest();
        request.setId(article.getId());
        request.setPrice(article.getPrice());
        return request;
    }
}
