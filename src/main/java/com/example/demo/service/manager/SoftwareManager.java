package com.example.demo.service.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.*;
import com.example.demo.mapper.SoftwareMapper;
import com.example.demo.request.*;
import com.example.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SoftwareManager {

    @Resource
    private UserService userService;

    @Resource
    private SoftwareMapper softwareMapper;

    public LambdaQueryWrapper<SoftwareBonus> getBonusWrapper(GetSoftwareBonusRequest request) {
        LambdaQueryWrapper<SoftwareBonus> wrapper = new LambdaQueryWrapper<>();
        List<Criteria.KV> items = request.getCriteria().getItems();
        for (Criteria.KV kv : items) {
            switch (kv.getKey()) {
                case "bonusAmount" : {
                    wrapper.eq(SoftwareBonus::getBonusAmount, kv.getValue());
                    break;
                }
                case "bonusType" : {
                    if (Integer.valueOf(kv.getValue()).equals(0)) break;
                    wrapper.eq(SoftwareBonus::getBonusType, kv.getValue());
                    break;
                }
                case "releaseStatus" : {
                    if (Integer.valueOf(kv.getValue()).equals(0)) break;
                    wrapper.eq(SoftwareBonus::getReleaseStatus, kv.getValue());
                    break;
                }
                case "inventorName" : {
                    wrapper.eq(SoftwareBonus::getInventorName, kv.getValue());
                    break;
                }
            }
        }
        return wrapper;
    }

    public LambdaQueryWrapper<Software> getSoftwareWrapper(GetSoftwareBonusRequest request) {
        LambdaQueryWrapper<Software> wrapper = new LambdaQueryWrapper<>();
        List<Criteria.KV> items = request.getCriteria().getItems();
        for (Criteria.KV kv : items) {
            switch (kv.getKey()) {
                case "softwareName" : {
                    wrapper.eq(Software::getSoftwareName, kv.getValue());
                    break;
                }
                case "softwareCode" : {
                    wrapper.eq(Software::getSoftwareCode, kv.getValue());
                    break;
                }
                case "version" : {
                    wrapper.eq(Software::getVersion, kv.getValue());
                    break;
                }
            }
        }
        return wrapper;
    }

    public LambdaQueryWrapper<Software> getWrapper(GetSoftwareFileInfoRequest request) {
        LambdaQueryWrapper<Software> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getSoftwareCode())) {
            wrapper.eq(Software::getSoftwareCode, request.getSoftwareCode());
        }
        return wrapper;
    }

    public LambdaQueryWrapper<SoftwareFile> getFileWrapper(GetSoftwareFileInfoRequest request) {
        LambdaQueryWrapper<SoftwareFile> wrapper = new LambdaQueryWrapper<>();
        List<Criteria.KV> items = request.getCriteria().getItems();
        for (Criteria.KV kv : items) {
            switch (kv.getKey()) {
                case "softwareCode" : {
                    Software software = softwareMapper.selectOne(new LambdaQueryWrapper<Software>().eq(Software::getSoftwareCode, kv.getValue()));
                    wrapper.eq(SoftwareFile::getSoftwareId, software.getId());
                    break;
                }
                case "fileName" : {
                    wrapper.eq(SoftwareFile::getFileName, kv.getValue());
                    break;
                }
                case "fileType" : {
                    if (Integer.parseInt(kv.getValue()) == 0) break;
                    wrapper.eq(SoftwareFile::getFileType, kv.getValue());
                    break;
                }
                case "uploadDateBegin" : {
                    String endDate = null;
                    for (Criteria.KV kV : items) {
                        if (kV.getKey().equals("uploadDateEnd")) {
                            endDate = kV.getValue();
                            break;
                        }
                    }
                    wrapper.between(SoftwareFile::getUploadDate, kv.getValue(), endDate);
                    break;
                }
            }
        }
//        wrapper.eq(SoftwareFile::getFileType, request.getFileType());
//        if (StringUtils.isNotBlank(request.getFileName())) {
//            wrapper.eq(SoftwareFile::getFileName, request.getFileName());
//        }
//        if (StringUtils.isNotBlank(request.getUploadDateBegin()) && StringUtils.isNotBlank(request.getUploadDateEnd())) {
//            wrapper.between(SoftwareFile::getUploadDate, request.getUploadDateBegin(), request.getUploadDateEnd());
//        }
        return wrapper;
    }
    public LambdaQueryWrapper<SoftwareOfficialFee> getCriteriaWrapper(GetSoftwareOfficialFeeRequest request) {
        LambdaQueryWrapper<SoftwareOfficialFee> wrapper = new LambdaQueryWrapper<>();
        List<Criteria.KV> items = request.getCriteria().getItems();
        for (Criteria.KV kv : items) {
            switch (kv.getKey()) {
                case "officialFeeName" : {
                    wrapper.eq(SoftwareOfficialFee::getOfficialFeeName, kv.getValue());
                    break;
                }
                case "dueDateBegin" : {
                    String endDate = null;
                    for (Criteria.KV kV : items) {
                        if (kV.getKey().equals("dueDateEnd")) {
                            endDate = kV.getValue();
                        }
                        break;
                    }
                    wrapper.between(SoftwareOfficialFee::getDueDate, kv.getValue(), endDate);
                    break;
                }
                case "actualPayDateBegin" : {
                    String endDate = null;
                    for (Criteria.KV kV : items) {
                        if (kV.getKey().equals("actualPayDateEnd")) {
                            endDate = kV.getValue();
                        }
                        break;
                    }
                    wrapper.between(SoftwareOfficialFee::getActualPayDate, kv.getValue(), endDate);
                    break;
                }
                case "dueAmount" : {
                    wrapper.eq(SoftwareOfficialFee::getDueAmount, kv.getValue());
                    break;
                }
                case "actualAmount" : {
                    wrapper.eq(SoftwareOfficialFee::getActualAmount, kv.getValue());
                    break;
                }
            }
        }
        return wrapper;
    }


    public LambdaQueryWrapper<SoftwareOfficialFee> getWrapper(GetSoftwareOfficialFeeRequest request) {
        LambdaQueryWrapper<SoftwareOfficialFee> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getOfficialFeeName())) {
            wrapper.eq(SoftwareOfficialFee::getOfficialFeeName, request.getOfficialFeeName());
        }
        if (StringUtils.isNotBlank(request.getDueDateBegin()) && StringUtils.isNotBlank(request.getDueDateEnd())) {
            wrapper.between(SoftwareOfficialFee::getDueDate, request.getDueDateBegin(), request.getDueDateEnd());
        }
        if (StringUtils.isNotBlank(request.getActualPayDateBegin()) && StringUtils.isNotBlank(request.getActualPayDateEnd())) {
            wrapper.between(SoftwareOfficialFee::getActualPayDate, request.getActualPayDateBegin(), request.getActualPayDateEnd());
        }
        if (StringUtils.isNotBlank(request.getDueAmount())) {
            wrapper.eq(SoftwareOfficialFee::getDueAmount, request.getDueAmount());
        }
        if (StringUtils.isNotBlank(request.getActualAmount())) {
            wrapper.eq(SoftwareOfficialFee::getActualAmount, request.getActualAmount());
        }
        return wrapper;
    }

    public LambdaQueryWrapper<Software> getWrapperByGetSoftwareRequest(GetSoftwareRequest request) {
        LambdaQueryWrapper<Software> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(request.getSoftwareName())) {
            wrapper.eq(Software::getSoftwareName, request.getSoftwareName());
        }
        if (StringUtils.isNotBlank(request.getSoftwareCode())) {
            wrapper.eq(Software::getSoftwareCode, request.getSoftwareCode());
        }
        if (StringUtils.isNotBlank(request.getVersion())) {
            wrapper.eq(Software::getVersion, request.getVersion());
        }
        if (StringUtils.isNotBlank(request.getInventorName())) {
            wrapper.eq(Software::getInventorId, userService.findUserByUserName(request.getInventorName()).getId());
        }
        if (StringUtils.isNotBlank(request.getAgency())) {
            wrapper.eq(Software::getAgency, request.getAgency());
        }
        if (StringUtils.isNotBlank(request.getRegisterCode())) {
            wrapper.eq(Software::getRegisterCode, request.getRegisterCode());
        }
        if (StringUtils.isNotBlank(request.getCertificateCode())) {
            wrapper.eq(Software::getCertificateCode, request.getCertificateCode());
        }
        if (StringUtils.isNotBlank(request.getArchiveCode())) {
            wrapper.eq(Software::getArchiveCode, request.getArchiveCode());
        }
        if (StringUtils.isNotBlank(request.getRightStatus())) {
            wrapper.eq(Software::getRightStatus, request.getRightStatus());
        }
        if (StringUtils.isNotBlank(request.getRightRange())) {
            wrapper.eq(Software::getRightRange, request.getRightRange());
        }
        if (StringUtils.isNotBlank(request.getProposalBeginDate()) && StringUtils.isNotBlank(request.getProposalEndDate())) {
            wrapper.between(Software::getProposalDate, request.getProposalBeginDate(), request.getProposalEndDate());
        }
        if (StringUtils.isNotBlank(request.getApplicationBeginDate()) && StringUtils.isNotBlank(request.getApplicationEndDate())) {
            wrapper.between(Software::getApplicationDate, request.getApplicationBeginDate(), request.getApplicationEndDate());
        }
        if (StringUtils.isNotBlank(request.getCertificateBeginDate()) && StringUtils.isNotBlank(request.getCertificateEndDate())) {
            wrapper.between(Software::getCertificateDate, request.getCertificateBeginDate(), request.getCertificateEndDate());
        }
        if (StringUtils.isNotBlank(request.getPublishBeginDate()) && StringUtils.isNotBlank(request.getPublishEndDate())) {
            wrapper.between(Software::getPublishDate, request.getPublishBeginDate(), request.getPublishEndDate());
        }
        if (StringUtils.isNotBlank(request.getArchiveBeginDate()) && StringUtils.isNotBlank(request.getArchiveEndDate())) {
            wrapper.between(Software::getArchiveDate, request.getArchiveBeginDate(), request.getArchiveEndDate());
        }
        if (StringUtils.isNotBlank(request.getFinishBeginDate()) && StringUtils.isNotBlank(request.getFinishEndDate())) {
            wrapper.between(Software::getFinishDate, request.getFinishBeginDate(), request.getFinishEndDate());
        }
        return wrapper;
    }

    public LambdaQueryWrapper<Software> getWrapper(GetSoftwareRequest request) {
        LambdaQueryWrapper<Software> wrapper = new LambdaQueryWrapper<>();
        List<Criteria.KV> items = request.getCriteria().getItems();
        for (Criteria.KV kv : items) {
            switch (kv.getKey()) {
                case "softwareName" : {
                    wrapper.eq(Software::getSoftwareName, kv.getValue());
                    break;
                }
                case "softwareCode" : {
                    wrapper.eq(Software::getSoftwareCode, kv.getValue());
                    break;
                }
                case "inventorName" : {
                    User user = userService.findUserByUserName(kv.getValue());
                    wrapper.eq(Software::getInventorId, user.getId());
                    break;
                }
                case "agencyName" : {
                    wrapper.eq(Software::getAgency, kv.getValue());
                    break;
                }
                case "developWay" : {
                    wrapper.eq(Software::getDevelopWay, kv.getValue());
                    break;
                }
                case "rightStatus" : {
                    wrapper.eq(Software::getRightStatus, kv.getValue());
                    break;
                }
                case "proposalBeginDate" : {
                    String endDate = null;
                    for (Criteria.KV kV : items) {
                        if (kV.getKey().equals("proposalEndDate")) {
                            endDate = kV.getValue();
                            break;
                        }
                    }
                    wrapper.between(Software::getProposalDate, kv.getValue(), endDate);
                    break;
                }
                case "applicationBeginDate" : {
                    String endDate = null;
                    for (Criteria.KV kV : items) {
                        if (kV.getKey().equals("applicationEndDate")) {
                            endDate = kV.getValue();
                            break;
                        }
                    }
                    wrapper.between(Software::getApplicationDate, kv.getValue(), endDate);
                    break;
                }
            }
        }
        return wrapper;
    }
}
