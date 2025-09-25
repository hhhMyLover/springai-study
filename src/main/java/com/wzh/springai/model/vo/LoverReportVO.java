package com.wzh.springai.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class LoverReportVO {
    private String title;
    private List<String> suggestions;
}