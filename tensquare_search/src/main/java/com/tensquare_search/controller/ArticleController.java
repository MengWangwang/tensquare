package com.tensquare_search.controller;

import com.common.entity.PageResult;
import com.common.entity.Result;
import com.common.entity.StatusCode;
import com.tensquare_search.pojo.Article;
import com.tensquare_search.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result save(@RequestBody Article article){
        articleService.save(article);

        return new Result(true, StatusCode.OK,"添加成功");
    }

    @GetMapping(value = "/{key}/{page}/{size}")
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData=articleService.findByKey(key,page,size);

        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }
}
