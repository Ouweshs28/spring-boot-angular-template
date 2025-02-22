package com.project.template.service;

import com.project.template.dto.CreateUpdateArticleRequest;

import java.util.Locale;

public interface ArticleService {
    Long createArticle(CreateUpdateArticleRequest createArticleRequest, Locale locale);

    CreateUpdateArticleRequest getArticle(Long id, Locale locale);
}
