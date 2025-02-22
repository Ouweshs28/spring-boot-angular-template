package com.project.template.rest;

import com.project.template.dto.CreateUpdateArticleRequest;
import com.project.template.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Long createArticle(@RequestBody CreateUpdateArticleRequest createArticleRequest, Locale locale) {
        return articleService.createArticle(createArticleRequest, locale);
    }

    @GetMapping("{id}")
    public CreateUpdateArticleRequest getArticle(@PathVariable Long id, Locale locale) {
        return articleService.getArticle(id, locale);
    }
}
