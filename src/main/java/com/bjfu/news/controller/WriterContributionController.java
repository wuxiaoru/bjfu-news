package com.bjfu.news.controller;


import com.bjfu.news.entity.NewsApproveContribution;
import com.bjfu.news.entity.NewsContribution;
import com.bjfu.news.model.ContributionDetail;
import com.bjfu.news.req.ContributionCreateParam;
import com.bjfu.news.req.ContributionReq;
import com.bjfu.news.req.IdsParam;
import com.bjfu.news.untils.FileUtils;
import com.bjfu.news.untils.MapMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    //新建稿件
    @ResponseBody
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public MapMessage create(@Validated @RequestBody ContributionCreateParam param) {

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
