package com.bjfu.news.controller;

import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.constant.EditStatus;
import com.bjfu.news.entity.NewsEditContribution;
import com.bjfu.news.entity.NewsWriterContribution;
import com.bjfu.news.model.ApproveList;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.untils.MapMessage;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/v1/edit")
public class EditContributionController extends AbstractNewsController {

    //根据名字分页按时间倒叙 1页10条 搜索列表
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage list(@Validated @RequestBody ContributionReq req) {
        int size = req.getSize() == null ? 10 : req.getSize();
        int page = req.getStart() == null ? 1 : req.getStart();
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", page);
        if (StringUtils.isEmpty(req.getName())) {
            List<NewsWriterContribution> newsWriterContributions = newsWriterContributionLoader.listByName(req.getName());
            if (CollectionUtils.isEmpty(newsWriterContributions)) {
                map.put("list", Collections.emptyList());
                map.put("totalCount", 0);
                map.put("maxPage", 0);
                return MapMessage.successMessage().add("data", map);
            }
            List<Long> contributionIds = newsWriterContributions.stream().map(NewsWriterContribution::getId).collect(Collectors.toList());
            req.setContributionIds(contributionIds);
        }
        req.setStart((page - 1) * size);
        req.setSize(size);
        int count = newsEditContributionLoader.getCount(req);
        List<NewsEditContribution> newsEditContributions = newsEditContributionLoader.list(req);
        List<Long> contributionIds = newsEditContributions.stream().map(NewsEditContribution::getContributionId).collect(Collectors.toList());
        Map<Long, NewsWriterContribution> contributionMap = newsWriterContributionLoader.selectByIds(contributionIds).stream().collect(Collectors.toMap(NewsWriterContribution::getId, Function.identity()));
        List<ApproveList> approveList = new ArrayList<>();
        for (NewsEditContribution newsEditContribution : newsEditContributions) {
            ApproveList approve = new ApproveList();
            approve.setId(newsEditContribution.getId());
            approve.setContributionId(newsEditContribution.getContributionId());
            NewsWriterContribution newsWriterContribution = contributionMap.get(newsEditContribution.getContributionId());
            if (Objects.isNull(newsWriterContribution)) {
                continue;
            }
            approve.setTitle(newsWriterContribution.getTitle());
            approve.setStatus(newsWriterContribution.getStatus());
            approveList.add(approve);
        }
        int maxPage = count % size == 0 ? count / size : count / size + 1;
        map.put("list", approveList);
        map.put("totalCount", count);
        map.put("maxPage", maxPage);
        return MapMessage.successMessage().add("data", map);
    }

    //预览

    //下载

    //审批
    @RequestMapping(value = "deal", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage deal(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id,
                           @Validated @NotBlank(message = "状态不能为空") String status,
                           @RequestParam(required = false) String editorSuggestion) {
        NewsEditContribution newsEditContribution = newsEditContributionLoader.selectById(id);
        if (Objects.isNull(newsEditContribution)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        NewsWriterContribution newsWriterContribution = newsWriterContributionLoader.selectById(newsEditContribution.getContributionId());
        if (Objects.isNull(newsWriterContribution)) {
            return MapMessage.errorMessage().add("info", "稿件信息有误");
        }
        newsEditContribution.setOperation(status);
        newsEditContribution.setSuggestion(editorSuggestion);
        editContributionService.updateOperation(newsEditContribution);
        if (status.equals(EditStatus.AGREE.name())) {
            newsWriterContribution.setStatus(ContributionStatus.APPROVE.getCode());
        }
        if (status.equals(EditStatus.REJECTION.name())) {
            newsWriterContribution.setStatus(ContributionStatus.APPROVAL_REJECTION.getCode());
        }
        newsWriterContributionService.updateStatus(newsWriterContribution);
        return MapMessage.successMessage();
    }

    //批量删除
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage delete(@RequestBody @Validated IdsParam idsParam) {
        List<Long> ids = idsParam.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            return MapMessage.successMessage();
        }
        int delete = editContributionService.delete(idsParam);
        if (delete == 0) {
            return MapMessage.errorMessage().add("info", "删除失败");
        }
        return MapMessage.successMessage();
    }
}
