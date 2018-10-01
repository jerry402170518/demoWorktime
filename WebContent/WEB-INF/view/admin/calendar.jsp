<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="./css/calendar.css">

<div class="calendar" id="calendar-app">
    <div class="calendar--day-view" id="day-view">
        <span class="day-view-exit" id="day-view-exit">&times;</span>
        <span class="day-view-date" id="day-view-date"></span>
        <div class="day-view-content">
            <div class="day-highlight" >
                請注意欲修改之日期是否正確，且記得填寫放假時數。
            </div>
            <div class="day-add-event" id="add-day-event-box" data-active="false">
                <div class="row">
                    <div class="half">
                        <label class="add-event-label">
                            Name of event
                            <input type="text" class="add-event-edit add-event-edit--long" placeholder="New event" id="input-add-event-name">

                        </label>
                    </div>
                    <div class="qtr">
                        <label class="add-event-label">
                            Start Time
                            <input type="text" class="add-event-edit" placeholder="8:15" id="input-add-event-start-time" data-options="1,2,3,4,5,6,7,8,9,10,11,12"
                                data-format="datetime">
                            <input type="text" class="add-event-edit" placeholder="am" id="input-add-event-start-ampm" data-options="a,p,am,pm">
                        </label>
                    </div>
                    <div class="qtr">
                        <label class="add-event-label">
                            End Time
                            <input type="text" class="add-event-edit" placeholder="9" id="input-add-event-end-time" data-options="1,2,3,4,5,6,7,8,9,10,11,12"
                                data-format="datetime">
                            <input type="text" class="add-event-edit" placeholder="am" id="input-add-event-end-ampm" data-options="a,p,am,pm">
                        </label>
                    </div>
                    <div class="half">
                        <a onkeyup="if(event.keyCode != 13) return; this.click();" tabindex="0" id="add-event-save" class="event-btn--save event-btn">save</a>
                        <a tabindex="0" id="add-event-cancel" class="event-btn--cancel event-btn">cancel</a>
                    </div>
                </div>

            </div>
            <div id="day-events-list" class="day-events-list">
                    <form class="form" action="Holiday?action=modifyHoliday" method="post">
                    		<input type="hidden" name="holiday" id="holiday">
                            <div class="radioLoc">
                                <input type="radio" name="status" value="ordinary"> 平常日
                                <input type="radio" name="status" value="holiday" checked>放假日
                            </div>
                            <span class="hourSpan">放假時數:</span>
                            <select name="hour" id="hour" class="hourSel">
                                <option value="0">0小時</option>
                                <option value="2">2小時</option>
                                <option value="3">3小時</option>
                                <option value="4">4小時</option>
                                <option value="5">5小時</option>
                                <option value="6">6小時</option>
                                <option value="7">7小時</option>
                                <option value="8">8小時</option>
                            </select>
                            <textarea name="reason" id="reason" cols="35" rows="14" required></textarea>
                            <div class="div">
                                <input type="submit" value="提交" id="reasonSub">
                                <input type="button" value="取消" id="dayViewExitBtn">
                            </div>
                   </form>
            </div>
            
        </div>
    </div>
    <div class="calendar--view" id="calendar-view">
        <div class="cview__month">
            <span class="cview__month-last" id="calendar-month-last" style="font-family: Microsoft JhengHei">Apr</span>
            <span class="cview__month-current" id="calendar-month" style="font-family: Microsoft JhengHei">May</span>
            <span class="cview__month-next" id="calendar-month-next" style="font-family: Microsoft JhengHei">Jun</span>
        </div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">日</div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">一</div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">二</div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">三</div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">四</div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">五</div>
        <div class="cview__header" style="font-family: Microsoft JhengHei">六</div>
        <div class="calendar--view" id="dates">
        </div>
    </div>
    <div class="footer">
        <span>
            <span id="footer-date" class="footer__link">Today is May 30</span>
        </span>
    </div>
</div>

<script src="./js/calendar.js"></script>