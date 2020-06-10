package com.bjfu.news.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.constant.UserRoleType;
import com.bjfu.news.entity.*;
import com.bjfu.news.model.*;
import com.bjfu.news.req.ContributionCreateParam;
import com.bjfu.news.req.ContributionEditParam;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.UserReq;
import com.bjfu.news.untils.DateUtils;
import com.bjfu.news.untils.FileUtils;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping("/v1/contribution")
public class WriterContributionController extends AbstractNewsController {

//    String FILE_PATH = "D:\\file";

    String FILE_PATH = "/usr/local/git/news/doc";

    //根据名字分页按时间倒叙 1页10条
    @RequestMapping(value = "list.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage list(@Validated @RequestBody ContributionReq req) {
        int size = req.getSize() != null ? req.getSize() : 10;
        int page = req.getPage() != null ? req.getPage() : 0;
        req.setStart((page - 1) * size > 0 ? (page - 1) * size : 0);
        req.setSize(size);
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
        Map<String, Object> map = new HashMap<>();
        map.put("list", list);
        map.put("pageSize", page);
        map.put("totalCount", count);
        map.put("maxPage", maxPage);
        return MapMessage.successMessage().add("data", map);
    }

    //上传
    @RequestMapping(value = "/upload.vpage",
            method = RequestMethod.POST)
    @ResponseBody
    public MapMessage upload(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest == null) {
            MapMessage.errorMessage().add("info", "请上传文件");
        }
        // 为了获取文件，这个类是必须的
        MultiValueMap<String, MultipartFile> map = ((MultipartHttpServletRequest) request).getMultiFileMap();
        // 获取到文件的列表
        List<MultipartFile> listFile = map.get("file");
        MultipartFile multipartFile = listFile.get(0);
        InputStream in = null;
        String filePath = "";
        try {
            in = multipartFile.getInputStream();
            String originalFilename = multipartFile.getOriginalFilename();
            //保存到本地服务器
            filePath = FileUtils.saveFile(in, originalFilename, FILE_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //一定要关闭资源
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return MapMessage.successMessage().add("data", filePath);
    }

    @ResponseBody
    @RequestMapping(value = "submit.vpage", method = RequestMethod.POST)
    public MapMessage submit(@Validated @RequestBody ContributionCreateParam param) {
        MapMessage check = check(param);
        if (!check.isSuccess()) {
            return check;
        }
        if (param.getApproveId() == null || param.getApproveId() <= 0L) {
            return MapMessage.errorMessage().add("info", "审批人不能为空");
        }
        return newsWriterContributionService.submitContribution(param);
    }

    @ResponseBody
    @RequestMapping(value = "fast-submit.vpage", method = RequestMethod.POST)
    public MapMessage fastSubmit(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id,
                                 @Validated @NotNull @Min(value = 1, message = "id必须大于0") Long approveId) {
        NewsContribution newsContribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(newsContribution)) {
            return MapMessage.errorMessage().add("info", "稿件id有误");
        }
        if (!newsContribution.getStatus().equals(ContributionStatus.DRAFT.name())) {
            return MapMessage.errorMessage().add("info", "当前稿件不是草稿状态，不能提交");
        }
        NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(approveId);
        if (Objects.isNull(newsUserInfo)) {
            return MapMessage.errorMessage().add("info", "审稿人id有误");
        }
        return newsWriterContributionService.fastSubmit(id, approveId);
    }

    @ResponseBody
    @RequestMapping(value = "draft.vpage", method = RequestMethod.POST)
    public MapMessage draft(@Validated @RequestBody ContributionCreateParam param) {
        MapMessage check = check(param);
        if (!check.isSuccess()) {
            return check;
        }
        return newsWriterContributionService.saveDraft(param);
    }

    private MapMessage check(ContributionCreateParam param) {
        if (param.getTitle() == null || param.getTitle().isEmpty()) {
            return MapMessage.errorMessage().add("info", "题目不能为空");
        }
        if (param.getDocUrl() == null || param.getDocUrl().isEmpty()) {
            return MapMessage.errorMessage().add("info", "稿件地址不能为空");
        }
        if (param.getDocAuthor() == null || param.getDocAuthor().isEmpty()) {
            return MapMessage.errorMessage().add("info", "稿件作者不能为空");
        }
        return MapMessage.successMessage();
    }


    //查看详情
    @RequestMapping(value = "detail.vpage", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage detail(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsContribution newsContribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(newsContribution)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        ContributionDetail detail = new ContributionDetail();
        BeanUtils.copyProperties(newsContribution, detail);
        detail.setSubmitTime(DateUtils.DateToString(newsContribution.getSubmitTime()));

        NewsApproveContribution newsApproveContribution = approveContributionLoader.selectByCId(newsContribution.getId());
        if (Objects.nonNull(newsApproveContribution)) {
            if (newsApproveContribution.getUserId() != null) {
                detail.setApproveId(newsApproveContribution.getUserId());
                NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(newsApproveContribution.getUserId());
                if (Objects.nonNull(newsUserInfo)) {
                    detail.setApproveName(newsUserInfo.getUserName());
                }
            }
            if (newsApproveContribution.getApproveTime() != null) {
                detail.setApproveTime(DateUtils.DateToString(newsApproveContribution.getApproveTime()));
            }
            if (newsApproveContribution.getSuggestion() != null && !newsApproveContribution.getSuggestion().isEmpty()) {
                detail.setApproveSuggestion(newsApproveContribution.getSuggestion());
            }
        }
        NewsEditContribution newsEditContribution = newsEditContributionLoader.selectByCId(newsContribution.getId());
        if (Objects.nonNull(newsEditContribution)) {
            if (newsEditContribution.getUserId() != null) {
                detail.setEditorId(newsEditContribution.getUserId());
                NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(newsEditContribution.getUserId());
                if (Objects.nonNull(newsUserInfo)) {
                    detail.setEditorName(newsUserInfo.getUserName());
                }
            }
            if (newsEditContribution.getSuggestion() != null && !newsEditContribution.getSuggestion().isEmpty()) {
                detail.setEditSuggestion(newsEditContribution.getSuggestion());
                if (newsEditContribution.getUpdateTime() != null) {
                    detail.setApproveTime(DateUtils.DateToString(newsEditContribution.getUpdateTime()));
                }
            }
        }
        List<NewsOperateLog> newsOperateLogs = newsLogLoader.loadByCId(newsContribution.getId());
        if (!CollectionUtils.isEmpty(newsOperateLogs)) {
            List<OperateLogDetail> list = new ArrayList<>();
            for (NewsOperateLog log : newsOperateLogs) {
                OperateLogDetail logDetail = new OperateLogDetail();
                logDetail.setOperateId(log.getOperateId());
                logDetail.setOperateTime(DateUtils.DateToString(log.getOperateTime()));
                logDetail.setStatus(log.getStatus());
                OperateLogBean jsonObject = JSONObject.toJavaObject(JSON.parseObject(log.getOperateDetail()), OperateLogBean.class);
                if (Objects.nonNull(jsonObject)) {
                    logDetail.setDocAuthor(jsonObject.getDocAuthor());
                    logDetail.setDocUrl(jsonObject.getDocUrl());
                    logDetail.setPicUrl(jsonObject.getPicUrl());
                    logDetail.setPicAuthor(jsonObject.getPicAuthor());
                    logDetail.setSuggestion(jsonObject.getSuggestion());
                }
                list.add(logDetail);
            }
            detail.setLogList(list);
        }
        return MapMessage.successMessage().add("data", detail);
    }

    //编辑
    @RequestMapping(value = "edit.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage edit(@Validated @RequestBody ContributionEditParam param) {
        if (param.getId() == null || param.getId() <= 0L) {
            return MapMessage.errorMessage().add("info", "稿件id不能为空");
        }
        NewsContribution newsContribution = newsWriterContributionLoader.selectById(param.getId());
        if (Objects.isNull(newsContribution)) {
            return MapMessage.errorMessage().add("info", "稿件id有误");
        }
        if (!(newsContribution.getStatus().equals(ContributionStatus.DRAFT.name()) || newsContribution.getStatus().equals(ContributionStatus.APPROVAL_REJECTION.name()))) {
            return MapMessage.errorMessage().add("info", "当前稿件状态不是草稿或者审批不过，不能编辑");
        }
        BeanUtils.copyProperties(param, newsContribution);
        newsContribution.setSubmitTime(new Date());
        int result = newsWriterContributionService.update(newsContribution);
        if (result == 0) {
            return MapMessage.errorMessage().add("info", "编辑失败");
        }
        return MapMessage.successMessage();
    }

    @RequestMapping(value = "delete.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage delete(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsContribution newsContribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(newsContribution)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        if (!newsContribution.getStatus().equals(ContributionStatus.DRAFT.name())) {
            return MapMessage.errorMessage().add("info", "当前稿件不是草稿状态，不能删除");
        }
        int delete = newsWriterContributionService.delete(id);
        if (delete == 0) {
            return MapMessage.errorMessage().add("info", "删除失败");
        }
        return MapMessage.successMessage();
    }

    @RequestMapping(value = "withDraw.vpage", method = RequestMethod.POST)
    @ResponseBody
    public MapMessage withDraw(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsContribution newsContribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(newsContribution)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        if (!newsContribution.getStatus().equals(ContributionStatus.APPROVAL_PENDING.name())) {
            return MapMessage.errorMessage().add("info", "当前稿件不是待审批状态，不能撤回");
        }
        newsContribution.setStatus(ContributionStatus.DRAFT.name());
        int delete = newsWriterContributionService.update(newsContribution);
        if (delete == 0) {
            return MapMessage.errorMessage().add("info", "撤回失败");
        }
        return MapMessage.successMessage();
    }

    @RequestMapping(value = "approve/list.vpage", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage approveList(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long userId) {
        NewsUserInfo newsUserInfo = newsUserInfoLoader.loadById(userId);
        if (Objects.isNull(newsUserInfo)) {
            return MapMessage.errorMessage().add("info", "用户id有误");
        }
        UserReq req = new UserReq();
        req.setUnit(newsUserInfo.getUnit());
        req.setRoleType(UserRoleType.APPROVER.name());
        List<NewsUserInfo> list = newsUserInfoLoader.list(req);
        if (CollectionUtils.isEmpty(list)) {
            return MapMessage.successMessage().add("data", Collections.emptyList());
        }
        List<UserInfo> userInfoList = new ArrayList<>();
        for (NewsUserInfo user : list) {
            UserInfo info = new UserInfo();
            info.setId(user.getId());
            info.setName(user.getUserName());
            userInfoList.add(info);
        }
        return MapMessage.successMessage().add("data", userInfoList);
    }
}
