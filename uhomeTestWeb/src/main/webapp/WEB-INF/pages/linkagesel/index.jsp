<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<html lang="zh">
<head>
    <meta charset="utf-8" />
    <title>选择城市3联动</title>
</head>

<body>
<select id="DressProvince"></select>
<select id="DressCity"></select>
<select id="DressArea"></select>
初始化城市联动
<br /><br /><br />

<select id="DressProvince2" disabled="disabled"></select>
<select id="DressCity2" disabled="disabled"></select>
<select id="DressArea2"></select>
设置默认区，省、市不可改
<br /><br /><br />

<select id="DressProvince3"></select>
<select id="DressCity3"></select>
<select id="DressArea3"></select>
设置默认省市区

<br /><br /><br />

<script src="${_jsPath}/jquery/jquery.1.8.1.js"></script>
<script src="${_jsPath}/linkagesel/linkage_sel.js"></script>
<script>
    $(function () {
        // 实例1：
        var oneLinkage = new linkageSelect();
        oneLinkage.init({
            "oneSel":['#DressProvince'],
            "twoSel":['#DressCity'],
            "threeSel":['#DressArea']
        });

        // 实例2：
        var DressProvince2 = 6600,
            DressCity2 = 6601,
            DressArea2 = 6608;
        if (DressProvince2) {
            var twoLinkage = new linkageSelect();
            twoLinkage.init({
                "oneSel":['#DressProvince2', '--省份--', DressProvince2],
                "twoSel":['#DressCity2', '--城市--', DressCity2],
                "threeSel":['#DressArea2', '--地区--', DressArea2]
            });
        } else {
            var twoLinkage = new linkageSelect();
            twoLinkage.init({
                "oneSel":['#DressProvince2', '--省份--'],
                "twoSel":['#DressCity2', '--城市--'],
                "threeSel":['#DressArea2', '--地区--']
            });
        }

        // 实例3：
        var DressProvince3 = 6600,
            DressCity3 = 6601,
            DressArea3 = 6608;
        if (DressProvince3) {
            var threeLinkage = new linkageSelect();
            threeLinkage.init({
                "oneSel":['#DressProvince3', '--省份--', DressProvince3],
                "twoSel":['#DressCity3', '--城市--', DressCity3],
                "threeSel":['#DressArea3', '--地区--', DressArea3]
            });
        } else {
            var threeLinkage = new linkageSelect();
            threeLinkage.init({
                "oneSel":['#DressProvince3', '--省份--'],
                "twoSel":['#DressCity3', '--城市--'],
                "threeSel":['#DressArea3', '--地区--'],
                'oneSelUrl':'get-province.htm',   //可供不同数据接口使用
                'twoSelUrl':'get-city.htm?pid={oneId}',
                'threeSelUrl':'get-area.htm?pid={oneId}&cid={twoId}'
            });
        }

    });
</script>
</body>
</html>
