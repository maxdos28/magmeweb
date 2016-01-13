package cn.magme.web.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.magme.constants.PojoConstant;
import cn.magme.pojo.EventLlk;
import cn.magme.pojo.User;
import cn.magme.service.EventLlkService;
import cn.magme.service.UserService;
import cn.magme.util.DateUtil;

@Results({ @Result(name = "index_success", location = "/WEB-INF/pages/event/index.ftl"),
        @Result(name = "llk_success", location = "/WEB-INF/pages/event/llk.ftl") })
@SuppressWarnings("serial")
public class UserEventAction extends BaseAction {

    @Resource
    private UserService userService;

    @Resource
    private EventLlkService eventLlkService;

    public String index() {

        chargeList = eventLlkService.queryLlkPrizeList(PojoConstant.EVENTLLK.CODE_CHARGE, null, null, null, null);

        return "index_success";
    }

    public String llk() {
        Date yesterDay = DateUtil.getLastDay();
        Date weekFirstDay = DateUtil.getDayByWeek(true);
        Date weekLastDay = DateUtil.getDayByWeek(false);

        llkYesterList = eventLlkService.queryLlkPrizeList(PojoConstant.EVENTLLK.CODE_MAG, yesterDay, yesterDay, null, null);
        llkWeekList = eventLlkService.queryLlkPrizeList(PojoConstant.EVENTLLK.CODE_MAG, weekFirstDay, weekLastDay, null,
                null);

        return "llk_success";
    }

    public String addJson() {
        User user = this.getSessionUser();
        EventLlk eventLlk = new EventLlk();
        eventLlk.setUserId(user.getId());
        eventLlk.setCode(code);
        eventLlk.setEventDate(DateUtil.parse(eventDate));
        eventLlk.setScore(new BigDecimal(score));
        eventLlk.setSign(sign);
        this.jsonResult = eventLlkService.insertEventLlk(eventLlk);
        return JSON;
    }

    public String hasPrizeJson() {
        this.jsonResult = eventLlkService.hasPrizeByUserIdAndCode(this.getSessionUserId(), null);
        return JSON;
    }

    private Integer code;
    private String eventDate;
    private String score;
    private String sign;
    private List<EventLlk> chargeList;
    private List<EventLlk> llkYesterList;
    private List<EventLlk> llkWeekList;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<EventLlk> getChargeList() {
        return chargeList;
    }

    public void setChargeList(List<EventLlk> chargeList) {
        this.chargeList = chargeList;
    }

    public List<EventLlk> getLlkYesterList() {
        return llkYesterList;
    }

    public void setLlkYesterList(List<EventLlk> llkYesterList) {
        this.llkYesterList = llkYesterList;
    }

    public List<EventLlk> getLlkWeekList() {
        return llkWeekList;
    }

    public void setLlkWeekList(List<EventLlk> llkWeekList) {
        this.llkWeekList = llkWeekList;
    }

}
