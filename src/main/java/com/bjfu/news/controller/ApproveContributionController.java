package com.bjfu.news.controller;

import com.bjfu.news.constant.ApproveStatus;
import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.model.ContributionList;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.untils.DateUtils;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/approve")
public class ApproveContributionController extends AbstractNewsController {

    @RequestMapping(value = "list.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage list(@Validated @RequestBody ContributionReq req) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", Collections.emptyList());
        map.put("pageSize", 0);
        map.put("totalCount", 0);
        map.put("maxPage", 0);
        if (req.getUserId() == null || req.getUserId() <= 0L) {
            return MapMessage.successMessage().add("data", map);
        }
        List<NewsApproveContribution> contributions = approveContributionLoader.selectByApproveId(req.getUserId());
        if (CollectionUtils.isEmpty(contributions)) {
            return MapMessage.successMessage().add("data", map);
        }
        List<Long> contributionIds = contributions.stream().map(NewsApproveContribution::getContributionId).collect(Collectors.toList());
        req.setContributionIds(contributionIds);
        int size = req.getSize() != null ? req.getSize() : 10;
        int page = req.getPage() != null ? req.getPage() : 0;
        req.setStart((page - 1) * size > 0 ? (page - 1) * size : 0);
        req.setSize(size);
        if (req.getStatus() == null || StringUtils.isEmpty(req.getStatus())) {
            req.setStatusList(ContributionStatus.APPROVE_MAPPING.keySet());
        }
        int count = newsWriterContributionLoader.getCount(req);
        List<NewsContribution> writerContributions = newsWriterContributionLoader.page(req);
        List<ContributionList> list = new ArrayList<>();
        for (NewsContribution contribution : writerContributions) {
            ContributionList contributionList = new ContributionList();
            BeanUtils.copyProperties(contribution, contributionList);
            contributionList.setSubmitTime(DateUtils.DateToString(contribution.getSubmitTime()));
            list.add(contributionList);
        }
        int maxPage = count % size == 0 ? count / size : count / size + 1;
        map.put("list", list);
        map.put("pageSize", page);
        map.put("totalCount", count);
        map.put("maxPage", maxPage);
        return MapMessage.successMessage().add("data", map);
    }

    //预览

    //下载

    //审批
    @RequestMapping(value = "approve.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage approve(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id,
                              @Validated @NotBlank(message = "状态不能为空") String status,
                              @RequestParam(required = false) String suggestion) {
        NewsContribution contribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(contribution)) {
            return MapMessage.errorMessage().add("info", "稿件id有误");
        }
        NewsApproveContribution approveContribution = approveContributionLoader.selectByCId(id);
        if (Objects.isNull(approveContribution)) {
            return MapMessage.errorMessage().add("info", "稿件id有误");
        }
        approveContribution.setSuggestion(suggestion);
        approveContribution.setApproveTime(new Date());
        approveContributionService.update(approveContribution);
        if (status.equals(ApproveStatus.AGREE.name())) {
            contribution.setStatus(ContributionStatus.APPROVE.name());
        }
        if (status.equals(ApproveStatus.REJECTION.name())) {
            contribution.setStatus(ContributionStatus.APPROVAL_REJECTION.name());
        }
        newsWriterContributionService.update(contribution);
        return MapMessage.successMessage();
    }
}
