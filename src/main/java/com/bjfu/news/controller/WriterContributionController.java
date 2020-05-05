package com.bjfu.news.controller;


import com.bjfu.news.entity.NewsCategory;
import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.model.ContributionDetail;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.service.NewsCategoryLoader;
import com.bjfu.news.service.NewsWriterContributionLoader;
import com.bjfu.news.service.NewsWriterContributionService;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/v1/contribution")
public class WriterContributionController {

    @Autowired
    private NewsWriterContributionService newsWriterContributionService;

    @Autowired
    private NewsWriterContributionLoader newsWriterContributionLoader;

    @Autowired
    private NewsCategoryLoader newsCategoryLoader;

    //根据名字分页按时间倒叙 1页10条
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage list(@RequestParam(required = false) String name,
                           @RequestParam(required = false, defaultValue = "1") int page,
                           @RequestParam(required = false, defaultValue = "10") int size) {
        ContributionReq req = new ContributionReq();
        if (!StringUtils.isEmpty(name)) {
            req.setName(name);
        }
        req.setStart((page - 1) * size);
        req.setSize(size);
        int count = newsWriterContributionLoader.getCount(req);
        List<NewsWriterContribution> writerContributions = newsWriterContributionLoader.listByName(req);
        int maxPage = count % size == 0 ? count / size : count / size + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("list", writerContributions);
        map.put("pageSize", page);
        map.put("totalCount", count);
        map.put("maxPage", maxPage);
        return MapMessage.successMessage().add("data", map);
    }

    //新建

    //查看详情
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage detail(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsWriterContribution newsWriterContribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(newsWriterContribution)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        ContributionDetail detail = new ContributionDetail();
        BeanUtils.copyProperties(newsWriterContribution, detail);
        Long categoryId = newsWriterContribution.getCategoryId();
        NewsCategory newsCategory = newsCategoryLoader.selectById(categoryId);
        String categoryName = "";
        if (Objects.nonNull(newsCategory)) {
            categoryName = newsCategory.getCategoryName();
        }
        detail.setCategory(categoryName);
        return MapMessage.successMessage().add("data", detail);
    }
    //编辑

    //批量删除
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage delete(@RequestBody @Validated IdsParam idsParam) {
        List<Long> ids = idsParam.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            return MapMessage.successMessage();
        }
        int delete = newsWriterContributionService.delete(idsParam);
        if (delete == 0) {
            return MapMessage.errorMessage().add("info", "删除失败");
        }
        return MapMessage.successMessage();
    }
}
