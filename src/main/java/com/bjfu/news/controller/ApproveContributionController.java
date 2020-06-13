package com.bjfu.news.controller;

import com.bjfu.news.constant.ApproveStatus;
import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.untils.MapMessage;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/approve")
public class ApproveContributionController extends AbstractNewsController {

    @RequestMapping(value = "list.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage list(@Validated @RequestBody ContributionReq req) {
        Map<String, Object> map = getInitialMap();
        if (req.getUserId() == null || req.getUserId() <= 0L) {
            return MapMessage.successMessage().add("data", map);
        }
        List<NewsApproveContribution> contributions = approveContributionLoader.selectByApproveId(req.getUserId());
        if (CollectionUtils.isEmpty(contributions)) {
            return MapMessage.successMessage().add("data", map);
        }
        List<Long> contributionIds = contributions.stream().map(NewsApproveContribution::getContributionId).collect(Collectors.toList());
        req.setContributionIds(contributionIds);
        return list(req, map);
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
