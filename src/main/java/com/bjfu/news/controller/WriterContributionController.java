package com.bjfu.news.controller;


import com.bjfu.news.constant.ContributionStatus;
import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.model.ContributionDetail;
import com.bjfu.news.req.ContributionCreateParam;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.untils.FileUtils;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/v1/contribution")
public class WriterContributionController extends AbstractNewsController {

    String FILE_PATH = "D:\\file";

    //根据名字分页按时间倒叙 1页10条
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage list(@Validated @RequestBody ContributionReq req) {
        int size = req.getSize() != null ? req.getSize() : 10;
        int page = req.getPage() != null ? req.getPage() : 0;
        req.setStart((page - 1) * size);
        req.setSize(size);
        int count = newsWriterContributionLoader.getCount(req);
        List<NewsContribution> writerContributions = newsWriterContributionLoader.pageByName(req);
        int maxPage = count % size == 0 ? count / size : count / size + 1;
        Map<String, Object> map = new HashMap<>();
        map.put("list", writerContributions);
        map.put("pageSize", page);
        map.put("totalCount", count);
        map.put("maxPage", maxPage);
        return MapMessage.successMessage().add("data", map);
    }

    //上传
    @RequestMapping(value = "/upload",
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
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public MapMessage detail(@Validated @NotNull @Min(value = 1, message = "id必须大于0") Long id) {
        NewsContribution newsContribution = newsWriterContributionLoader.selectById(id);
        if (Objects.isNull(newsContribution)) {
            return MapMessage.errorMessage().add("info", "id有误");
        }
        ContributionDetail detail = new ContributionDetail();
        BeanUtils.copyProperties(newsContribution, detail);
        String categoryName = "";
        detail.setCategory(categoryName);
        List<NewsApproveContribution> newsApproveContributions = approveContributionLoader.selectByCId(newsContribution.getId());
//        if (!CollectionUtils.isEmpty(newsApproveContributions)) {
//            NewsApproveContribution newsApproveContribution = newsApproveContributions.stream().filter(e -> !e.getOperation().equals(ApproveStatus.NONE.name())).findFirst().orElse(null);
//            if (Objects.nonNull(newsApproveContribution)) {
//                detail.setApproveSuggestion(newsApproveContribution.getSuggestion());
//            }
//        }
//        List<NewsEditContribution> newsEditContributions = newsEditContributionLoader.selectByCId(newsContribution.getId());
//        if (!CollectionUtils.isEmpty(newsEditContributions)) {
//            NewsEditContribution newsEditContribution = newsEditContributions.stream().filter(e -> !e.getOperation().equals(EditStatus.NONE.name())).findFirst().orElse(null);
//            if (Objects.nonNull(newsEditContribution)) {
//                detail.setApproveSuggestion(newsEditContribution.getSuggestion());
//            }
//        }
        return MapMessage.successMessage().add("data", detail);
    }
    //编辑


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
        int delete = newsWriterContributionService.updateStatus(newsContribution);
        if (delete == 0) {
            return MapMessage.errorMessage().add("info", "撤回失败");
        }
        return MapMessage.successMessage();
    }
}
