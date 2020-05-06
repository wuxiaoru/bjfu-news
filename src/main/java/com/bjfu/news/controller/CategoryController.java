package com.bjfu.news.controller;

import com.bjfu.news.entity.NewsCategory;
import com.bjfu.news.untils.MapMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/v1/category")
public class CategoryController extends AbstractNewsController{

    //新建
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage insert(@Validated @NotBlank(message = "类别名称不能为空") String name) {
        int result = newsCategoryService.insert(name);
        if (result == 0) {
            return MapMessage.errorMessage().add("info", "添加失败");
        }
        return MapMessage.successMessage();
    }

    //编辑
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage edit(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id, @Validated @NotBlank(message = "类别名称不能为空") String name) {
        NewsCategory newsCategory = newsCategoryLoader.selectById(id);
        if (Objects.isNull(newsCategory)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        if (newsCategory.getCategoryName().equals(name)) {
            return MapMessage.successMessage();
        }
        newsCategory.setCategoryName(name);
        int result = newsCategoryService.update(newsCategory);
        if (result == 0) {
            return MapMessage.errorMessage().add("info", "编辑失败");
        }
        return MapMessage.successMessage();
    }

    //删除
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage delete(@Validated @NotBlank @Min(value = 1, message = "id必须大于0") Long id) {
        NewsCategory newsCategory = newsCategoryLoader.selectById(id);
        if (Objects.isNull(newsCategory)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        newsCategory.setDisabled(true);
        int result = newsCategoryService.update(newsCategory);
        if (result == 0) {
            return MapMessage.errorMessage().add("info", "删除失败");
        }
        return MapMessage.successMessage();
    }

    //所有
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage list() {
        List<NewsCategory> newsCategories = newsCategoryLoader.selectAll();
        if (CollectionUtils.isEmpty(newsCategories)) {
            return MapMessage.errorMessage().add("info", "查询失败");
        }
        return MapMessage.successMessage().add("data", newsCategories);
    }
}
